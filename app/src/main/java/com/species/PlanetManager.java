package com.species;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Range;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlanetManager extends AppCompatActivity implements Serializable {

    public static final int IMAGE_TOP = 20; // Distancia superior de la imagen
    private Species specie;
    private Stars star;
    private Planets planet;
    //private Builds build;
    //private Surfaces surface;
    private Recursos res;
    private Point buildPoint;
    private int buildX, buildY;
    private Boolean canBuild;
    private LinearLayout lin;
    private ImageView img;
    private Button proyecto;
    private int turn;
    private int cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_manager);
        hideView();
        Intent i = getIntent();
        //specie = (Species)i.getSerializableExtra("specie");
        //star = (Stars)i.getSerializableExtra("star");
        //planet = (Planets)i.getSerializableExtra("planet");
        //build = (Builds)i.getSerializableExtra("build");
        //res = (Recursos)i.getSerializableExtra("recursos");
        //canBuild = (Boolean)i.getSerializableExtra("canBuild");

        //planet = new Planets();
        planet = (Planets)i.getSerializableExtra("planet");
        //planet = planet.getPlanetById(this, planet.getId());

        lin = findViewById(R.id.planetLayout);
        img = findViewById(R.id.planetView);
        //Log.i("PlanetManager", "Star: " + planet.getStar() + " Planet: " + planet.getName());

        setPlanet();

    }

    public void onResume(){
        super.onResume();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        turn = data.getInt("turn", 0);
    }

    protected void play() {
        //super.onStart();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.factory);
        mp.start();
    }

    private void setPlanet() {
        TextView name = findViewById(R.id.planetName);
        TextView desc = findViewById(R.id.planetDesc);
        TextView msg =  findViewById(R.id.textMessage);
        TextView textProyecto =  findViewById(R.id.textProyecto);
        Button proyecto = findViewById(R.id.buildButton);

        String nameType = planet.getNameType(planet.getType());
        String nameSize = planet.getNameSize(planet.getSize());
        name.setText(planet.getName());
        desc.setText(String.format("Planeta %s %s", nameSize, nameType));

        Bitmap fondo = Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        drawPlanet(fondo, bitmap, canvas, planet.getSize());

/*        surface = new Surfaces();
        int explore = planet.getExplore();
        boolean origin = surface.getOrigin(this); // Comprueba si hay Colonia Base
        String owner = planet.getOwner();

        if(explore == 0 || !owner.equals(specie.getName())){
            msg.setText("No has explorado este planeta\nConstruye una nave con un sensor\npara escanear la superficie");
            textProyecto.setEnabled(false);
            proyecto.setEnabled(false);
            Log.i("NO_EXPLORADO", explore+"");
        } else if(origin){
            msg.setText("Sin Proyecto");
            Log.i("ORIGEN", origin+"");
            drawSurface();
        } else {
            msg.setText("Sin Proyecto");
            setNewSurface();
        }*/
    }

    private void drawPlanet(Bitmap fondo, Bitmap bitmap, Canvas canvas, int size) {
        String imagePlanet = getImagePlanet(planet.getType());
        int resImage = this.getResources().getIdentifier(imagePlanet, "drawable", this.getPackageName());
        Bitmap planetCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizePlanet = Bitmap.createScaledBitmap(planetCenter, size*230, size*230, true);
        // Draw Planet
        canvas.drawBitmap(fondo, new Matrix(), null);
        canvas.drawBitmap(resizePlanet,
                (fondo.getWidth() - resizePlanet.getWidth()) >> 1,
                (fondo.getHeight() - resizePlanet.getHeight()) >> 1, new Paint());
        img.setImageBitmap(bitmap);

    }

    // TODO Asignar imagen según el tipo de planeta
    private String getImagePlanet(Integer type) {
        Resources res = this.getResources();
        String[] imagePlanet = res.getStringArray(R.array.image_planet);
        return imagePlanet[type-1];
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem option){
        TopMenu menu = new TopMenu(this);
        menu.onOptionsItemSelected(option);
        return false;
    }

    private void hideView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

}

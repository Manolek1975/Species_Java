package com.species;

import static com.species.Game.turn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.List;

public class PlanetManager extends AppCompatActivity implements Serializable {
    public static final int IMAGE_TOP = 20; // Distancia superior de la imagen
    private int starId;
    private Planets planet;
    private Builds build;
    private Surfaces surface;
    private Recursos res;
    private Point buildPoint;
    private int buildX, buildY;
    private Boolean canBuild;
    private LinearLayout lin;
    private ImageView img;
    int endTurn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_manager);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        surface =  new Surfaces();
        Intent i = getIntent();
        planet = (Planets)i.getSerializableExtra("planet");
        starId = planet.getStar();
        build = (Builds)i.getSerializableExtra("build");

        setPlanet();
    }

    private void setPlanet() {
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textEndTurn);
        TextView planetName = findViewById(R.id.planetName);
        TextView planetType = findViewById(R.id.planetType);
        planetName.setText(planet.getName());
        String type = planet.getNameType(planet.getType());
        planetType.setText(String.format("Planeta %s", type));
        ImageView imgPlanet = findViewById(R.id.imagePlanet);
        String img = planet.getImagePlanet(this, planet.getType());
        int res = this.getResources().getIdentifier(img, "drawable", this.getPackageName());
        imgPlanet.setImageResource(res);

        if (build != null){
            ImageButton proyectoButton = findViewById(R.id.proyectoButton);
            String imgBuild = build.getImage();
            int resBuild = this.getResources().getIdentifier(imgBuild, "drawable", this.getPackageName());
            textProyecto.setText(build.getName());
            proyectoButton.setImageResource(resBuild);
            endTurn = build.getCost();
            textProyectoTurnos.setText(String.format("%d turnos", endTurn));

            surface.insertSurface(this, planet.getId(), build.getId(), endTurn, 0);

            Log.i("BUILD", build.getName());
        } else {
            textProyecto.setText(R.string.sin_proyecto);
        }

    }

    public void advanceTurn(View view) {
        TextView textTurn = findViewById(R.id.textTurn);
        TextView textProyecto = findViewById(R.id.textProyecto);
        TextView textProyectoTurnos = findViewById(R.id.textEndTurn);

        int turn = Game.advanceTurn(view);
        textTurn.setText(String.valueOf(turn));
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();

        Surfaces square = new Surfaces();
        square.updateSquare(this);

        List<Surfaces> surfaceList = surface.getTurns(this);
        if(surfaceList.isEmpty()) return;
        for(Surfaces val : surfaceList) {
            endTurn = val.getCost();
            textProyectoTurnos.setText(String.format("%s turnos", endTurn));
            surface = val;
        }

        ImageButton buildButton = findViewById(R.id.proyectoButton);
        MutableLiveData<Integer> listen = new MutableLiveData<>();
        listen.setValue(endTurn);
        listen.observe(this, changedValue -> {
            if(endTurn == 0){
                //Log.i("RES", surface.getPlanet() + " , " + surface.getBuild() + " , " + surface.getColor());
                buildButton.setImageDrawable(null);
                textProyectoTurnos.setText("");
                textProyecto.setText(R.string.sin_proyecto);
                //res.increaseRecursos(this, planet, build.getBuildById(this, surface.getBuild()), surface.getColor());
                Game.buildCompleted(this, surface);
            }
        });
    }

    protected void play() {
        super.onStart();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.factory);
        mp.start();
    }

    public void onPause(){
        super.onPause();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = data.edit();
        edit.putInt("turn", turn);
        edit.apply();
    }

    public void onResume(){
        super.onResume();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        turn  = data.getInt("turn", 0);
        TextView textTurn = findViewById(R.id.textTurn);
        textTurn.setText(String.valueOf(turn));
    }

    public void runSistem(View view) {
        Intent i =  new Intent(this, SistemActivity.class);
        i.putExtra("starId", starId);
        startActivity(i);
    }

    public void runBuilds(View view) {
        Intent i =  new Intent(this, BuildsActivity.class);
        i.putExtra("planet", planet);
        startActivity(i);
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

}

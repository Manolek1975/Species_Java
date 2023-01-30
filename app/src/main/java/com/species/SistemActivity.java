package com.species;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;


public class SistemActivity extends AppCompatActivity implements Serializable {

    private Species specie;
    private Stars star;
    private Planets planet;
    private Recursos res;
    private ImageView sistemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistem_activity);
        hideView();

        Intent i = getIntent();
        specie = (Species)i.getSerializableExtra("specie");
        star = (Stars)i.getSerializableExtra("star");
        res = (Recursos)i.getSerializableExtra("recursos");

        TextView starName = findViewById(R.id.starName);
        sistemView = findViewById(R.id.sistemView);
        starName.setText("Sistema " + star.getName());
        Log.i("Sistem_Activity",star.getName() + " : " + specie.getName());
        drawSistem();
    }

    private void drawSistem() {
        int angle = 0;
        int radio = 600;
        int x, y;

        Planets planets = new Planets();
        List<Planets> planetList = planets.getPlanets(this, star);

        //String starImage = imageStar(star.getMagnitud());
        String starImage = star.getImage();
        int resImage = this.getResources().getIdentifier(starImage, "drawable", this.getPackageName());
        Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        Bitmap starCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resultingBitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(resultingBitmap);
        canvas.drawBitmap(fondo, new Matrix(), null);
        canvas.drawBitmap(starCenter,
                (fondo.getWidth() - starCenter.getWidth()) >> 1,
                (fondo.getHeight() - starCenter.getHeight()) >> 1, new Paint());

        for(Planets planet: planetList){
            angle += 30;
            x = (int)(radio * cos(angle));
            y = (int)(radio * sin(angle));
            int size = planet.getSize();
            //String imagePlanet = getImagePlanet(planet.getType());
            //resImage = this.getResources().getIdentifier(imagePlanet, "drawable", this.getPackageName());
            Bitmap drawPlanet = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizePlanet = Bitmap.createScaledBitmap(drawPlanet, 50 + size*20, 50 + size*20, true);
            canvas.drawBitmap(resizePlanet,
                    (fondo.getWidth() - starCenter.getWidth()) + x >> 1,
                    (fondo.getHeight() - starCenter.getHeight()) + y >> 1, new Paint());

        }
        sistemView.setImageBitmap(resultingBitmap);

        // Add Buttons to scroll
        LinearLayout planetButtons = findViewById(R.id.planetButtonsSistem);
        for(Planets planet: planetList){

            Button planetButton = new Button(this);
            //String iconPlanet = getIconPlanet(planet.getType());

            //resImage = this.getResources().getIdentifier(iconPlanet, "drawable", this.getPackageName());
            planetButton.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            planetButton.setCompoundDrawablesWithIntrinsicBounds(resImage, 0, 0, 0);
            planetButton.setPadding(50,20,0,20);
            planetButton.setCompoundDrawablePadding(50);
            planetButton.setAllCaps(false);
            planetButton.setText(planet.getName());
            planetButton.setBackgroundColor(Color.TRANSPARENT);
            planetButton.setTextColor(Color.WHITE);
            planetButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

/*            planetButton.setOnClickListener(v -> {
                Intent i = new Intent(SistemActivity.this, PlanetManager.class);
                i.putExtra("specie", specie);
                i.putExtra("star", star);
                i.putExtra("planet", planet);
                i.putExtra("recursos", res);
                i.putExtra("canBuild", false);
                startActivity(i);
            });*/
            planetButtons.addView(planetButton);
        }

    }

/*    private String getImagePlanet(Integer type) {
        Resources res = this.getResources();
        String[] imagePlanet = res.getStringArray(R.array.image_planet);
        return imagePlanet[type-1];
    }

    private String getIconPlanet(Integer type) {
        Resources res = this.getResources();
        String[] iconPlanet = res.getStringArray(R.array.icon_planet);
        return iconPlanet[type-1];
    }*/

    public String imageStar(Integer magnitud) {
        Resources res = this.getResources();
        String[] imageStars = res.getStringArray(R.array.image_stars);
        return imageStars[magnitud-1];
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

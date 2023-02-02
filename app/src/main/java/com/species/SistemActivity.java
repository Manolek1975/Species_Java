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
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    int width, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistem_activity);
        hideView();
        // Calcular medidas del smartphone
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        Intent i = getIntent();
        star = new Stars();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);
        //specie = (Species)i.getSerializableExtra("specie");
        //star = (Stars)i.getSerializableExtra("star");
        //res = (Recursos)i.getSerializableExtra("recursos");

        TextView starName = findViewById(R.id.systemName);
        sistemView = findViewById(R.id.sistemView);
        starName.setText(String.format("Sistema %s", star.getName()));
        //Log.i("Sistem_Activity",star.getName() + " : " + specie.getName());
        drawSistem();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        int centerX = width - 200 >> 1;
        int centerY = height - 200 >> 1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Planets planet = new Planets();
                List<Planets> planetsList = planet.getPlanets(this, star);
                for(Planets val : planetsList) {
                    Range<Integer> rangoX = Range.create(x, x+200);
                    Range<Integer> rangoY = Range.create(y, y+200);
                    if (rangoX.contains(val.getX()+150) && rangoY.contains(val.getY()+300)) {
                        Intent i = new Intent(this, PlanetManager.class);
                        i.putExtra("starId", star.getId());
                        i.putExtra("planet", val);
                        i.putExtra("canBuild", false);
                        startActivity(i);
                    }
                }
                Log.i("XY", "X:" + x + " | Y:" + y);
                Log.i("center", "X:" + centerX + " | Y:" + centerY);

        }
        return false;
    }

    private void drawSistem() {
        ImageView image = findViewById(R.id.sistemView);
        // Crear fondo con medidas
        Bitmap fondo = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = this.getResources().getIdentifier("fondo_sistema", "drawable", this.getPackageName());
        Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        canvas.drawBitmap(fondoView, new Matrix(), null);
        image.setImageBitmap(bitmap);

        // Draw Star
        String starImage = star.getImage();
        int resImage = this.getResources().getIdentifier(starImage, "drawable", this.getPackageName());
        Bitmap starCenter = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeStar = Bitmap.createScaledBitmap(starCenter, 200, 200, true);
        int centerX = width - 200 >> 1;
        int centerY = height - 200 >> 1;
        canvas.drawBitmap(resizeStar, centerX, centerY, new Paint());

        // Draw Planets
        int numPlanets = 0;
        Planets planets = new Planets();
        List<Planets> planetList = planets.getPlanets(this, star);
        for(Planets planet: planetList){
            numPlanets += 1;
            int size = planet.getSize();
            centerX = width >> 1;
            centerY = height >> 1;
            // Draw orbit
/*            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setStrokeWidth(2);
            p.setStyle(Paint.Style.STROKE);
            canvas.drawOval(centerX - 500, centerY - 200, centerX + 500, centerY + 200, p);
            canvas.drawLine(centerX, centerY, centerX+ x, centerY + y, p);*/

            if (planet.getX() == 0){
                Planets xy = new Planets();
                setPlanetCoord(planet.getId(), centerX, centerY, numPlanets);
                xy = xy.getPlanetById(this, planet.getId());
                planet.setX(xy.getX());
                planet.setY(xy.getY());
            }

            String imagePlanet = getImagePlanet(planet.getType());
            resImage = this.getResources().getIdentifier(imagePlanet, "drawable", this.getPackageName());
            Bitmap drawPlanet = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizePlanet = Bitmap.createScaledBitmap(drawPlanet, 50+size*20, 50+size*20, true);
            canvas.drawBitmap(resizePlanet, planet.getX(), planet.getY(), new Paint());
        }
        sistemView.setImageBitmap(bitmap);

        // Add Buttons to scroll
/*
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

*/
/*            planetButton.setOnClickListener(v -> {
                Intent i = new Intent(SistemActivity.this, PlanetManager.class);
                i.putExtra("specie", specie);
                i.putExtra("star", star);
                i.putExtra("planet", planet);
                i.putExtra("recursos", res);
                i.putExtra("canBuild", false);
                startActivity(i);
            });*//*

            planetButtons.addView(planetButton);
        }
*/

    }

    private void setPlanetCoord(int id, int x, int y, int numPlanets) {
        Planets planet = new Planets();
            if (numPlanets == 1) {
                x = x - 350;
                y = y - 300;
            }
            if (numPlanets == 2) {
                x = x + 300;
                y = y + 350;
            }
            if (numPlanets == 3) {
                x = x + 300;
                y = y - 200;
            }
            if (numPlanets == 4) {
                x = x - 400;
                y = y + 300;
            }
        planet.setPlanetXY(id, x, y, this);
    }

    private String getImagePlanet(Integer type) {
        Resources res = this.getResources();
        String[] imagePlanet = res.getStringArray(R.array.image_planet);
        return imagePlanet[type-1];
    }

/*    private String getIconPlanet(Integer type) {
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

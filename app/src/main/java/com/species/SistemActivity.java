package com.species;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;


public class SistemActivity extends AppCompatActivity implements Serializable {
    private Stars star = new Stars();
    private ImageView sistemView;
    int width, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistem_activity);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        // Calcular medidas del smartphone
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);

        TextView starName = findViewById(R.id.systemName);
        sistemView = findViewById(R.id.sistemView);
        starName.setText(String.format("Sistema %s", star.getName()));
        Log.i("Sistem_Activity", star.getName());
        drawSistem();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Planets planet = new Planets();
            List<Planets> planetsList = planet.getPlanets(this, star);
            for (Planets val : planetsList) {
                Range<Integer> rangoX = Range.create(x, x + 200);
                Range<Integer> rangoY = Range.create(y, y + 200);
                if (rangoX.contains(val.getX() + 150) && rangoY.contains(val.getY() + 400)) {
                    if (val.getExplore() != 1) {
                        View customToastroot = View.inflate(this, R.layout.custom_toast, null);
                        TextView msg = (TextView) customToastroot.findViewById(R.id.toastMsg);
                        msg.setText(R.string.explore);
                        Toast customtoast = new Toast(getApplicationContext());
                        customtoast.setView(customToastroot);
                        customtoast.setDuration(Toast.LENGTH_LONG);
                        customtoast.show();
                        return false;
                    }

                    Intent i = new Intent(this, PlanetManager.class);
                    i.putExtra("starId", star.getId());
                    i.putExtra("planet", val);
                    i.putExtra("canBuild", false);
                    startActivity(i);
                }
            }
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

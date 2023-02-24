package com.species;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class SistemActivity extends AppCompatActivity {
    private Stars star = new Stars();
    LinearLayout fondo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        fondo = findViewById(R.id.sistemLayout);
        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);

        drawStar();
        drawPlanets();
        drawShip();
    }

    private void drawStar() {
        TextView sistemName = findViewById(R.id.sistemName);
        sistemName.setText(star.getName());
        ImageButton img = new ImageButton(this);
        int resImage = Game.getResId(star.getImage(), R.drawable.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeStar = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        img.setImageBitmap(resizeStar);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setX(0);
        img.setY(300);
        fondo.addView(img);
    }

    private void drawPlanets() {
        Planets planet = new Planets();
        for(int i=0; i < star.getPlanets(); i++){
            List<Planets> planetList = planet.getStarPlanets(this, star.getId());
            ImageButton img = new ImageButton(this);
            planet = planetList.get(i);
            int type = planet.getType();
            String image = planet.getImagePlanet(this, type);
            img.setBackgroundColor(Color.TRANSPARENT);
            int resImage = Game.getResId(image, R.drawable.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeShip = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            img.setImageBitmap(resizeShip);
            setPlanetPosition(img, i);
            fondo.addView(img);

        }

    }

    private void drawShip() {

        ImageButton img = new ImageButton(this);
        img.setImageResource(R.drawable.ship0);
        img.setBackgroundColor(Color.TRANSPARENT);
        int resImage = Game.getResId("ship0", R.drawable.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeShip = Bitmap.createScaledBitmap(bitmap, 160, 80, true);
        img.setImageBitmap(resizeShip);
        img.setX(-200);
        img.setY(200);
        fondo.addView(img);

        ObjectAnimator animX = ObjectAnimator.ofFloat(img, "translationX", 300f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(img, "translationY", -300f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(3000);
        animSetXY.start();

        img.setOnClickListener(v -> {
            Log.i("XY", fondo.getWidth() + "," + fondo.getHeight());
        });
    }

    private void setPlanetPosition(ImageButton img, int numPlanet) {
        int x=0, y =0;
        if (numPlanet == 0) {
            x = -300;
            y = -100;
        }
        if (numPlanet == 1) {
            x = 300;
            y = 200;
        }
        if (numPlanet == 2) {
            x = 300;
            y = -400;
        }
        if (numPlanet == 3) {
            x = -300;
            y = -100;
        }
        img.setX(x);
        img.setY(y);

        //planet.setPlanetXY(id, x, y, this);
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
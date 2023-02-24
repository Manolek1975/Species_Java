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

public class SistemActivity extends AppCompatActivity {
    private Stars star = new Stars();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistem);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);

        Intent i = getIntent();
        int starId = (int)i.getSerializableExtra("starId");
        star = star.getStarById(this, starId);

        drawStar();
        drawPlanets();
        drawShip();
    }

    private void drawStar() {
        LinearLayout fondo = findViewById(R.id.sistemLayout);
        TextView sistemName = findViewById(R.id.sistemName);
        sistemName.setText(star.getName());
        ImageButton img = new ImageButton(this);
        img.setImageResource(R.drawable.star1);
        img.setBackgroundColor(Color.TRANSPARENT);

        fondo.addView(img);
    }

    private void drawPlanets() {
        LinearLayout fondo = findViewById(R.id.sistemLayout);
        ImageButton img = new ImageButton(this);
        //img.setImageResource(R.drawable.icon_planet_congenial);
        img.setBackgroundColor(Color.TRANSPARENT);
        int resImage = Game.getResId("icon_planet_congenial", R.drawable.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resImage);
        Bitmap resizeShip = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        img.setImageBitmap(resizeShip);
        img.setX(-300);
        img.setY(-400);
        fondo.addView(img);

    }

    private void drawShip() {
        LinearLayout fondo = findViewById(R.id.sistemLayout);
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
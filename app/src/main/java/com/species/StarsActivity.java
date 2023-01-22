package com.species;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class StarsActivity extends AppCompatActivity implements Serializable {

    private Stars stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stars_activity);
        hideView();

        //Intent i = getIntent();
        //species = (Species)i.getSerializableExtra("specie");
        Species species = new Species();
        species = species.getMainSpecie(this);
        stars = new Stars();
        stars = stars.getMainStar(this);
        Log.i("specie", species.getName()+" - "+stars.getName());
        drawSector();
    }

    private void drawSector() {
        ImageView img = findViewById(R.id.fondoView);
        Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo2);
        Bitmap resultingBitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(resultingBitmap);
        canvas.drawBitmap(fondo, new Matrix(), null);

        List<Stars> starList = stars.getStars(this);
        drawJumps(canvas, starList);
        for(Stars val : starList){
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(48);
            int resImage = this.getResources().getIdentifier(val.getImage(), "drawable", this.getPackageName());
            Bitmap drawPlanet = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizePlanet = Bitmap.createScaledBitmap(drawPlanet, 100 , 100, true);
            canvas.drawBitmap(resizePlanet,
                    (val.getX()),
                    (val.getY()), new Paint());

            canvas.drawText(val.getName(), val.getX() - val.getName().length(), val.getY() - 10, paint);

            //Log.i("stars", val.getName() + ": " + val.getX() + "," + val.getY());
        }
        // Asociar el BitMap con el ImageView
        img.setImageBitmap(resultingBitmap);

    }

    private void drawJumps(Canvas canvas, List<Stars> starList){
        Paint line = new Paint();
        line.setColor(Color.CYAN);
        line.setStrokeWidth(5);
        int x1 = starList.get(0).getX() + 50;
        int y1 = starList.get(0).getY() + 50;
        int x2 = starList.get(1).getX() + 50;
        int y2 = starList.get(1).getY() + 50;
        canvas.drawLine(x1, y1, x2, y2, line);

    }

    private void hideView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}

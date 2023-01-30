package com.species;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class SectorActivity extends AppCompatActivity implements Serializable {

    private Stars stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_activity);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Stars star = new Stars();
                List<Stars> starList = star.getStars(this);
                for(Stars val : starList) {
                    Range<Integer> rangoX = Range.create(x, x+50);
                    Range<Integer> rangoY = Range.create(y, y+50);
                    if (rangoX.contains(val.getX()+80) && rangoY.contains(val.getY()+300)) { //getY+250 AVD
                        Intent i = new Intent(this, StarsActivity.class);
                        i.putExtra("star", val.getName());
                        startActivity(i);
                        Log.i("StarName", val.getName());
                        Log.i("Rango", rangoX + "," + rangoY);
                        Log.i("starCOORD", "X:" + val.getX() + " | Y:" + val.getY());
                    }
                }
                Log.i("XY", "X:" + x + " | Y:" + y);
        }
        return false;
    }

    private void drawSector() {
        ImageView image = findViewById(R.id.fondoView);
        // Calcular medidas del smartphone
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Log.i("Metrics", width + "," + height);

        // Crear fondo con medidas
        Bitmap fondo = Bitmap.createBitmap(width, height+50, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = this.getResources().getIdentifier("fondo2", "drawable", this.getPackageName());
        Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        canvas.drawBitmap(fondoView, new Matrix(), null);
        image.setImageBitmap(bitmap);

        List<Stars> starList = stars.getStars(this);
        //drawJumps(canvas, starList);
        for(Stars star : starList){
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(24);
            int resImage = this.getResources().getIdentifier(star.getImage(), "drawable", this.getPackageName());
            Bitmap drawPlanet = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeStar = Bitmap.createScaledBitmap(drawPlanet, 100 , 100, true);
            canvas.drawBitmap(resizeStar, (star.getX()), (star.getY()), new Paint());
            canvas.drawText(star.getName(), star.getX() - star.getName().length(), star.getY(), paint);

            //Log.i("stars", val.getName() + ": " + val.getX() + "," + val.getY());
        }
        // Asociar el BitMap con el ImageView
        image.setImageBitmap(bitmap);
    }

    private void drawJumps(Canvas canvas, List<Stars> starList){
        Paint line = new Paint();
        line.setColor(Color.CYAN);
        line.setStrokeWidth(5);
        int x1 = starList.get(0).getX() + 75;
        int y1 = starList.get(0).getY() + 75;
        int x2 = starList.get(1).getX() + 75;
        int y2 = starList.get(1).getY() + 75;
        canvas.drawLine(x1, y1, x2, y2, line);
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
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
    }
}

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

public class StarsActivity extends AppCompatActivity implements Serializable {
    private Species specie = new Species();
    private Stars star = new Stars();
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stars_activity);
        View view = getWindow().getDecorView();
        Game.hideviewMenu(view);
        // Calcular medidas del smartphone
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        int specieId = data.getInt("specieId", 0);
        specie = specie.getSpecieById(this, specieId);
        star = star.getMainStar(this);
        Log.i("StarActivity", specie.getName() + ", " +star.getName());
        drawSector();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Stars star = new Stars();
            List<Stars> starList = star.getStars(this);
            for (Stars val : starList) {
                Range<Integer> rangoX = Range.create(x, x + 50);
                Range<Integer> rangoY = Range.create(y, y + 50);
                if (rangoX.contains(val.getX() + 80) && rangoY.contains(val.getY() + 300)) { //getY+250 AVD
                    Intent i = new Intent(this, SistemActivity.class);
                    i.putExtra("starId", val.getId());
                    startActivity(i);
                }
            }
        }
        return false;
    }

    private void drawSector() {
        //TODO Draw solamente Estrellas exploradas
        ImageView image = findViewById(R.id.fondoView);
        // Crear fondo con medidas
        Bitmap fondo = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(fondo.getWidth(), fondo.getHeight(), fondo.getConfig());
        Canvas canvas = new Canvas(bitmap);
        int resImageFondo = Game.getResId("fondo_sector", R.drawable.class);
        Bitmap fondoView = BitmapFactory.decodeResource(getResources(), resImageFondo);
        canvas.drawBitmap(fondoView, new Matrix(), null);
        image.setImageBitmap(bitmap);

        // Draw Stars
        List<Stars> starList = star.getStars(this);
        //TODO Draw saltos
        //drawJumps(canvas, starList);
        for(Stars star : starList){
            Paint paint = new Paint();
            if (star.getId() == specie.getStar()){
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.WHITE);
            }
            paint.setTextSize(24);
            int resImage = Game.getResId(star.getImage(), R.drawable.class);
            Bitmap drawPlanet = BitmapFactory.decodeResource(getResources(), resImage);
            Bitmap resizeStar = Bitmap.createScaledBitmap(drawPlanet, 100 , 100, true);
            canvas.drawBitmap(resizeStar, (star.getX()), (star.getY()), new Paint());
            canvas.drawText(star.getName(), star.getX() - star.getName().length(), star.getY(), paint);
        }
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

}

package com.species;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;


import java.util.Objects;

public class MoveShip extends AppCompatImageView {

    Bitmap nave, fondo;
    private int x, y, width, heigth;

    public MoveShip(Context context) { super(context); }
    public MoveShip(Context context, Ships ship) {
        super(context);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        heigth = displaymetrics.heightPixels;
        BitmapDrawable imageFondo = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.fondo_sistema);
        fondo = Objects.requireNonNull(imageFondo).getBitmap();
        fondo = Bitmap.createScaledBitmap(fondo, width, heigth, false);
        BitmapDrawable imageShip = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.ship0);
        nave = Objects.requireNonNull(imageShip).getBitmap();
        nave = Bitmap.createScaledBitmap(nave, 50, 25, false);
        x = ship.getX();
        y = ship.getY();
    }

    public void destino(int posX, int posY){
        x = posX;
        y = posY;
        Log.i("DESTINO", x + "," + y);
    }

    public boolean move(){
        return false;
    }

    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(fondo, width, heigth, null);
        canvas.drawBitmap(nave, x+100, y+75, null);
    }

}

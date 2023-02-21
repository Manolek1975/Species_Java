package com.species;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.WindowManager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class MoveShip extends AppCompatImageView {

    Bitmap nave, fondo;
    private int x;
    private int y;

    public MoveShip(Context context) { super(context); }
    public MoveShip(Context context, Ships ship) {
        super(context);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        Point position = new Point();
        display.getSize(position);
        int width = position.x;
        int heigth = position.y;

        BitmapDrawable imageFondo = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.fondo_sistema);
        fondo = Objects.requireNonNull(imageFondo).getBitmap();
        fondo = Bitmap.createScaledBitmap(fondo, width, heigth, false);
        BitmapDrawable imageShip = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.ship0);
        nave = Objects.requireNonNull(imageShip).getBitmap();
        nave = Bitmap.createScaledBitmap(nave, 50, 25, false);

        x = ship.getX();
        y = ship.getY();
    }

    public boolean destino(int x, int y){
        return false;
    }

    public boolean move(){
        return false;
    }

    protected void onDraw(Canvas canvas){
        //canvas.drawBitmap(fondo, 0, 0, null);
        canvas.drawBitmap(nave, x, y, null);
    }

}

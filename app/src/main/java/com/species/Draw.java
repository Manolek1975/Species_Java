package com.species;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.species.R;
import com.species.ui.stars.Stars;

public class Draw extends AppCompatImageView {

    Bitmap bmStar;
    private int x, y;

    public Draw(Context context) { super(context); }
    public Draw(@NonNull Context context, Stars star) {
        super(context);

        int resImage = Game.getResId(star.getImage(), R.drawable.class);
        Bitmap drawStar = BitmapFactory.decodeResource(getResources(), resImage);
        bmStar = Bitmap.createScaledBitmap(drawStar, 100 , 100, false);
        x = star.getX();
        y = star.getY();
    }

    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(bmStar, x, y, null);
    }
}

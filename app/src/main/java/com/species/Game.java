package com.species;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Game extends AppCompatActivity {

    static int turn;

    public static int advanceTurn(View view){
        turn += 1;
        return turn;
    }

    // Dialog BUILD
    public static void buildCompleted(Context context, Surfaces surface){
        Planets planet = new Planets();
        planet = planet.getPlanetById(context, surface.getPlanet());
        int starId = planet.getStar();
        Builds build = new Builds();
        build = build.getBuildById(context, surface.getBuild());
        String buildImage = build.getImageBuild(context, surface.getBuild());
        int res = Game.getResId(buildImage, R.drawable.class);
        //int res = context.getResources().getIdentifier(buildImage, "drawable", context.getPackageName());

        Planets finalPlanet = planet;
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogStyle)
                .setIcon(res)
                .setTitle(build.getName())
                .setMessage("La construcción de " + build.getName() +" se ha completado en\n" + planet.getName())
                .setNegativeButton("Ignorar", (dialogInterface, i) -> {
                    //set what should happen when negative button is clicked
                    //Toast.makeText(getApplicationContext(),"Acción cancelada",Toast.LENGTH_LONG).show();
                })
                .setPositiveButton("Ir a Planeta", (dialogInterface, i) -> {
                    //surface = new Surfaces();
                    Intent intent =  new Intent(context, PlanetManager.class);
                    intent.putExtra("starId", starId);
                    intent.putExtra("planet", finalPlanet);
                    context.startActivity(intent);
                })
                .show();
    }

    public static void leaveOrbit(Context context, Ships ship, int planet) {
        int res = Game.getResId(ship.getImage(), R.drawable.class);
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogStyle)
                .setIcon(res)
                .setTitle(ship.getName())
                .setMessage("¿Desea abandonar la orbita?")
                .setNegativeButton("NO", (dialogInterface, i) -> {
                    //set what should happen when negative button is clicked
                    //Toast.makeText(getApplicationContext(),"Acción cancelada",Toast.LENGTH_LONG).show();
                })
                .setPositiveButton("SI", (dialogInterface, i) -> {
                    //surface = new Surfaces();
                    Intent intent =  new Intent(context, SistemActivity.class);
                    intent.putExtra("starId", ship.getSistema());
                    intent.putExtra("planet", planet);
                    context.startActivity(intent);
                })
                .show();
    }

    public static boolean getFill(Context context, int x, int y){
        int build = 0;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE x=? AND y=? AND build NOT NULL",
                new String[] { String.valueOf(x), String.valueOf(y) });
        c.moveToFirst();
        if(c.getCount() > 0){
            return true;
            //build = c.getInt(2);
        }
        c.close();
        db.close();
        return false;
    }

    /**
     * @author Lonkly
     * @param variableName - name of drawable, e.g R.drawable.<b>image</b>
     * @param с - class of resource, e.g R.drawable.class or R.raw.class
     * @return integer id of resource
     */
    public static int getResId(String variableName, Class<?> с) {
        Field field;
        int resId = 0;
        try {
            field = с.getField(variableName);
            try {
                resId = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    public static void hideview(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    public static void hideviewMenu(View view) {
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }





}

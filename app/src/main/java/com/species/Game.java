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

import java.util.ArrayList;
import java.util.List;

public abstract class Game extends AppCompatActivity {

    static int turn;

    public static int advanceTurn(View view){
        //TODO Insertar en Species Detalle el surface y los recursos -- setNewSquares()
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
        int res = context.getResources().getIdentifier(buildImage, "drawable", context.getPackageName());

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

    public static List<Point> setAvailables(Context context, Surfaces surface, Planets planet) {
        List<Surfaces> builds = surface.getBuildings(context, planet.getId());
        List<Point> availables = new ArrayList<>();
        for(Surfaces val : builds) {
            Point point = new Point(val.getX(), val.getY());
            Point c1 = new Point(point.x - 100, point.y - 50);
            Point c2 = new Point(point.x + 100, point.y - 50);
            Point c3 = new Point(point.x - 100, point.y + 50);
            Point c4 = new Point(point.x + 100, point.y + 50);
            boolean fill1 = Game.getFill(context, c1.x, c1.y);
            boolean fill2 = Game.getFill(context, c2.x, c2.y);
            boolean fill3 = Game.getFill(context, c3.x, c3.y);
            boolean fill4 = Game.getFill(context, c4.x, c4.y);
            if (!fill1) availables.add(c1);
            if (!fill2) availables.add(c2);
            if (!fill3) availables.add(c3);
            if (!fill4) availables.add(c4);
        }
        return availables;
    }

    public static boolean getFill(Context context, int x, int y){
        String build = null;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE x=? AND y=?",
                new String[] { String.valueOf(x), String.valueOf(y) });
        c.moveToFirst();
        if(c.getCount() > 0){
            build = c.getString(2);
        }
        c.close();
        db.close();
        return build != null;
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

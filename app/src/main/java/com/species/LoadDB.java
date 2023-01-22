package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Random;

public class LoadDB extends AppCompatActivity implements Serializable {

    private final Context context;

    public LoadDB(Context context){
        this.context = context;
    }

    protected void deleteDB(){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        //DBHelper.deleteDatabase(context);
        db.execSQL("delete from "+ DBSpecies.TABLE_NAME);
        db.execSQL("delete from "+ DBStars.TABLE_NAME);
    }

    protected void insertDB() {
        insertSpecies();
        insertStars();
    }

    public void insertSpecies() {
        Resources res = context.getResources();
        String[] name = res.getStringArray(R.array.name_species);
        String[] image = res.getStringArray(R.array.image_species);
        String[] star = res.getStringArray(R.array.star_species);

        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < name.length; i++) {
            // Rellena la tabla species
            values.put(DBSpecies.COLUMN_NAME, name[i]);
            //values.put(DBSpecies.COLUMN_DESC, description[i]);
            values.put(DBSpecies.COLUMN_IMAGE, image[i]);
            //values.put(DBSpecies.COLUMN_SKILL, hability[i]);
            values.put(DBSpecies.COLUMN_TYPE, 0);
            values.put(DBSpecies.COLUMN_STAR, star[i]);
            // Inserta una nueva fila con los valores de la key
            db.insert(DBSpecies.TABLE_NAME, null, values);
        }
        db.close();
    }

    private void insertStars() {
        Resources res = context.getResources();
        String[] name = res.getStringArray(R.array.name_stars);
        String[] image = res.getStringArray(R.array.image_stars);
        String[] coords = res.getStringArray(R.array.coords_stars);

        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < name.length; i++) {
            Random rand = new Random();
            String[] split = coords[i].split(",");
            Integer x = Integer.parseInt(split[0]);
            Integer y = Integer.parseInt(split[1]);
            // Rellena la tabla Stars
            values.put(DBStars.COLUMN_NAME, name[i]);
            values.put(DBStars.COLUMN_SECTOR, 1);
            values.put(DBStars.COLUMN_IMAGE, image[i]);
            values.put(DBStars.COLUMN_PLANETS, 1);
            values.put(DBStars.COLUMN_JUMPS, 1);
            values.put(DBStars.COLUMN_X, x);
            values.put(DBStars.COLUMN_Y, y);
            values.put(DBStars.COLUMN_TYPE, 0);
            values.put(DBStars.COLUMN_EXPLORE, 0);
            // Inserta una nueva fila con values
            db.insert(DBStars.TABLE_NAME, null, values);
        }
        db.close();
    }
}

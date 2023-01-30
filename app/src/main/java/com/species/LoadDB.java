package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
        //insertStars();
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

    public void insertStars(int w, int h) {
        Resources res = context.getResources();
        String[] name = res.getStringArray(R.array.name_stars);
        String[] image = res.getStringArray(R.array.image_stars);
        //String[] coords = res.getStringArray(R.array.coords_stars);

        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < name.length; i++) {
            Random rand = new Random();
            int xC = rand.nextInt(w-200) + 50;
            int yC = rand.nextInt(h-300) + 80;
            int planets = rand.nextInt(4) + 1;
            List<String> coords = new ArrayList<>();
            for(int j = 0; j < 20; j++)
                coords.add(xC + "," + yC);

            String[] split = coords.get(i).split(",");
            Integer x = Integer.parseInt(split[0]);
            Integer y = Integer.parseInt(split[1]);
            // Rellena la tabla Stars
            values.put(DBStars.COLUMN_NAME, name[i]);
            values.put(DBStars.COLUMN_SECTOR, 1);
            values.put(DBStars.COLUMN_IMAGE, image[i]);
            values.put(DBStars.COLUMN_PLANETS, planets);
            values.put(DBStars.COLUMN_JUMPS, 1);
            values.put(DBStars.COLUMN_X, x);
            values.put(DBStars.COLUMN_Y, y);
            values.put(DBStars.COLUMN_TYPE, 0);
            values.put(DBStars.COLUMN_EXPLORE, 0);
            // Inserta una nueva fila con values
            db.insert(DBStars.TABLE_NAME, null, values);

            //Log.i("COORDS", i + ":" + x + "," + y);
        }
        db.close();
    }

    public void insertPlanets() {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM stars", null);
        List<Stars> starsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Stars star = new Stars(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            );
            starsList.add(star);
        }
        cursor.close();

        for(Stars star:starsList){
            for(int i=1; i<=star.getPlanets();i++) {
                Random rand = new Random();
                values.put(DBPlanets.COLUMN_STAR, star.getName());
                values.put(DBPlanets.COLUMN_NAME, star.getName() + " " + roman(i));
                values.put(DBPlanets.COLUMN_SIZE, rand.nextInt(5) + 1);
                values.put(DBPlanets.COLUMN_TYPE, rand.nextInt(11) + 1);
                values.put(DBPlanets.COLUMN_OWNER, "");
                // Inserta una nueva fila con los valores de la key
                db.insert(DBPlanets.TABLE_NAME, null, values);
            }
        }
        db.close();
    }

    private String roman(Integer n){
        switch(n){
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            default:
                return null;
        }
    }
}

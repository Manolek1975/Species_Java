package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Planets extends AppCompatActivity implements IPlanets, Serializable {

    public static final int POPULATION_INIT = 50;
    private Integer id;
    private Integer star;
    private String name;
    private Integer size;
    private Integer type;
    private Integer x;
    private Integer y;
    private int population;
    private String owner;
    private int explore;
    private int origin;

    public Planets(){super();}

    public Planets(Integer id, Integer star, String name, Integer size, Integer type,
                   Integer x, Integer y,int population, String owner, int explore, int origin) {
        this.id = id;
        this.star = star;
        this.name = name;
        this.size = size;
        this.type = type;
        this.x = x;
        this.y = y;
        this.population = population;
        this.owner = owner;
        this.explore = explore;
        this.origin = origin;
    }

    public List<Planets> getPlanets(Context context, Stars star) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM planets WHERE star='" + star.getId() + "'", null);
        List<Planets> planetsList = new ArrayList<>();
        while (c.moveToNext()) {
            Planets planet = new Planets(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getString(8),
                    c.getInt(9),
                    c.getInt(10)
            );
            planetsList.add(planet);
        }
        c.close();
        db.close();
        return planetsList;
    }

    @Override
    public Planets getPlanetById(Context context, int id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM planets WHERE id=" + id, null);
        c.moveToFirst();
        Planets planet = new Planets(
                c.getInt(0),
                c.getInt(1),
                c.getString(2),
                c.getInt(3),
                c.getInt(4),
                c.getInt(5),
                c.getInt(6),
                c.getInt(7),
                c.getString(8),
                c.getInt(9),
                c.getInt(10)
        );
        c.close();
        db.close();
        return planet;
    }

    @Override
    public List<Planets> getOwnPlanets(Context context, int specieId) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM planets WHERE owner=" + specieId, null);
        List<Planets> planetsList = new ArrayList<>();
        while (c.moveToNext()) {
            Planets planet = new Planets(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getString(8),
                    c.getInt(9),
                    c.getInt(10)
            );
            planetsList.add(planet);
        }
        c.close();
        db.close();
        return planetsList;
    }

    @Override
    public Planets getPlanetTarget(Context context, String planetName) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM planets WHERE name='" + planetName + "'", null);
        c.moveToFirst();
        if(c.getCount() > 0) {
            Planets planet = new Planets(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getString(8),
                    c.getInt(9),
                    c.getInt(10)
            );
            c.close();
            db.close();
            return planet;
        }
        return this;
    }

    @Override
    public void setPlanetXY(int id, int x, int y, Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBPlanets.COLUMN_X, x);
        values.put(DBPlanets.COLUMN_Y, y);

        db.update("planets", values, "id=" + id, null);
        db.close();
    }

    //TODO Asignar imagen según el tipo de planeta
    protected String getImagePlanet(Context context, Integer type) {
        Resources res = context.getResources();
        String[] imagePlanet = res.getStringArray(R.array.image_planet);
        return imagePlanet[type-1];
    }

    public String getIconPlanet(Context context, Integer type) {
        Resources res = context.getResources();
        String[] iconPlanet = res.getStringArray(R.array.icon_planet);
        return iconPlanet[type-1];
    }

/*    public void setPopulation(Context context, Planets planet, Recursos res) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recursos WHERE id='" + res.getId() + "'", null);
        c.moveToFirst();
        int prosperity = c.getInt(3);
        int pop = c.getInt(5);
        Log.i("population", population+"");
        int crecimiento = 0;
        if(this.population == 0){
            try {
                crecimiento = POPULATION_INIT / (prosperity - ( pop / 4));
            }
            catch(Exception e) {
                crecimiento = 0;
            }
            this.population = crecimiento;
        }
        Log.i("crecimiento", crecimiento+"");
        c.close();
        decPopulation();
        ContentValues values = new ContentValues();
        values.put(DBPlanets.COLUMN_POPULATION, population);

        db.update("planets", values, "id=" + planet.getId(), null);
        db.close();
    }*/

    public void decPopulation() {
        this.population = population - 1;
    }

    public String getNameType(int type){
        switch (type){
            case 1: return "Yermo";
            case 2: return "Primordial";
            case 3: return "Agradable";
            case 4: return "Edén";
            case 5: return "Mineral";
            case 6: return "Supermineral";
            case 7: return "Capilla";
            case 8: return "Catedral";
            case 9: return "Especial";
            case 10: return "Abundante";
            case 11: return "Cornucopia";
            default: return null;
        }
    }

    public String getNameSize(int size){
        switch (size){
            case 1: return "Enano";
            case 2: return "Pequeño";
            case 3: return "Medio";
            case 4: return "Grande";
            case 5: return "Enorme";
            default: return null;
        }
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getStar() {
        return star;
    }
    public Integer getSize() {
        return size;
    }
    public Integer getType() {
        return type;
    }
    public int getPopulation() { return population; }
    public String getOwner() {
        return owner;
    }
    public int getExplore() { return explore; }
    public int getOrigin() { return origin; }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStar(Integer star) {
        this.star = star;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}

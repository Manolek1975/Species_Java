package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recursos implements Serializable, IRecursos{

    int id;
    int planet;
    int industry = 0;
    int prosperity = 0;
    int research = 0;
    int population = 0;
    int maxPopulation;
    int shield;
    int defence;
    int offence;

    Builds build;

    public Recursos() {
        super();
    }

    public Recursos(int id, int planet, int industry, int prosperity, int research,
                    int population, int maxPopulation, int shield, int defence, int offence) {
        this.id = id;
        this.planet = planet;
        this.industry = industry;
        this.prosperity = prosperity;
        this.research = research;
        this.population = population;
        this.maxPopulation = maxPopulation;
        this.shield = shield;
        this.defence = defence;
        this.offence = offence;
    }

    public Recursos(int industry, int prosperity, int research, int population) {
        this.industry = industry;
        this.prosperity = prosperity;
        this.research = research;
        this.population = population;
    }

    @Override
    public void increaseRecursos(Context context, Planets planet, Builds build, int color) {
        int incRed = 0, incGreen = 0, incBlue = 0;
        Integer[] red = {1,2,7,8,12,31,32};
        Integer[] green = {1,3,7,9,11};
        Integer[] blue = {1,4,7,10,11,12,32};

        Boolean val = Arrays.asList(red).contains(build.getId());
        switch (color){
            case Color.RED: if(Arrays.asList(red).contains(build.getId())) incRed = 1; break;
            case Color.GREEN: if(Arrays.asList(green).contains(build.getId())) incGreen = 1; break;
            case Color.BLUE: if(Arrays.asList(blue).contains(build.getId())) incBlue = 1; break;
        }
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c1 = db.rawQuery("SELECT * FROM recursos WHERE planet=" + planet.getId(), null);
        c1.moveToFirst();
        id = c1.getInt(0);
        industry = c1.getInt(2);
        prosperity = c1.getInt(3);
        research = c1.getInt(4);
        population = c1.getInt(5);
        c1.close();

        Cursor c2 = db.rawQuery("SELECT * FROM builds WHERE name=?",
                new String[]{build.getName()});
        c2.moveToFirst();
        int buildIndustry = c2.getInt(6);
        int buildProsperity = c2.getInt(7);
        int buildResearch = c2.getInt(8);
        int buildPopulation = c2.getInt(9);
        c2.close();

        ContentValues values = new ContentValues();
        values.put(DBRecursos.COLUMN_INDUSTRY, buildIndustry + industry + incRed);
        values.put(DBRecursos.COLUMN_PROSPERITY, buildProsperity + prosperity + incGreen);
        values.put(DBRecursos.COLUMN_RESEARCH, buildResearch + research + incBlue);
        values.put(DBRecursos.COLUMN_POPULATION, buildPopulation + population);

        db.update("recursos", values, "id=" + id, null);
        db.close();
    }

    @Override
    public List<Recursos> getRecursos(Context context, Planets planet) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recursos WHERE planet=" + planet.getId(), null);

        List<Recursos> recursos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Recursos res = new Recursos(
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            );
            recursos.add(res);
        }
        cursor.close();
        db.close();
        return recursos;
    }

    @Override
    public Recursos getRecursosByPlanet(Context context, Planets planet) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recursos WHERE planet=?",
                new String[] { planet.getName() });
        cursor.moveToFirst();
            Recursos res = new Recursos(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            );
        cursor.close();
        db.close();
        return res;
    }

    @Override
    public void insertRecursos(Context context, Planets planet) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        int room = getRoom(planet.getSize());
        ContentValues values = new ContentValues();
        values.put(DBRecursos.COLUMN_PLANET, planet.getId());
        values.put(DBRecursos.COLUMN_INDUSTRY, 1);
        values.put(DBRecursos.COLUMN_PROSPERITY, 1);
        values.put(DBRecursos.COLUMN_RESEARCH, 0);
        values.put(DBRecursos.COLUMN_POPULATION, 2);
        values.put(DBRecursos.COLUMN_MAXPOPULATION, room);
        values.put(DBRecursos.COLUMN_SHIELD, 0);
        values.put(DBRecursos.COLUMN_DEFENCE, 1);
        values.put(DBRecursos.COLUMN_OFFENCE, 0);
        // Inserta una nueva fila con los valores de la key
        db.insert(DBRecursos.TABLE_NAME, null, values);
        db.close();
    }

    public int getRoom(int size) {
        int room = 0;
        switch (size){
            case 1: room = 7; break;
            case 2: room = 12; break;
            case 3: room = 25; break;
            case 4: room = 45; break;
            case 5: room = 72; break;
        }
        return room;
    }

    public int getId() { return id; }
    public int getPlanet() { return planet; }
    public int getIndustry() {
        return industry;
    }
    public int getPopulation() {
        return population;
    }
    public int getMaxPopulation() {
        return maxPopulation;
    }
    public void setPlanet(int planet) {
        this.planet = planet;
    }
    public void setIndustry(int industry) {
        this.industry = industry;
    }
    public void setPopulation(int population) {
        this.population = population;
    }
    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }
    public int getProsperity() {
        return prosperity;
    }
    public void setProsperity(int prosperity) {
        this.prosperity = prosperity;
    }
    public int getResearch() {
        return research;
    }
    public void setResearch(int research) {
        this.research = research;
    }
}

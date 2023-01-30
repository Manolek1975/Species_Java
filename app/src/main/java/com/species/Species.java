package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Species implements ISpecies {

    private int id;
    private String name;
    private String desc;
    private String image;
    private String skill;
    private int type;

    public Species () { super(); }
    public Species(int id, String name, String desc, String image, String skill, int type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.skill = skill;
        this.type = type;
    }

    @Override
    public List<Species> getSpecies(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM species", null);
        List<Species> speciesList = new ArrayList<>();
        while (c.moveToNext()) {
            Species specie = new Species(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getInt(5)
            );
            speciesList.add(specie);
        }
        c.close();
        db.close();
        return speciesList;
    }

    @Override
    public void setMainSpecie(Context context, int id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues newVal = new ContentValues();
        newVal.put("type", 1);
        db.update("species", newVal, "id=?", new String[] { String.valueOf(id) });

        // Asigna un estrella aleatoria a la especie
        Random rand = new Random();
        ContentValues values = new ContentValues();
        int count = (int) DatabaseUtils.queryNumEntries(db, "species");
        int starId = rand.nextInt(count) + 1;
        values.put(DBStars.COLUMN_EXPLORE, true);
        db.update("stars", values,"id=" + starId, null);
        db.close();
    }

    @Override
    public Species getMainSpecie(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM species WHERE type=1",null);
        c.moveToNext();
        Species specie = new Species();
            specie.setId(c.getInt(0));
            specie.setName(c.getString(1));
            specie.setDesc(c.getString(2));
            specie.setImage(c.getString(3));
            specie.setSkill(c.getString(4));
            specie.setType(c.getInt(5));
        c.close();
        db.close();
        return specie;
    }

    public Species getSpecieByName(Context context, String name) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM species WHERE name=?",
                new String[] { name });
        c.moveToNext();
        Species specie = new Species();
            specie.setId(c.getInt(0));
            specie.setName(c.getString(1));
            specie.setDesc(c.getString(2));
            specie.setImage(c.getString(3));
            specie.setSkill(c.getString(4));
            specie.setType(c.getInt(5));
        c.close();
        db.close();
        return specie;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public String getSkill() {
        return skill;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setType(int type) {
        this.type = type;
    }


}

package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private int star;

    public Species () { super(); }
    public Species(int id, String name, String desc, String image, String skill, int type, int star) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.skill = skill;
        this.type = type;
        this.star = star;
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
                    c.getInt(5),
                    c.getInt(6)
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
        // Busca planetas tipo Agradable
        Cursor c = db.rawQuery("SELECT * FROM planets WHERE type=? AND size>?", new String[] { "3", "2" });
        c.moveToFirst();
        // Recoge el primer planeta del rawquery
        int idPlanet = c.getInt(0);
        int starId = c.getInt(1);
        // update owner y explore en planetas
        newVal = new ContentValues();
        newVal.put("explore", 1);
        newVal.put("owner", 1);
        db.update("planets", newVal, "id=" + idPlanet, null);
        // update explore en estrellas en la especie
        newVal = new ContentValues();
        newVal.put("type", 1);
        newVal.put("explore", 1);
        db.update("stars", newVal, "id=" + starId, null);
        // update species
        newVal = new ContentValues();
        newVal.put("type", 1);
        newVal.put("star", starId);
        db.update("species", newVal, "id=" + id, null);

        c.close();
        db.close();
    }

    @Override
    public Species getMainSpecie(Context context) {
        return null;
    }

/*    @Override
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
    }*/

    @Override
    public Species getSpecieById(Context context, int id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM species WHERE id=" + id, null);
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

    public int getStar() {
        return star;
    }
}

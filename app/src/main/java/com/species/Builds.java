package com.species;

import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Builds implements Serializable, IBuilds {

    private Integer id;
    private String name;
    private String image;
    private String description;
    private Integer cost;
    private Integer technology;
    private Integer industry;
    private Integer prosperity;
    private Integer research;
    private Integer population;
    private Integer shield;
    private Integer invasion;
    private Integer offense;

    public Builds(){ super();}

    public Builds(int id, String name, String image, String description, Integer cost, Integer technology,
                  Integer industry, Integer prosperity, Integer research, Integer population,
                  Integer shield, Integer invasion, Integer offense) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.cost = cost;
        this.technology = technology;
        this.industry = industry;
        this.prosperity = prosperity;
        this.research = research;
        this.population = population;
        this.shield = shield;
        this.invasion = invasion;
        this.offense = offense;
    }

    public String getImageBuild(Context context, int id){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String buildName;
        Cursor c = db.rawQuery("SELECT * FROM builds WHERE id=" + id, null);
        c.moveToFirst();
             buildName = c.getString(2);
        c.close();
        db.close();
        return buildName;
    }

    @Override
    public List<Builds> getBuilds(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM builds WHERE technology='0'", null);
        List<Builds> buildList = new ArrayList<>();
        while (c.moveToNext()) {
            Builds build = new Builds(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getInt(8),
                    c.getInt(9),
                    c.getInt(10),
                    c.getInt(11),
                    c.getInt(12)
            );
            buildList.add(build);
        }
        c.close();
        db.close();
        return buildList;
    }

    public Builds getBuildById(Context context, int id){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM builds WHERE id=" + id,null);
        c.moveToFirst();
        Builds build = new Builds(
                c.getInt(0),
                c.getString(1),
                c.getString(2),
                c.getString(3),
                c.getInt(4),
                c.getInt(5),
                c.getInt(6),
                c.getInt(7),
                c.getInt(8),
                c.getInt(9),
                c.getInt(10),
                c.getInt(11),
                c.getInt(12)
        );
        c.close();
        db.close();
        return build;
    }

    public void buildClear(Context context) {
        int id = 0;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE turns>0", null);
        if(c.getCount() > 0) {
            c.moveToFirst();
            id = c.getInt(0);
        }

        ContentValues val = new ContentValues();
        val.put("build", NULL);
        val.put("turns", 0);
        db.update("surfaces", val, "id=" + id, null);
        c.close();
        db.close();
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getTechnology() {
        return technology;
    }

    public void setTechnology(Integer technology) {
        this.technology = technology;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getProsperity() {
        return prosperity;
    }

    public void setProsperity(Integer prosperity) {
        this.prosperity = prosperity;
    }

    public Integer getResearch() {
        return research;
    }

    public void setResearch(Integer research) {
        this.research = research;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getShield() {
        return shield;
    }

    public void setShield(Integer shield) {
        this.shield = shield;
    }

    public Integer getInvasion() {
        return invasion;
    }

    public void setInvasion(Integer invasion) {
        this.invasion = invasion;
    }

    public Integer getOffense() {
        return offense;
    }

    public void setOffense(Integer offense) {
        this.offense = offense;
    }
}

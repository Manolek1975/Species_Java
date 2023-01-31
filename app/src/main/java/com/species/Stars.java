package com.species;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Stars implements IStars {

    private int id;
    private String name;
    private int sector;
    private String image;
    private int planets;
    private int jumps;
    private int x;
    private int y;
    private int type;
    private int explore;

    public Stars() { super(); }
    public Stars(int id, String name, int sector, String image, int planets, int jumps, int x, int y, int type, int explore) {
        this.id = id;
        this.name = name;
        this.sector = sector;
        this.image = image;
        this.planets = planets;
        this.jumps = jumps;
        this.x = x;
        this.y = y;
        this.type = type;
        this.explore = explore;
    }

    public List<Stars> getStars (Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        //Cursor c = db.rawQuery("SELECT * FROM stars WHERE explore=true", null);
        Cursor c = db.rawQuery("SELECT * FROM stars", null);
        List<Stars> starList = new ArrayList<>();
        while (c.moveToNext()) {
            Stars star = new Stars(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getString(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getInt(8),
                    c.getInt(9)
            );
            starList.add(star);
        }
        c.close();
        db.close();
        return starList;
    }

    @Override
    public Stars getStarById(Context context, int id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM stars WHERE id=" + id, null);
        c.moveToFirst();
        Stars star = new Stars(
                c.getInt(0),
                c.getString(1),
                c.getInt(2),
                c.getString(3),
                c.getInt(4),
                c.getInt(5),
                c.getInt(6),
                c.getInt(7),
                c.getInt(8),
                c.getInt(9)
        );
        c.close();
        db.close();
        return star;
    }

    public Stars getMainStar(Context context) {
        Stars star = new Stars();
        return star;
    }

    public void setMainStar(Context context) {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExplore() {
        return explore;
    }

    public void setExplore(int explore) {
        this.explore = explore;
    }

    public int getPlanets() {
        return planets;
    }

    public void setPlanets(int planets) {
        this.planets = planets;
    }

    public int getJumps() {
        return jumps;
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }


}

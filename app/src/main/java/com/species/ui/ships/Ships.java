package com.species.ui.ships;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.species.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Ships implements IShips {

    private int id;
    private String name;
    private String image;
    private int size;
    private String type;
    private int specie;
    private int star;
    private int planet;
    private int jump;
    private int location;
    private float x;
    private float y;

    public Ships() {
        super();
    }

    public Ships(int id, String name, String image, int size, String type, int specie,
                 int star, int planet, int jump, int location, float x, float y) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.size = size;
        this.type = type;
        this.specie = specie;
        this.star = star;
        this.planet = planet;
        this.jump = jump;
        this.location = location;
        this.x = x;
        this.y = y;
    }

    @Override
    public List<Ships> getOwnShips(Context context, int id) {
        Ships ship;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ships WHERE specie=" +  id, null);
        List<Ships> shipList = new ArrayList<>();
        while(c.moveToNext() && c.getCount() != 0){
            ship = new Ships(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getInt(8),
                    c.getInt(9),
                    c.getInt(10),
                    c.getInt(11)
            );
            shipList.add(ship);
        }
        c.close();
        db.close();
        return shipList;
    }

    @Override
    public List<Ships> getPlanetShips(Context context, int id) {
        Ships ship;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ships WHERE planet=? AND location=1",
                new String[] { String.valueOf(id) });
        List<Ships> shipList = new ArrayList<>();
        while(c.moveToNext() && c.getCount() != 0){
            ship = new Ships(
                c.getInt(0),
                c.getString(1),
                c.getString(2),
                c.getInt(3),
                c.getString(4),
                c.getInt(5),
                c.getInt(6),
                c.getInt(7),
                c.getInt(8),
                c.getInt(9),
                c.getInt(10),
                c.getInt(11)
            );
            shipList.add(ship);
        }
        c.close();
        db.close();
        return shipList;
    }

    @Override
    public List<Ships> getSistemShips(Context context, int id) {
        Ships ship;
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ships WHERE star=? AND location=2",
                new String[] { String.valueOf(id) });
        List<Ships> shipList = new ArrayList<>();
        while(c.moveToNext() && c.getCount() != 0){
            ship = new Ships(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getInt(7),
                    c.getInt(8),
                    c.getInt(9),
                    c.getInt(10),
                    c.getInt(11)
            );
            shipList.add(ship);
        }
        c.close();
        db.close();
        return shipList;
    }

    @Override
    public void updateShipXY(Context context, float x, float y, int id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("x", x);
        values.put("y", y);
        db.update("ships", values, "id=" + id, null);
        db.close();
    }

    @Override
    public void updateShipLocation(Context context, int id, int location) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("location", location);
        db.update("ships", values, "id=" + id, null);
        db.close();
    }

    public String getTextLocation(Context context, int loc){
        String text = "";
        switch(loc){
            case 1: text = "Planeta"; break;
            case 2: text = "Sistema"; break;
            case 3: text = "Hiperespacio"; break;
        }
        return text;
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

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public int getStar() {
        return star;
    }

    public int getPlanet() {
        return planet;
    }

    public int getJump() {
        return jump;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getLocation() {
        return location;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

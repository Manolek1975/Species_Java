package com.species;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Surfaces implements Serializable {

    private Integer id;
    private String planet;
    private String build;
    private Integer turns = -1;
    private Integer x;
    private Integer y;
    private Integer color;
    private int turn;

    public Surfaces(){ super();}

    public Surfaces(int id, String planet, String build, int turns, int x, int y, int color) {
        this.id = id;
        this.planet = planet;
        this.build = build;
        this.turns = turns;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public List<Point> getSquares(Context context, int size){
        Resources res = context.getResources();
        List<Point> coords = new ArrayList<>();
        List<String> squares;
        int resPlanet = 0;
        int x, y;

        switch (size){
            case 1:
                resPlanet = R.array.squares_tiny; break;
            case 2:
                resPlanet = R.array.squares_small; break;
            case 3:
                resPlanet = R.array.squares_medium; break;
            case 4:
                resPlanet = R.array.squares_large; break;
            case 5:
                resPlanet = R.array.squares_enormous; break;
        }
        squares = new ArrayList<>(Arrays.asList(res.getStringArray(resPlanet)));
        for(String val : squares){
            String[] split = val.split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            Point point = new Point(x,y);
            coords.add(point);
        }
        return coords;
    }

    public Paint setSquareColor(Paint color, int type){
        Range<Integer> rangoBlack;
        Range<Integer> rangoWhite;
        Range<Integer> rangoRed;
        Range<Integer> rangoGrenn;
        Range<Integer> rangoBlue;
        Random rand = new Random();
        int r = rand.nextInt(100)+1;
        int x = 0;

        switch (type) {
            case 1:
                // Husk 100, 0, 0, 0, 0
                color.setColor(Color.BLACK);
                break;
            case 2:
                // Primordial 50, 44, 2, 2, 2
                rangoBlack = Range.create(x, x + 50);
                rangoWhite = Range.create(x + 51,x + 94);
                rangoRed = Range.create(x + 95,x + 96);
                rangoGrenn = Range.create(x + 97,x + 98);
                rangoBlue = Range.create(x + 99,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 3:
                // Agradable 20, 69, 3, 5, 3
                rangoBlack = Range.create(x, x + 20);
                rangoWhite = Range.create(x + 21,x + 89);
                rangoRed = Range.create(x + 90,x + 92);
                rangoGrenn = Range.create(x + 93,x + 97);
                rangoBlue = Range.create(x + 98,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 4:
                // Edén 0, 74, 3, 20, 3
                rangoWhite = Range.create(x,x + 74);
                rangoRed = Range.create(x + 75,x + 77);
                rangoGrenn = Range.create(x + 78,x + 97);
                rangoBlue = Range.create(x + 98,x + 100);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 5:
                // Mineral 40, 46, 10, 2, 2
                rangoBlack = Range.create(x, x + 40);
                rangoWhite = Range.create(x + 41,x + 86);
                rangoRed = Range.create(x + 87,x + 96);
                rangoGrenn = Range.create(x + 97,x + 98);
                rangoBlue = Range.create(x + 99,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 6:
                // Supermineral 20, 56, 20, 2, 2
                rangoBlack = Range.create(x, x + 20);
                rangoWhite = Range.create(x + 21,x + 76);
                rangoRed = Range.create(x + 77,x + 96);
                rangoGrenn = Range.create(x + 97,x + 98);
                rangoBlue = Range.create(x + 99,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 7:
                // Capilla 40, 46, 2, 2, 10
                rangoBlack = Range.create(x, x + 40);
                rangoWhite = Range.create(x + 41,x + 86);
                rangoRed = Range.create(x + 87,x + 88);
                rangoGrenn = Range.create(x + 89,x + 90);
                rangoBlue = Range.create(x + 91,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 8:
                // Catedral 20, 54, 3, 3, 20
                rangoBlack = Range.create(x, x + 20);
                rangoWhite = Range.create(x + 21,x + 74);
                rangoRed = Range.create(x + 75,x + 76);
                rangoGrenn = Range.create(x + 77,x + 78);
                rangoBlue = Range.create(x + 79,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 9:
                // Especial 40, 30, 10, 10, 10
                rangoBlack = Range.create(x, x + 40);
                rangoWhite = Range.create(x + 41,x + 70);
                rangoRed = Range.create(x + 71,x + 80);
                rangoGrenn = Range.create(x + 81,x + 90);
                rangoBlue = Range.create(x + 91,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 10:
                // Abundante 20, 35, 15, 15, 15
                rangoBlack = Range.create(x, x + 20);
                rangoWhite = Range.create(x + 21,x + 55);
                rangoRed = Range.create(x + 56,x + 70);
                rangoGrenn = Range.create(x + 71,x + 85);
                rangoBlue = Range.create(x + 86,x + 100);
                if(rangoBlack.contains(r)) color.setColor(Color.BLACK);
                if(rangoWhite.contains(r)) color.setColor(Color.WHITE);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
            case 11:
                // Cornucopia 0, 0, 33, 33, 33
                rangoRed = Range.create(x,x + 33);
                rangoGrenn = Range.create(x + 34,x + 67);
                rangoBlue = Range.create(x + 68,x + 100);
                if(rangoRed.contains(r)) color.setColor(Color.RED);
                if(rangoGrenn.contains(r)) color.setColor(Color.GREEN);
                if(rangoBlue.contains(r)) color.setColor(Color.BLUE);
                break;
        }

        return color;

    }

    public List<Surfaces> getBuildings(Context context, String planet){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE planet='" + planet + "' AND build NOT NULL", null);
        List<Surfaces> buildList = new ArrayList<>();
        while (c.moveToNext()) {
            Surfaces surface = new Surfaces(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6)
            );
            buildList.add(surface);
        }
        c.close();
        db.close();

        return buildList;
    }

    public boolean getOrigin(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE build='Colonia Base'", null);
        if (c.moveToFirst() && c.getCount() > 0) {
            return true;
        }
        c.close();
        db.close();
        return false;
    }

    public List<Surfaces> getSurfaces(Context context, String planet) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM surfaces WHERE planet='" + planet + "'", null);
        List<Surfaces> surfaceList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Surfaces surface = new Surfaces(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
            );
            surfaceList.add(surface);
        }
        cursor.close();
        db.close();
        return surfaceList;
    }

    public void setSquares(Context context, Surfaces surface) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE planet='" + surface.planet + "'", null);
        ContentValues values = new ContentValues();
        values.put(DBSurfaces.COLUMN_PLANET, surface.planet );
        values.put(DBSurfaces.COLUMN_BUILD, surface.build );
        values.put(DBSurfaces.COLUMN_X, surface.x );
        values.put(DBSurfaces.COLUMN_Y, surface.y );
        values.put(DBSurfaces.COLUMN_COLOR, surface.color );

        db.insert(DBSurfaces.TABLE_NAME, null, values);
    }

    public void setBuilding(Context context, Surfaces surface, Builds build) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSurfaces.COLUMN_BUILD, build.getName());
        values.put(DBSurfaces.COLUMN_TURNS, build.getCost());

        db.update("surfaces", values,"id=" + surface.getId(), null);
    }

    public int countBuildings(Context context){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT build FROM surfaces WHERE build NOT NULL", null);
        c.moveToFirst();
            int count = c.getCount();
        c.close();
        db.close();
        return count;
    }

    public List<Surfaces> getTurns(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE turns > 0", null);
        List<Surfaces> surfaceList = new ArrayList<>();
        while(c.moveToNext()) {
                Surfaces surface = new Surfaces(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3),
                        c.getInt(4),
                        c.getInt(5),
                        c.getInt(6)
                );
            surfaceList.add(surface);
        }
        c.close();
        db.close();
        return surfaceList;
    }

    public Surfaces getSurfaceProyecto(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM surfaces WHERE turns > 0", null);
        c.moveToFirst();
        if(c.getCount() == 0) { return null; };
        Surfaces surface = new Surfaces(
                c.getInt(0),
                c.getString(1),
                c.getString(2),
                c.getInt(3),
                c.getInt(4),
                c.getInt(5),
                c.getInt(6));
        c.close();
        db.close();
        return surface;
    }

    public void setCost(Context context, Integer cost) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM surfaces WHERE turns > 0", null);
        c.moveToFirst();
        if(c.getCount() > 0) {
            id = c.getInt(0);
            ContentValues values = new ContentValues();
            values.put(DBSurfaces.COLUMN_TURNS, cost);
            db.update("surfaces", values,"id=" + id, null);
        }
        c.close();
        db.close();
    }

    public int incTurn() {
        turn += 1;
        return turn;
    }

    public int decTurns() {
        turns -= 1;
        return turns;
    }

    public void setTurn(int turn) { this.turn = turn; }
    public int getTurn() { return turn; }
    public void setTurns(int turns) { this.turns = turns; }
    public Integer getTurns() { return turns; }
    public Integer getId() { return id; }
    public String getPlanet() { return planet; }
    public String getBuild() { return build; }
    public Integer getX() { return x; }
    public Integer getY() { return y; }
    public Integer getColor() { return color; }
    public void setPlanet(String planet) { this.planet = planet; }
    public void setBuild(String build) { this.build = build; }
    public void setX(Integer x) { this.x = x; }
    public void setY(Integer y) { this.y = y; }
    public void setColor(int color) { this.color = color; }



}
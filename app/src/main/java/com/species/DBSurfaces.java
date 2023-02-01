package com.species;

public class DBSurfaces {

    private DBSurfaces() {}

    public static final String TABLE_NAME = "surfaces";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLANET = "planet";
    public static final String COLUMN_BUILD = "build";
    public static final String COLUMN_TURNS = "turns";
    public static final String COLUMN_X  = "x";
    public static final String COLUMN_Y  = "y";
    public static final String COLUMN_COLOR  = "color";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSurfaces.TABLE_NAME + " (" +
                    DBSurfaces.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBSurfaces.COLUMN_PLANET + " INTEGER," +
                    DBSurfaces.COLUMN_BUILD + " INTEGER," +
                    DBSurfaces.COLUMN_TURNS + " INTEGER," +
                    DBSurfaces.COLUMN_X + " INTEGER," +
                    DBSurfaces.COLUMN_Y + " INTEGER," +
                    DBSurfaces.COLUMN_COLOR + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBSurfaces.TABLE_NAME;
}

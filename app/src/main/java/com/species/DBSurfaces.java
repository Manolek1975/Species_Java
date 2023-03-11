package com.species;

public class DBSurfaces {

    private DBSurfaces() {}

    public static final String TABLE_NAME = "surfaces";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLANET = "planet";
    public static final String COLUMN_BUILD = "build";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_RESOURCE = "resource";


    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSurfaces.TABLE_NAME + " (" +
                    DBSurfaces.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBSurfaces.COLUMN_PLANET + " INTEGER," +
                    DBSurfaces.COLUMN_BUILD + " INTEGER," +
                    DBSurfaces.COLUMN_COST + " INTEGER," +
                    DBSurfaces.COLUMN_RESOURCE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBSurfaces.TABLE_NAME;
}

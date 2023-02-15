package com.species;

public class DBShips {

    private DBShips() {}

    public static final String TABLE_NAME = "ships";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SISTEMA = "sistema";
    public static final String COLUMN_PLANET = "planet";
    public static final String COLUMN_JUMP = "jump";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";



    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBShips.TABLE_NAME + " (" +
                    DBShips.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBShips.COLUMN_NAME + " INTEGER," +
                    DBShips.COLUMN_SIZE + " INTEGER," +
                    DBShips.COLUMN_TYPE + " INTEGER," +
                    DBShips.COLUMN_SISTEMA + " INTEGER," +
                    DBShips.COLUMN_PLANET + " INTEGER," +
                    DBShips.COLUMN_JUMP + " INTEGER," +
                    DBShips.COLUMN_X + " INTEGER," +
                    DBShips.COLUMN_Y + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBSurfaces.TABLE_NAME;
}

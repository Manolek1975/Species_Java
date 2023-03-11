package com.species.database;

public class DBStars {

    private DBStars() {}

    public static final String TABLE_NAME = "stars";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SECTOR = "sector";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PLANETS  = "planets";
    public static final String COLUMN_JUMPS  = "jumps";
    public static final String COLUMN_X  = "x";
    public static final String COLUMN_Y  = "y";
    public static final String COLUMN_TYPE  = "type";
    public static final String COLUMN_EXPLORE  = "explore";


    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBStars.TABLE_NAME + " (" +
                    DBStars.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBStars.COLUMN_NAME + " INTEGER," +
                    DBStars.COLUMN_SECTOR + " INTEGER," +
                    DBStars.COLUMN_IMAGE + " INTEGER," +
                    DBStars.COLUMN_PLANETS + " INTEGER," +
                    DBStars.COLUMN_JUMPS + " INTEGER," +
                    DBStars.COLUMN_X + " INTEGER," +
                    DBStars.COLUMN_Y + " INTEGER," +
                    DBStars.COLUMN_TYPE + " INTEGER," +
                    DBStars.COLUMN_EXPLORE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBStars.TABLE_NAME;
}

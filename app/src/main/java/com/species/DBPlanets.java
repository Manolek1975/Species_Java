package com.species;

public class DBPlanets {

    private DBPlanets(){}

    public static final String TABLE_NAME = "planets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STAR  = "star";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SIZE  = "size";
    public static final String COLUMN_TYPE  = "type";
    public static final String COLUMN_X  = "x";
    public static final String COLUMN_Y  = "y";
    public static final String COLUMN_POPULATION  = "population";
    public static final String COLUMN_OWNER  = "owner";
    public static final String COLUMN_EXPLORE  = "explore";
    public static final String COLUMN_ORIGIN  = "origin";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBPlanets.TABLE_NAME + " (" +
                    DBPlanets.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBPlanets.COLUMN_STAR + " INTEGER," +
                    DBPlanets.COLUMN_NAME + " INTEGER," +
                    DBPlanets.COLUMN_SIZE + " INTEGER," +
                    DBPlanets.COLUMN_TYPE + " INTEGER," +
                    DBPlanets.COLUMN_X + " INTEGER," +
                    DBPlanets.COLUMN_Y + " INTEGER," +
                    DBPlanets.COLUMN_POPULATION + " INTEGER," +
                    DBPlanets.COLUMN_OWNER + " INTEGER," +
                    DBPlanets.COLUMN_EXPLORE + " INTEGER," +
                    DBPlanets.COLUMN_ORIGIN+ " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBPlanets.TABLE_NAME;

}

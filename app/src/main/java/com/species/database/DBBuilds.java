package com.species.database;

public class DBBuilds {

    public DBBuilds() {}

    public static final String TABLE_NAME = "builds";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_IMAGE  = "image";
    public static final String COLUMN_DESCRIPTION  = "description";
    public static final String COLUMN_COST  = "cost";
    public static final String COLUMN_TECHNOLOGY  = "technology";
    public static final String COLUMN_INDUSTRY  = "industry";
    public static final String COLUMN_PROSPERITY = "prosperity";
    public static final String COLUMN_RESEARCH  = "research";
    public static final String COLUMN_POPULATION  = "population";
    public static final String COLUMN_SHIELD  = "shield";
    public static final String COLUMN_INVASION  = "invasion";
    public static final String COLUMN_OFFENCE  = "offence";

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBBuilds.TABLE_NAME + " (" +
                    DBBuilds.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBBuilds.COLUMN_NAME + " INTEGER," +
                    DBBuilds.COLUMN_IMAGE + " INTEGER," +
                    DBBuilds.COLUMN_DESCRIPTION + " INTEGER," +
                    DBBuilds.COLUMN_COST + " INTEGER," +
                    DBBuilds.COLUMN_TECHNOLOGY + " INTEGER," +
                    DBBuilds.COLUMN_INDUSTRY + " INTEGER," +
                    DBBuilds.COLUMN_PROSPERITY + " INTEGER," +
                    DBBuilds.COLUMN_RESEARCH + " INTEGER," +
                    DBBuilds.COLUMN_POPULATION + " INTEGER," +
                    DBBuilds.COLUMN_SHIELD + " INTEGER," +
                    DBBuilds.COLUMN_INVASION + " INTEGER," +
                    DBBuilds.COLUMN_OFFENCE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBBuilds.TABLE_NAME;
}

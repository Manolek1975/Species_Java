package com.species.database;

public class DBRecursos {

    private DBRecursos() {}

    public static final String TABLE_NAME = "recursos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PLANET  = "planet";
    public static final String COLUMN_INDUSTRY  = "industry";
    public static final String COLUMN_PROSPERITY  = "prosperity";
    public static final String COLUMN_RESEARCH  = "research";
    public static final String COLUMN_POPULATION  = "population";
    public static final String COLUMN_MAXPOPULATION  = "maxpopulation";
    public static final String COLUMN_SHIELD  = "shield";
    public static final String COLUMN_DEFENCE  = "defence";
    public static final String COLUMN_OFFENCE  = "offence";


    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBRecursos.TABLE_NAME + " (" +
                    DBRecursos.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBRecursos.COLUMN_PLANET + " INTEGER," +
                    DBRecursos.COLUMN_INDUSTRY + " INTEGER," +
                    DBRecursos.COLUMN_PROSPERITY + " INTEGER," +
                    DBRecursos.COLUMN_RESEARCH + " INTEGER," +
                    DBRecursos.COLUMN_POPULATION + " INTEGER," +
                    DBRecursos.COLUMN_MAXPOPULATION + " INTEGER," +
                    DBRecursos.COLUMN_SHIELD + " INTEGER," +
                    DBRecursos.COLUMN_DEFENCE + " INTEGER," +
                    DBRecursos.COLUMN_OFFENCE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBRecursos.TABLE_NAME;
}

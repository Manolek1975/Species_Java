package com.species;

public class DBSpecies {

    private DBSpecies() {}

    public static final String TABLE_NAME = "species";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC  = "description";
    public static final String COLUMN_IMAGE  = "image";
    public static final String COLUMN_SKILL  = "skill";
    public static final String COLUMN_TYPE  = "type";
    public static final String COLUMN_STAR  = "star";


    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBSpecies.TABLE_NAME + " (" +
                    DBSpecies.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    DBSpecies.COLUMN_NAME + " INTEGER," +
                    DBSpecies.COLUMN_DESC + " INTEGER," +
                    DBSpecies.COLUMN_IMAGE + " INTEGER," +
                    DBSpecies.COLUMN_SKILL + " INTEGER," +
                    DBSpecies.COLUMN_TYPE + " INTEGER," +
                    DBSpecies.COLUMN_STAR + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBSpecies.TABLE_NAME;

}

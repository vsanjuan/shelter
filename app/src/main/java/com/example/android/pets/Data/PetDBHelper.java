package com.example.android.pets.Data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.Data.PetsContract.Pets;

/**
 * Created by Salvador on 24/10/2016.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Shelter.db";

    private  static final String TEXT_TYPE = " TEXT";
    private  static final String COMMA_SEP = ",";


    private  static  final String SQL_CREATE_ENTRIES =

            "CREATE TABLE " + Pets.TABLE_NAME + "(" +
                    Pets._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    Pets.COLUMN_PET_NAME + TEXT_TYPE + " NOT NULL" + COMMA_SEP +
                    Pets.COLUMN_PET_BREED + TEXT_TYPE + COMMA_SEP +
                    Pets.COLUMN_PET_GENDER + " INTEGER NOT NULL DEFAULT "
                                           + Pets.GENDER_UNKNOWN + COMMA_SEP +
                    Pets.COLUMN_PET_WEIGHT + " INTEGER" + " )";

    private static final String SQL_ADD_COLUMN =
            "ALTER TABLE" + Pets.TABLE_NAME + "ADD COLUMN" + "age INTEGER";



    public PetDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_ADD_COLUMN);


    }


}

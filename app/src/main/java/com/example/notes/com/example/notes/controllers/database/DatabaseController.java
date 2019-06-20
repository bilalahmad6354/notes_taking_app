package com.example.notes.com.example.notes.controllers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseController extends SQLiteOpenHelper {

    // Databse Detail
    public static final String DATABASE_NAME = "notesdatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table's List
    public static final String INPUT_TABLE_NAME_NOTES = "Notes";

    // Columns for INPUT_TABLE_NAME_NOTES
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + INPUT_TABLE_NAME_NOTES + "(" +
                INPUT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                INPUT_COLUMN_Title + " TEXT, " +
                INPUT_COLUMN_Text + " TEXT )"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INPUT_TABLE_NAME_NOTES);
        onCreate(db);
    }

}

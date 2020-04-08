package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "notesDB";
    //struct
    public static final String LOG_TABLE = "login";
    public static final String NOTES_TABLE = "note";
    //login keys
    public static final String KEY_NAME = "name";
    public static final String KEY_PIN = "pin";
    public static final String KEY_ADMIN = "admin";
    //note keys
    public static final String KEY_ID = "_id";
    public static final String KEY_HEAD = "head";
    public static final String KEY_BODY = "body";
    public static final String KEY_CREATOR_NAME = "creatorName";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LOG_TABLE + "(" + KEY_NAME + " text primary key, " + KEY_PIN + " text,"
                + KEY_ADMIN + " integer" + ")");
        db.execSQL("create table " + NOTES_TABLE + "(" + KEY_ID + " integer primary key, " + KEY_HEAD + " text,"
                + KEY_BODY + " text," + KEY_CREATOR_NAME + " text,FOREIGN KEY(" + KEY_CREATOR_NAME + ") REFERENCES " + LOG_TABLE +
                "(" + KEY_NAME + "))");
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, "admin");
        contentValues.put(KEY_PIN, "0000");
        contentValues.put(KEY_ADMIN, 1);
        db.insert(LOG_TABLE, null, contentValues);
        //test
        contentValues.clear();
        contentValues.put(KEY_HEAD, "1");
        contentValues.put(KEY_BODY, "2");
        db.insert(NOTES_TABLE, null, contentValues);
        //
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + NOTES_TABLE);
        db.execSQL("drop table if exists " + LOG_TABLE);

        onCreate(db);
    }
}

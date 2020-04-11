package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbNoteStore implements NoteStore {
    private SQLiteDatabase database;
    public DbNoteStore(SQLiteDatabase database){
        this.database=database;
    }
    @Override
    public List<Map<String, String>> getData(String admin, String name) {
        Cursor queryCursor = null;
        if ("1".equals(admin)) {
            queryCursor = database.query(DbHelper.NOTES_TABLE, new String[]{DbHelper.KEY_ID,
                            DbHelper.KEY_HEAD, DbHelper.KEY_BODY,DbHelper.KEY_DEADLINE_DATE},
                    null, null, null, null, DbHelper.KEY_DEADLINE_DATE);
        } else {
            queryCursor = database.query(DbHelper.NOTES_TABLE, new String[]{DbHelper.KEY_ID,
                            DbHelper.KEY_HEAD, DbHelper.KEY_BODY,DbHelper.KEY_DEADLINE_DATE},
                    DbHelper.KEY_CREATOR_NAME + "='" + name + "'", null, null,
                    null, DbHelper.KEY_DEADLINE_DATE);
        }
        Map<String, String> mapItem = new HashMap<>();
        List <Map<String,String>> tempList = new ArrayList<>();
        if (queryCursor != null) {
            if (queryCursor.moveToFirst()) {
                do {
                    for (String cn : queryCursor.getColumnNames()) {
                        mapItem.put(cn, queryCursor.getString(queryCursor.getColumnIndex(cn)));
                    }
                    tempList.add(mapItem);
                    mapItem = new HashMap<>();
                } while (queryCursor.moveToNext());
            }
            queryCursor.close();
        }
        return tempList;
    }

    @Override
    public void addNote(String headText,String bodyText,String creatorName,Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.KEY_HEAD, headText);
        contentValues.put(DbHelper.KEY_BODY, bodyText);
        contentValues.put(DbHelper.KEY_CREATOR_NAME, creatorName);
        contentValues.put(DbHelper.KEY_DEADLINE_DATE,df.format(date));
        database.insert(DbHelper.NOTES_TABLE, null, contentValues);
    }

    @Override
    public Note getNote(String noteId) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Cursor queryCursor = database.query(DbHelper.NOTES_TABLE, new String[]{DbHelper.KEY_HEAD,
                        DbHelper.KEY_BODY, DbHelper.KEY_DEADLINE_DATE},
                DbHelper.KEY_ID + "=" + noteId,
                null, null, null, null);
        Note result=null;
        if (queryCursor != null) {
            if (queryCursor.moveToFirst()) {
                try {
                    String a=queryCursor.getString(queryCursor.getColumnIndex(DbHelper.KEY_DEADLINE_DATE));
                    result=new Note(queryCursor.getString(queryCursor.getColumnIndex(DbHelper.KEY_HEAD)),
                            queryCursor.getString(queryCursor.getColumnIndex(DbHelper.KEY_BODY)),
                            df.parse(queryCursor.getString(queryCursor.getColumnIndex(DbHelper.KEY_DEADLINE_DATE))).getTime());
                }catch (ParseException e){}
            }
        }
        return result;
    }

    @Override
    public void udpateNote(String headText, String bodyText, String noteId, Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.KEY_HEAD, headText);
        contentValues.put(DbHelper.KEY_BODY, bodyText);
        contentValues.put(DbHelper.KEY_DEADLINE_DATE,df.format(date));
        database.update(DbHelper.NOTES_TABLE, contentValues, DbHelper.KEY_ID +
                "=" + noteId, null);
    }

    @Override
    public void deleteNote(String noteId) {
        database.delete(DbHelper.NOTES_TABLE, DbHelper.KEY_ID + "="
                + noteId, null);
    }
}

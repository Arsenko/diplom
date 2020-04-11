package com.example.notes;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class App extends Application {
    private static DbKeystore keystore;
    private static DbNoteStore noteHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper db = new DbHelper(getApplicationContext());
        SQLiteDatabase database = db.getWritableDatabase();
        keystore = new DbKeystore(database);
        noteHolder = new DbNoteStore(database);
    }

    public static Keystore getKeystore() {
        return keystore;
    }

    public static NoteStore getNoteHolder() {
        return noteHolder;
    }
}

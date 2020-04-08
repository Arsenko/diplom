package com.example.notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterList {
    private final String HEAD = "head";
    private final String BODY = "body";
    private List<Map<String, String>> adapterList;

    public AdapterList(int admin, String name, Context context) {
        getData(admin, name, context);
    }

    @NonNull
    public SimpleAdapter createAdapter(Context context) {
        return new SimpleAdapter(context, adapterList, R.layout.list_item, new String[]{HEAD, BODY}, new int[]{R.id.head, R.id.body});
    }

    @NonNull
    public void getData(int admin, String name, Context context) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor queryCursor = null;
        if (admin == 1) {
            queryCursor = database.query(db.NOTES_TABLE, new String[]{db.KEY_ID, db.KEY_HEAD, db.KEY_BODY},
                    null, null, null, null, null);
        } else {
            queryCursor = database.query(db.NOTES_TABLE, new String[]{db.KEY_ID, db.KEY_HEAD, db.KEY_BODY},
                    db.KEY_CREATOR_NAME + "='" + name + "'", null, null,
                    null, null);
        }
        Map<String, String> mapItem = new HashMap<>();
        adapterList = new ArrayList<>();
        if (queryCursor != null) {
            if (queryCursor.moveToFirst()) {
                do {
                    for (String cn : queryCursor.getColumnNames()) {
                        mapItem.put(cn, queryCursor.getString(queryCursor.getColumnIndex(cn)));
                    }
                    adapterList.add(mapItem);
                    mapItem = new HashMap<>();
                } while (queryCursor.moveToNext());
            }
            queryCursor.close();
        }
        db.close();
    }

    public List<Map<String, String>> getAdapterList() {
        return adapterList;
    }
}

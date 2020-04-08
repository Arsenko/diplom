package com.example.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }

    private void init() {
        final TextView headText = findViewById(R.id.headText);
        final TextView bodyText = findViewById(R.id.bodyText);
        final Button btnSave = findViewById(R.id.btnSave);
        final Intent intentToGet = getIntent();
        final String creatorName = intentToGet.getExtras().getString(DbHelper.KEY_NAME);
        if (intentToGet.getExtras().containsKey(DbHelper.KEY_ID)) {
            final DbHelper db = new DbHelper(getApplicationContext());
            final SQLiteDatabase database = db.getReadableDatabase();
            Cursor queryCursor = database.query(db.NOTES_TABLE, new String[]{db.KEY_HEAD, db.KEY_BODY},
                    db.KEY_ID + "=" + intentToGet.getExtras().getString(db.KEY_ID), null, null, null, null);
            if (queryCursor != null) {
                if (queryCursor.moveToFirst()) {
                    headText.setText(queryCursor.getString(queryCursor.getColumnIndex(db.KEY_HEAD)));
                    bodyText.setText(queryCursor.getString(queryCursor.getColumnIndex(db.KEY_BODY)));
                }
            }
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(db.KEY_HEAD, headText.getText().toString());
                    contentValues.put(db.KEY_BODY, bodyText.getText().toString());
                    database.update(db.NOTES_TABLE, contentValues, db.KEY_ID + "=" + intentToGet.getExtras().getString(db.KEY_ID), null);
                    database.close();
                    finish();
                }
            });
        } else {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbHelper db = new DbHelper(getApplicationContext());
                    SQLiteDatabase database = db.getReadableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(db.KEY_HEAD, headText.getText().toString());
                    contentValues.put(db.KEY_BODY, bodyText.getText().toString());
                    if (creatorName != null) {
                        contentValues.put(db.KEY_CREATOR_NAME, creatorName);
                        database.insert(db.NOTES_TABLE, null, contentValues);
                        database.close();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    public void onDel(MenuItem item) {
        DbHelper db = new DbHelper(getApplicationContext());
        SQLiteDatabase database = db.getReadableDatabase();
        database.delete(db.NOTES_TABLE, db.KEY_ID + "=" + getIntent().getExtras().getString(db.KEY_ID), null);
        database.close();
        finish();
    }
}

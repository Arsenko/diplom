package com.example.notes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {
    private int placeholderIndex = 0;
    private ImageView[] placeholders;
    private String cashedPass;
    private String name;
    private EditText loginText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        init();
    }

    private void init() {
        loginText = findViewById(R.id.editText);
        placeholders = new ImageView[]{findViewById(R.id.placeholder0), findViewById(R.id.placeholder1), findViewById(R.id.placeholder2), findViewById(R.id.placeholder3)};
    }

    public void clickNumber(View v) {
        if (placeholderIndex == 0) {
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass = ((Button) v).getText().toString();
            placeholderIndex++;
        } else if (placeholderIndex < 3) {
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass += ((Button) v).getText().toString();
            placeholderIndex++;
        } else {
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass += ((Button) v).getText().toString();
            name = loginText.getText().toString();
            DbHelper db = new DbHelper(getApplicationContext());
            SQLiteDatabase database = db.getWritableDatabase();
            Cursor queryCursor = database.query(db.LOG_TABLE, new String[]{db.KEY_ADMIN},
                    db.KEY_NAME + "='" + name + "' and " + db.KEY_PIN + "='" + cashedPass + "'",
                    null, null, null, null);
            if (queryCursor != null) {
                if (queryCursor.moveToFirst()) {
                    Intent toMain = new Intent(this, MainActivity.class);
                    toMain.putExtra(db.KEY_NAME, name);
                    toMain.putExtra(db.KEY_ADMIN, queryCursor.getInt(queryCursor.getColumnIndex(db.KEY_ADMIN)));
                    startActivity(toMain);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.title_dialog);
                    builder.setPositiveButton(getString(R.string.answer_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbHelper db = new DbHelper(getApplicationContext());
                            SQLiteDatabase database = db.getReadableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(db.KEY_NAME, name);
                            contentValues.put(db.KEY_PIN, cashedPass);
                            contentValues.put(db.KEY_ADMIN, 0);
                            database.insert(db.LOG_TABLE, null, contentValues);
                            cashedPass = "";
                            Toast.makeText(getApplicationContext(), getString(R.string.dialog_toast), Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.answer_no), null);
                    builder.create().show();
                    clickC(null);
                }
            }
            queryCursor.close();
        }
    }

    public void clickC(View v) {
        for (int i = placeholderIndex; i > -1; i--) {
            placeholders[i].setImageDrawable(getDrawable(R.drawable.placeholder_none));
        }
        placeholderIndex = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        clickC(null);
        loginText.setText("");
    }
}

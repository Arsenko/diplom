package com.example.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {
    private int placeholderIndex=0;
    private ImageView[] placeholders;
    private String cashedPass;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        init();
    }

    private void init(){
        placeholders=new ImageView[]{findViewById(R.id.placeholder0),findViewById(R.id.placeholder1),findViewById(R.id.placeholder2),findViewById(R.id.placeholder3)};
    }

    public void clickNumber(View v){
        if(placeholderIndex==0){
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass=((Button) v).getText().toString();
            placeholderIndex++;
        }else if(placeholderIndex<3) {
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass+= ((Button) v).getText().toString();
            placeholderIndex++;
        }else{
            cashedPass+= ((Button) v).getText().toString();
            name=((EditText)findViewById(R.id.editText)).getText().toString();
            DbHelper db=new DbHelper(getApplicationContext());
            SQLiteDatabase database=db.getWritableDatabase();
            Cursor queryCursor=database.query(db.LOG_TABLE,new String[]{db.KEY_ADMIN},
                    db.KEY_NAME+"='"+name+"' and "+db.KEY_PIN+"='"+cashedPass+"'",
                    null,null,null,null );
            if(queryCursor!=null){
                if(queryCursor.moveToFirst()) {
                    Intent toMain = new Intent(this, MainActivity.class);
                    toMain.putExtra(db.KEY_NAME, name);
                    toMain.putExtra(db.KEY_ADMIN, cashedPass);
                    startActivity(toMain);
                }
            }
        }
    }

    public void clickC(View v){
        for(int i=placeholderIndex;i>-1;i--){
            placeholders[i].setImageDrawable(getDrawable(R.drawable.placeholder_none));
        }
        placeholderIndex=0;
    }
}

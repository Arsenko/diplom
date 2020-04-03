package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        Button btnAdd=findViewById(R.id.btnAdd);
        ListView notesList=findViewById(R.id.notesList);
        AdapterList adapterList=new AdapterList(getIntent().getExtras().getInt(DbHelper.KEY_ADMIN),getIntent().getExtras().getString(DbHelper.KEY_NAME),this);
        SimpleAdapter adapter=adapterList.createAdapter(this);
        notesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

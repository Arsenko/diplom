package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {
    private SimpleAdapter adapter;
    private AdapterList adapterList;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button btnAdd = findViewById(R.id.btnAdd);
        ListView notesList = findViewById(R.id.notesList);
        name = getIntent().getExtras().getString(AppConstants.name);
        adapterList = new AdapterList(this,getIntent().getExtras().getString(AppConstants.admin),name);
        adapter = adapterList.createAdapter();
        notesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editNote = new Intent(MainActivity.this, AddActivity.class);
                editNote.putExtra(AppConstants.noteId, adapterList.getAdapterList().get(position).get(DbHelper.KEY_ID));
                startActivity(editNote);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(MainActivity.this, AddActivity.class);
                addNote.putExtra(AppConstants.name, name);
                startActivity(addNote);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            init();
        }
    }
}

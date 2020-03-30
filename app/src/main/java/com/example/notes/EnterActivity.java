package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {
    private int placeholderIndex=0;
    private ImageView[] placeholders;
    private String cashedPass;

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
        if(placeholderIndex<3) {
            placeholders[placeholderIndex].setImageDrawable(getDrawable(R.drawable.placeholder_process));
            cashedPass+= ((Button) v).getText().toString();
            placeholderIndex++;
        }else{
            cashedPass+= ((Button) v).getText().toString();
            //заглушка !!!!!!!!!!!!!!!!!!!!!
            if(cashedPass.equals("0000")){
                Intent toMain=new Intent(this,MainActivity.class);
                startActivity(toMain);
            }
            //
        }
    }
}

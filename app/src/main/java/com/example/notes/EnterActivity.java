package com.example.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
            final Keystore keystore = App.getKeystore();
            if (keystore.userExists(name)) {
                String isAdmin = keystore.isAdmin(name, cashedPass);
                if(isAdmin!=null) {
                    Intent toMain = new Intent(this, MainActivity.class);
                    toMain.putExtra(AppConstants.name, name);
                    toMain.putExtra(AppConstants.admin, isAdmin);
                    startActivity(toMain);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
                    clickC(null);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.title_dialog);
                builder.setPositiveButton(getString(R.string.answer_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        keystore.newUser(name, cashedPass);
                        cashedPass = "";
                        Toast.makeText(getApplicationContext(), getString(R.string.dialog_toast), Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton(getString(R.string.answer_no), null);
                builder.create().show();
                clickC(null);
            }
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

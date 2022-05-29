package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        Button btn = (Button)findViewById(R.id.button1);
        TextView t1 = (TextView)findViewById(R.id.text1);
        EditText e1 = (EditText)findViewById(R.id.etext1);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Editable new1 = e1.getText();
                t1.setText(new1);
                Toast.makeText(MainActivity.this, getString(R.string.toast), Toast.LENGTH_SHORT).show();
            }
        });
        CheckBox c1 = (CheckBox)findViewById(R.id.cb1);
        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (c1.isChecked()){
                    Snackbar.make(c1, getString(R.string.csnack), Snackbar.LENGTH_LONG)
                            .setAction("Undo", click -> c1.setChecked(false))
                            .show(); }
                else{
                    Snackbar.make(c1, getString(R.string.csnack2), Snackbar.LENGTH_LONG)
                            .setAction("Undo", click -> c1.setChecked(true))
                            .show();
                }

            }
        });



    }
}
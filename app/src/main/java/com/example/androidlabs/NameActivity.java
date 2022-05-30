package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        SharedPreferences pref = getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String name = pref.getString("savedName", "");
        TextView t2 = (TextView)findViewById(R.id.t2);
        String welcome = getString(R.string.welcome);
        t2.setText(welcome + " " + name + "!");
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);
        b2.setOnClickListener(new View.OnClickListener() //don't call me that - set result to 0 and return
        {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();

            }
        });
        b3.setOnClickListener(new View.OnClickListener()  //Thank you - set result to 1 and return
        {
            @Override
            public void onClick(View v) {

                setResult(1);
                finish();
            }
        });

    }

    public NameActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
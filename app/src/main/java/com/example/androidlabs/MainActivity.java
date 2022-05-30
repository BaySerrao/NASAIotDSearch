package com.example.androidlabs;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.b1);
        EditText e1 = (EditText)findViewById(R.id.e1);
        TextView t1 = (TextView)findViewById(R.id.t1);
        SharedPreferences pref = getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String name = pref.getString("savedName", "");
        e1.setText(name);
        final String input1;


        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Editable input1 = e1.getText();
                Intent nextPage = new Intent(MainActivity.this, NameActivity.class);
                nextPage.putExtra("input", input1);

                editor.putString("savedName", input1.toString());
                editor.commit();
                startActivityForResult(nextPage, 2);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        EditText e1 = (EditText)findViewById(R.id.e1);
        editor.putString("savedName", e1.getText().toString());
        editor.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == 1){
            finish();
    }
}
}
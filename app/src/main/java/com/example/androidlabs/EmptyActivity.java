package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


    }
    public void doThing(){
       // DetailsFragment fraggy = new DetailsFragment();
       // FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().add(R.id.frame,fraggy).commit();
        //getSupportFragmentManager().beginTransaction()
             //   .add(R.id.frame, new DetailsFragment()).commit();

        ft.replace(R.id.frame, new DetailsFragment());
        ft.commit();
    }
}
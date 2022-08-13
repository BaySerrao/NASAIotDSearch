package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar tool = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tool);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.d1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tool,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),R.string.toast1,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.aboutmessage)
                        .setTitle(R.string.helpmenutitle)
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int d){
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
                break;
        }
        return false;
    }
    boolean onNavigationItemSelected(MenuItem item){
        Toolbar tool = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tool);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.d1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tool,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        int id = item.getItemId();
        if (id == R.id.nav1) {
            //launch main activity on home button
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else if (id==R.id.nav2){
            //launch dad joke activity
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        }else if (id==R.id.nav3){
            //call finishAffinity(); to close and exit
            finishAffinity();
            System.exit(0);
        }else if (id==R.id.nav4){
            Intent intent = new Intent(this, Recentdetails.class);
            startActivity(intent);
        }else if (id==R.id.nav5){
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenny, menu);
        return true;
    }
}
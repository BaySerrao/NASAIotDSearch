package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Recentdetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentdetails);
        Toolbar tool = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tool);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.d1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tool,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        Dbopen db = new Dbopen(this);
        ArrayList<Favorite> favoriteArrayList = new ArrayList<Favorite>(db.getFavorites());
        ListAdapter listAdapter = new ListAdapter(this,R.layout.list_item, favoriteArrayList );
        ListView listView = (ListView) findViewById(R.id.favlist);
        details detail = new details();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle passer = new Bundle();
        String passurl, passhdurl, passdate;
        passurl = getIntent().getStringExtra("url");
        passhdurl = getIntent().getStringExtra("hdurl");
        passdate  = getIntent().getStringExtra("date");
        passer.putString("url",passurl);
        passer.putString("hdurl",passhdurl);
        passer.putString("date",passdate);
        detail.setArguments(passer);
        fragmentTransaction.replace(R.id.detaillayout, detail).commit();
        Log.d(Recentdetails.class.getName(), "url:" + passurl);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),R.string.toast1,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.recentmessage)
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
            //launch favorites
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        }else if (id==R.id.nav3){
            //call finishAffinity(); to close and exit
            finishAffinity();
            System.exit(0);
        }else if (id==R.id.nav4){
            try{
                Intent intent = new Intent(this, Recentdetails.class);
                startActivity(intent);
            }catch (Exception e){}

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
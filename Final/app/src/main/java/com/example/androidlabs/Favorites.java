package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity{

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites);

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
            listView.setAdapter(listAdapter);
            loadlist();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                               int index, long arg3) {
                    alertDialogBuilder.setTitle(getString(R.string.deleteTitle));
                    alertDialogBuilder.setMessage(getString(R.string.deleteBody));
                    alertDialogBuilder.setPositiveButton(getString(R.string.messpos), (click, arg) -> {
                        try{
                            favoriteArrayList.remove(index);
                            removeFromDB(favoriteArrayList.get(index).date);
                            listAdapter.notifyDataSetChanged();
                        } catch (Exception e){

                        }
                    });
                    alertDialogBuilder.setNegativeButton(getString(R.string.messNeg), (click, arg) -> {

                    });
                    alertDialogBuilder.setNeutralButton(getString(R.string.messNeut), (click, arg) -> {
                    try {
                        openFragment(index);
                    }catch (Exception e){

                    }
                    });
                    alertDialogBuilder.create().show();

                    return false;
                }
            });

        }
    private void openFragment(int i){
        Dbopen db = new Dbopen(this);
        ArrayList<Favorite> favoriteArrayList = new ArrayList<Favorite>(db.getFavorites());
        ListAdapter listAdapter = new ListAdapter(this,R.layout.list_item, favoriteArrayList );
        ListView listView = (ListView) findViewById(R.id.favlist);
        details detail = new details();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //passer will be used to pass the url, hdurl, date, and index to the fragment for showing details of the Favorite
        Bundle passer = new Bundle();
        String passurl, passhdurl, passdate;
        int passindex;
        passurl = favoriteArrayList.get(i).url.toString();
        passhdurl = favoriteArrayList.get(i).hdurl.toString();
        passdate  = favoriteArrayList.get(i).date.toString();

        passer.putString("url",passurl);
        passer.putString("hdurl",passhdurl);
        passer.putString("date",passdate);
        passer.putInt("index", i);
        detail.setArguments(passer);
        //Creating Intent
        Intent detailer = new Intent(Favorites.this, Recentdetails.class);
        detailer.putExtras(passer);
        startActivity(detailer);
    }
    @Override
    protected void onStart() {
        super.onStart();
        loadlist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadlist();
    }


    public void removeFromDB(String i){
        //connect
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();

        Log.i("is this working?", "It seems to be getting called" + i);
       // db.delete(Dbopen.tableName, Dbopen.col_id + "= ?", new String[] {Integer.toString(i)});
        db.delete(Dbopen.tableName, Dbopen.col_date + "= ?", new String[]{i});
    }

           public void loadlist(){
        //connect

        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();
        ArrayList<Favorite> favlist;
        favlist = opener.getFavorites();
        ArrayAdapter<Favorite> favadapter = new ArrayAdapter<Favorite>(this, R.layout.list_item, favlist);
        //gather columns
        String[] columns = {Dbopen.col_id, Dbopen.col_url,Dbopen.col_hdurl,  Dbopen.col_date};
        //query results
        Cursor results = db.query(Dbopen.tableName, columns, null, null, null, null, null);
        //column indices
        int col1Index = results.getColumnIndex(opener.col_id);
        int col2Index = results.getColumnIndex(opener.col_url);
        int col3Index = results.getColumnIndex(opener.col_hdurl);
        int col4Index = results.getColumnIndex(opener.col_date);

                while(results.moveToNext()) {
                    String url = results.getString(col2Index);
                    String date = results.getString(col4Index);
                    int id = results.getInt(col1Index);
                    String hdurl = results.getString(col3Index);
                    favadapter.add(new Favorite(id, url, hdurl, date));

                }

                for(int i =0; i<opener.getFavoritesCount(); i++){
                    Favorite fav = favlist.get(i);

                    favadapter.notifyDataSetChanged();
                }
                for (Favorite fv : favlist) {
                    String log = "Id: " + fv.getId() + " ,url: " + fv.geturl() + " ,hdurl: " +
                            fv.getHdurl() + " ,date: " + fv.getDate();
                    //Writing Contacts to log
                    Log.d("Favorite: ", log);
                }
                favadapter.notifyDataSetChanged();
            }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),R.string.toast1,Toast.LENGTH_SHORT).show();
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
                dialog.setMessage(R.string.favoritehelpmessage)
                        .setTitle(R.string.helpmenutitle)
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int d){
                                dialogInterface.cancel();
                            }
                        });
                android.app.AlertDialog alert = dialog.create();
                alert.show();
                break;
            //case R.id.item2:
            //  Toast.makeText(getApplicationContext(),R.string.toast2,Toast.LENGTH_SHORT).show();
            //  break;

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
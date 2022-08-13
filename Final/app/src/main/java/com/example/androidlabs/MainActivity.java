package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //SharedPrefs
    //Boolean to handle enabling the favorite button and mitigate addition of default text to database
    Boolean searchUsed = false;
    private static String formattedDate;
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sharedPreferences
        loadSharedPrefs();
        //define ProgressBar
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progbar);
        //defining the hidden textview used for the hdurl
        TextView hd = (TextView) findViewById(R.id.hidhdurl);
        //define the calendar
        EditText dp = (EditText) findViewById(R.id.dp1);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //onclick for edittext for datepicker
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = year+"-"+month+"-"+dayOfMonth;
                        dp.setText(date);

                    }
                },year, month,day);
                dialog.show();

            }
        });
        //defining the toolbar and navdrawer
        Toolbar tool = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tool);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.d1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tool,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        //Textview allocation for onclick on URL textview from main activity
        TextView clickableURL = (TextView) findViewById(R.id.texturl);
        //creation of onclicklistener for clickableURL
                clickableURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //This should take the value of the URL textview and open it within the device's built-in browser
                        //commenting out original url user because assignment wants us to show url but open hdurl. Uncomment this and comment
                        // out the first 2 lines of the try block to use normal url
                        //String url = new String();
                        //url = clickableURL.getText().toString();
                        progressBar.setProgress(0);
                        try {

                            //hdurl parser from hidden textview
                            TextView hd = (TextView) findViewById(R.id.hidhdurl);
                            TextView url = (TextView) findViewById(R.id.texturl);
                            String hdurl = hd.getText().toString();
                            String useurl = url.getText().toString();
                            if(hdurl.equals("")) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(useurl));
                                startActivity(browserIntent);
                            }else{
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hdurl));
                                startActivity(browserIntent);
                            }

                        }catch (Exception E){
                        }
                    }
                });
        //Button allocation for Search Button
        Button searchButton = (Button) findViewById(R.id.search_button);
        //Date Textview
        TextView datetext = (TextView) findViewById(R.id.textdate);
        //Onclicklistener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this should grab the date from the datepicker. Then it will
                // populate the date textview, start an asynctask to search the API on the
                // specified date, then update the url textview and hdurl
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                formattedDate = sdf.format(calendar.getTime());
                Parser parser = new Parser();
                parser.execute();
                progressBar.setProgress(0);
                progressBar.setProgress(10);
                progressBar.setProgress(20);
                progressBar.setProgress(30);
                progressBar.setProgress(40);
                progressBar.setProgress(50);
                progressBar.setProgress(60);
                progressBar.setProgress(70);
                progressBar.setProgress(80);
                progressBar.setProgress(90);
                progressBar.setProgress(100);
                String comp = getString(R.string.urler);

                if (!clickableURL.getText().toString().equals(comp)) {
                    {
                        searchUsed = true;

                    }

                }
            }

        });

        //allocate favorites button
        Button fav = (Button) findViewById(R.id.favbutton);
        //Database work
        Dbopen db = new Dbopen(this);
        //onclicklistener for favorites button
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this should open the database, then save the details to the database
                //u1 is url, u2 is hdurl, and d1 is date text
                if (searchUsed) {
                    String u1, u2, d1;
                    u1 = clickableURL.getText().toString();
                    u2 = hd.getText().toString();
                    d1 = datetext.getText().toString();
                    db.addtoDB(u1, u2, d1);
                    progressBar.setProgress(0);
                    Log.d("Reading: ", "Reading all favorites...");
                    List<Favorite> favorites = db.getFavorites();
                    for (Favorite fv : favorites) {
                        String log = "Id: " + fv.getId() + " ,url: " + fv.geturl() + " ,hdurl: " +
                                fv.getHdurl() + " ,date: " + fv.getDate();
                        // Writing Contacts to log
                        Log.d("Favorite: ", log);
                    }
                    View view = findViewById(R.id.search_button);
                    String message = "Added to Favorites!";
                    int duration = Snackbar.LENGTH_LONG;
                    showSnackbar(view, message, duration);
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSharedPrefs();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSharedPrefs();
    }
    protected void onPause() {
        super.onPause();
        saveSharedPrefs();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadSharedPrefs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),R.string.toast1,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.helpmenumessage)
                        .setTitle(R.string.helpmenutitle)
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int d){
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
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
    private void log(final String msg)
    {
        final String TAG = MainActivity.class.getName();
        Log.d(TAG, msg);
    }
    private void saveSharedPrefs(){
        log("saveSharedPreferences called.");
        TextView url = (TextView)findViewById(R.id.texturl);
        TextView hdurl = (TextView)findViewById(R.id.hidhdurl);
        TextView date = (TextView)findViewById(R.id.textdate);
        boolean searchstate = searchUsed;
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("url", url.getText().toString());
        editor.putString("hdurl", hdurl.getText().toString());
        editor.putString("date", date.getText().toString());
        editor.putBoolean("searched", searchstate);
        editor.commit();
    }
    private void loadSharedPrefs(){
        log("loadSharedPreferences called.");
        TextView url = (TextView)findViewById(R.id.texturl);
        TextView hdurl = (TextView)findViewById(R.id.hidhdurl);
        TextView date = (TextView)findViewById(R.id.textdate);
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        url.setText(sp.getString("url", getResources().getString(R.string.urler)));
        hdurl.setText(sp.getString("hdurl", "No Value Saved"));
        date.setText(sp.getString("date", getResources().getString(R.string.datesel)));
        searchUsed = sp.getBoolean("searched", false);
    }
    //this class parses the JSON and uses the AsyncTask to pull from the API and parse it for the information required.
    class Parser extends AsyncTask<String, Integer, String> {

        EditText dp = (EditText) findViewById(R.id.dp1);
        String formattedDate = dp.getText().toString();
        String api_url = "https://api.nasa.gov/planetary/apod?api_key=1chD9lSMHznfK02MGiTnfrVdm0yfQqP9huUhshFB&date=";
        String parsedurl, parsedhdurl, parseddate;


        //Define the date to add from the datePicker - needs to be formatted YYYY-MM-DD
        String url_adder = api_url+formattedDate;
        public String gethdurl(){
            return parsedhdurl;
        }
        @Override
        protected String doInBackground(String... strings) {

            //create stringbuilder to pull the JSON from the API
            StringBuilder builder = new StringBuilder();
            //Pull from the API
            try {
                URL url = new URL(url_adder);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (true) {
                    String readLine = reader.readLine();
                    String data = readLine;
                    if (data == null) {
                        break;
                    }
                    builder.append(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            //defining Textview sections to be updated
            TextView t1 = (TextView) findViewById(R.id.texturl);
            TextView t2 = (TextView) findViewById(R.id.textdate);
            TextView t3 = (TextView) findViewById(R.id.hidhdurl);

            try {

                JSONObject jObject = new JSONObject(s.trim());

                jObject.keys().forEachRemaining(k ->
                {
                    try {
                        parsedurl = jObject.get("url").toString();
                        parseddate = jObject.get("date").toString();
                        parsedhdurl = jObject.get("hdurl").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            t1.setText(parsedurl);
            t2.setText(parseddate);
            t3.setText(parsedhdurl);
            super.onPostExecute("done");
        }
    }
}
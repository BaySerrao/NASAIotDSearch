package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    String name, height, mass;
    ArrayList<String> listo;
    ArrayList<String> listo2;
    ArrayList<String> listo3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yoink y1 = new yoink();
        y1.execute();
        lv = findViewById(R.id.lv1);
        EmptyActivity e1 = new EmptyActivity();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                e1.doThing();
                return true;
            }
        });
    }


    class yoink extends AsyncTask<String, Integer, String> {
        String api_url = "https://swapi.dev/api/people/?format=json";
        String name, height, mass;


        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(api_url);
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
            listo = new ArrayList<String>();
            listo2 = new ArrayList<String>();
            listo3 = new ArrayList<String>();
            try {
                JSONObject jobect = new JSONObject(s);
                JSONArray jarray = jobect.getJSONArray("results");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject j1 = jarray.getJSONObject(i);
                    name = j1.getString("name");
                    height = j1.getString("height");
                    mass = j1.getString("mass");
                    listo.add(name);
                    listo2.add(height);
                    listo3.add(mass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ListAdapter adapter = new ArrayAdapter<String>(
                    MainActivity.this,
                    R.layout.list_filler,
                    listo);
            lv.setAdapter(adapter);
            super.onPostExecute("done");
        }
    }
}


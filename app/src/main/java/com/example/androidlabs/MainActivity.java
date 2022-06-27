package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
    ArrayList<HashMap<String,String>> listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yoink y1 = new yoink();
        y1.execute();

        lv = findViewById(R.id.lv1);
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
            ArrayList<HashMap<String,String>> listo = new ArrayList<>();
            try {
                JSONObject jobect = new JSONObject();
                JSONArray jarray = jobect.getJSONArray("results");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject j1 = jarray.getJSONObject(i);
                    name = j1.getString("name");
                    height = j1.getString("height");
                    mass = j1.getString("mass");
                    HashMap<String,String> n1 = new HashMap<>();
                    n1.put("name", name);
                    listo.add(i, n1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    listo,
                    R.layout.activity_main,
                    new String[]{"name"},
                    new int[]{R.id.lv1});
            lv.setAdapter(adapter);
            super.onPostExecute("done");
        }
    }
}


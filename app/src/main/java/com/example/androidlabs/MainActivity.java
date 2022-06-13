package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToDo> toDoArrayList;
    ArrayAdapter<ToDo> arrayAdapter;
    ListAdapter listAdapter;
    ListView lv1;
    EditText et1;
    private Object ToDo;
    ToDo t1;
    Dbopen opener = new Dbopen(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText)findViewById(R.id.et1);
        toDoArrayList =  new ArrayList<ToDo>();
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();
        listAdapter = new ListAdapter(this,android.R.layout.activity_list_item, toDoArrayList);
        lv1 = (ListView)findViewById(R.id.lv1);
        lv1.setAdapter(listAdapter);
        Switch sw1 = (Switch) findViewById(R.id.sw1);
        Button btn = (Button) findViewById(R.id.b1);
        loadFromDB();

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            EditText et1 = (EditText)findViewById(R.id.et1);
            Switch sw1 = (Switch) findViewById(R.id.sw1);
            ToDo newToDo = new ToDo(et1.getText().toString(),sw1.isChecked());
            addToDB(newToDo);
            listAdapter.add(newToDo);
            listAdapter.notifyDataSetChanged();
            et1.setText("");

        }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                alertDialogBuilder.setTitle(getString(R.string.messTitle));
                alertDialogBuilder.setMessage(getString(R.string.messBody) + (index+1));
                alertDialogBuilder.setPositiveButton(getString(R.string.messPos), (click, arg) -> {
                toDoArrayList.remove(index);
                removeFromDB(index+1);
                listAdapter.notifyDataSetChanged();
                });
                alertDialogBuilder.setNegativeButton(getString(R.string.messNeg), (click, arg) -> {

                });
                alertDialogBuilder.create().show();

                return false;
            }
        });
    }
    public void addItemToList(View view){
        EditText et1 = (EditText)findViewById(R.id.et1);
        Switch sw1 = (Switch) findViewById(R.id.sw1);
        ToDo newToDo = new ToDo(et1.getText().toString(),sw1.isChecked());
        addToDB(newToDo);
        listAdapter.add(newToDo);
        listAdapter.notifyDataSetChanged();
        et1.setText("");


    }

    public void loadFromDB(){
        //connect
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();
        //gather columns
        String[] columns = {Dbopen.col_id, Dbopen.col_action, Dbopen.col_urgent};
        //query results
        Cursor results = db.query(Dbopen.tableName, columns, null, null, null, null, null);
        //column indices
        int col1Index = results.getColumnIndex(opener.col_id);
        int col2Index = results.getColumnIndex(opener.col_action);
        int col3Index = results.getColumnIndex(opener.col_urgent);

        while(results.moveToNext()){
            String action = results.getString(col2Index);
            String urgent = results.getString(col3Index);
            long id = results.getLong(col1Index);
            if (urgent.equals("false")){
                listAdapter.add(new ToDo(action, false));

            } else {
                listAdapter.add(new ToDo(action, true));

            }
            listAdapter.notifyDataSetChanged();
        }
        printCursor(results);
    }
    public void addToDB(ToDo newToDo){
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();
        Switch sw1 = (Switch) findViewById(R.id.sw1);
        //gather columns
        String[] columns = {Dbopen.col_id, Dbopen.col_action, Dbopen.col_urgent};
        //query results
        Cursor results = db.query(Dbopen.tableName, columns, null, null, null, null, null);
        ContentValues cv = new ContentValues();
        cv.put(Dbopen.col_action, newToDo.action);
        if(newToDo.urgent.equals("false")){
            cv.put(Dbopen.col_urgent, "false");
        }   else {
            cv.put(Dbopen.col_urgent, "true");
        }
        listAdapter.notifyDataSetChanged();
        long newId = db.insert(Dbopen.tableName, null, cv);

    }

    public void removeFromDB(int i){
        //connect
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();

        Log.i("is this working?", "It seems to be getting called");
        db.delete(Dbopen.tableName, Dbopen.col_id + "= ?", new String[] {Integer.toString(i)});

    }
    public void printCursor(Cursor results){
        Dbopen opener = new Dbopen(this);
        SQLiteDatabase db;
        db = opener.getWritableDatabase();
        Switch sw1 = (Switch) findViewById(R.id.sw1);
        //gather columns
        String[] columns = {Dbopen.col_id, Dbopen.col_action, Dbopen.col_urgent};
        //query results

        int col1Index = results.getColumnIndex(opener.col_id);
        int col2Index = results.getColumnIndex(opener.col_action);
        int col3Index = results.getColumnIndex(opener.col_urgent);


            int count = results.getCount();
            int version = db.getVersion();
            int numCols = columns.length;

        Log.i("Version ", Integer.toString(version) );
        Log.i("Number of columns ", Integer.toString(numCols) );
        for (int i = 0; i< columns.length; i++){
            Log.i("Column " + i, columns[i]);
        }
        Log.i("Number of results in cursor ", Integer.toString(count));
        /*while(results.moveToNext()) {
            String action = results.getString(col2Index);
            String urgent = results.getString(col3Index);
            long id = results.getLong(col1Index);
            Log.i("Result " ,Long.toString(id) + " " + action + " "+ urgent);
        } */
        results.moveToFirst();
        while(!results.isAfterLast()){
            String action = results.getString(col2Index);
            String urgent = results.getString(col3Index);
            long id = results.getLong(col1Index);
            Log.i("Result " ,Long.toString(id) + " " + action + " "+ urgent);
            results.moveToNext();
        }
    }

}
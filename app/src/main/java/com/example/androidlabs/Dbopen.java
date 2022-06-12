package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;

public class Dbopen extends SQLiteOpenHelper {
    protected final static String database = "Lab 5 Database";
    protected final static int versionNumber = 1;
    public final static String tableName = "To_Do";
    public final static String col_action = "To_Do";
    public final static String col_urgent = "false";
    public final static String col_id = "_id";
    public Dbopen(Context c){
        super (c, database, null, versionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + col_action + " text," + col_urgent + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db){

    }
}

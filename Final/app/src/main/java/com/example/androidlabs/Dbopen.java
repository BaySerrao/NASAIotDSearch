package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Dbopen extends SQLiteOpenHelper {
    protected final static String database = "Favorites_List";
    protected final static int versionNumber = 1;
    public final static String tableName = "Favorites";
    public final static String col_url = "Url";
    public final static String col_hdurl = "Hdurl";
    public final static String col_date = "Date";
    public final static String col_id = "_id";
    public Dbopen(Context c){
        super (c, database, null, versionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_url + " text," + col_hdurl + " text," + col_date +" text);");

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
    public void delete(int i){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Favorites", col_id + "=?", new String[]{Integer.toString(i)});
    }
    public void addtoDB(String a, String b, String c){
        //a is url, b is hdurl, and c is date
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_url, a);
        values.put(col_hdurl, b);
        values.put(col_date, c);
        db.insert(tableName, null, values);
        db.close();
    }
    public ArrayList<Favorite> getFavorites(){
        ArrayList<Favorite> favoriteList = new ArrayList<Favorite>();
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                Favorite favorite = new Favorite();
                favorite.setId(Integer.parseInt(cursor.getString(0)));
                favorite.seturl(cursor.getString(1));
                favorite.sethdurl(cursor.getString(2));
                favorite.setDate(cursor.getString(3));
                favoriteList.add(favorite);
            }while (cursor.moveToNext());
        }
        return favoriteList;
    }

    public void deleteFavorite(Favorite favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, col_id + " =?", new String[]{String.valueOf(favorite.getId())});
        db.close();
    }
    public int getFavoritesCount(){
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

}

package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Favorite>{

    private Context context;
    private ArrayList<Favorite> favoriteArrayList;
    private int layout;
    private Favorite favorite;
    public ListAdapter(Context context, int layout, ArrayList<Favorite> favoriteArrayList) {
        super(context, 0, favoriteArrayList);
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
        this.layout = layout;
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public Favorite getItem(int position){
        return favoriteArrayList.get(position);
    }
    @Override
    public int getCount(){
        return favoriteArrayList.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data from position

        //
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_item, parent, false);
        }
        Favorite favorite = getItem(position);
        TextView tv1 = (TextView)convertView.findViewById(R.id.tv1);

        tv1.setText(favorite.date.toString());
        return convertView;
    }
}
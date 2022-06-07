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

public class ListAdapter extends ArrayAdapter<ToDo>{

    private Context context;
    private ArrayList<ToDo> toDoArrayList;
    private int layout;
    private ToDo toDo;
    public ListAdapter(Context context, int layout, ArrayList<ToDo> toDoArrayList) {
        super(context, 0, toDoArrayList);
        this.context = context;
        this.toDoArrayList = toDoArrayList;
        this.layout = layout;
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public ToDo getItem(int position){
        return toDoArrayList.get(position);
    }
    @Override
    public int getCount(){
        return toDoArrayList.size();
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
        ToDo toDo = getItem(position);
        TextView tv1 = (TextView)convertView.findViewById(R.id.tv1);
        tv1.setText(ToDo.getAction());
        if (toDo.getUrgent()==true){
            convertView.setBackgroundColor(Color.RED);
            tv1.setTextColor(Color.WHITE);
        }


        return convertView;
    }
}
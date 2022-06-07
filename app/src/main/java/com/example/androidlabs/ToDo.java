package com.example.androidlabs;

import android.text.Editable;

import java.util.ArrayList;

public class ToDo {
    public String action;
    public  Boolean urgent;
    ArrayList<ToDo> toDoArrayList;
    public ToDo(String action, Boolean urgent)
    {
        this.action = action;
        this.urgent = urgent;
    }

}

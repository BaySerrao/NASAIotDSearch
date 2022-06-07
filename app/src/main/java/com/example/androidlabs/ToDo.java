package com.example.androidlabs;

import android.text.Editable;

import java.util.ArrayList;

public class ToDo {
    public static Editable action;
    public static Boolean urgent;
    ArrayList<ToDo> toDoArrayList;
    public ToDo(Editable action, Boolean urgent)
    {
        this.action = action;
        this.urgent = urgent;
    }
    public static Editable getAction(){
        return action;
    }
    public static boolean getUrgent(){
        return urgent;
    }
}

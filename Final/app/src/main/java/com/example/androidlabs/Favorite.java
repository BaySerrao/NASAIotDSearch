package com.example.androidlabs;

public class Favorite {
    int id;
    //this class handles creating objects to be listed in the Favorites listview.
    String url, hdurl, date;
    public Favorite(){};
    public Favorite(int id, String url, String hdurl, String date){
        this.id=id;
        this.url=url;
        this.hdurl=hdurl;
        this.date=date;
    }
    public Favorite(String url, String hdurl, String date){
        this.url=url;
        this.hdurl=hdurl;
        this.date=date;
    }
    public int getId(){
        return id;
    }
    public String geturl(){
        return url;
    }
    public String getHdurl(){
        return hdurl;
    }
    public String getDate(){
        return date;
    }
    public void seturl(String url){
        this.url = url;
    }
    public void sethdurl(String hdurl ){
        this.hdurl = hdurl ;
    }
    public void setDate(String date ){
        this.date =date ;
    }
    public void setId(int id){
        this.id=id;
    }


}

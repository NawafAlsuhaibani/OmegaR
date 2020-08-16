package com.example.omegar.NonActivityClasses;

import android.view.View;

public class Ed_card {
    //Attributes
    String title;
    String Abstract;
    String url;


    //Constructor method
    public Ed_card(String title, String Abstract, String url){
        setTitle(title);
        setAbstract(Abstract);
        setUrl(url);
    }

    public String getAbstract() {
        return Abstract;
    }
    public String getTitle(){
        return title;
    }
    public String getUrl(){return url;}

    public void setUrl(String s){this.url = s;}
    public void setAbstract(String s){
        this.Abstract = s;
    }
    public void setTitle(String s){
        this.title = s;
    }
}

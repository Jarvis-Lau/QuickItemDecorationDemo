package com.jarvislau.quickitemdecorationdemo;

/**
 * Created by JarvisLau on 2018/7/9.
 * Description :
 */

public class Entity {

    private String text;
    private String date;

    public Entity(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
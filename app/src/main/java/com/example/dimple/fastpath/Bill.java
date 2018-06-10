package com.example.dimple.fastpath;

import java.util.ArrayList;
import java.util.Date;

public class Bill {
    private String listName;
    private ArrayList<String> listItems;
    private String date;
    private String userID;

    public Bill(){

    }

    public Bill(String listName, String date){
        this.listName = listName;
        this.date = date;
    }

    public void setListItems(ArrayList<String> listItems) {
        this.listItems = listItems;
    }

    public String getDate() {
        return date;
    }

    public String getListName() {
        return listName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<String> getListItems() {
        return listItems;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}

package com.example.kwinam.isafeyou.isafeyou.Item;

/**
 * Created by Taewoong on 2017-08-01.
 */

public class Item {
    String name;
    String phonenumber;
    String message;

    public Item(String name, String phonenumber, String message){
        this.name = name;
        this.phonenumber = phonenumber;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

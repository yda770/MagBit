package com.example.yehuda_da.magbit.models;

import android.media.Image;

import java.util.List;

public class Synagogue {
    private int Id;
    private String Name;
    private String Image;
    private String Address;
    private List<Prayer> Prayers;

    public int getId() {
        return Id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImeg() {
        return Image;
    }

    public void setImeg(String imeg) {
        Image = imeg;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<Prayer> getPrayers() {
        return Prayers;
    }

    public void setPrayers(List<Prayer> prayers) {
        Prayers = prayers;
    }

    public Synagogue(String name, Image Image, String address) {
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Id = String key = database.getReference("quiz").push().getKey();;
        Name = name;
        Image = Image;
        Address = address;
    }
}

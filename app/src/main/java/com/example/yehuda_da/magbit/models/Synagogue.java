package com.example.yehuda_da.magbit.models;

import android.media.Image;

import java.util.List;

public class Synagogue {
    private int Id;
    private String Name;
    private Image Imeg;
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

    public Image getImeg() {
        return Imeg;
    }

    public void setImeg(Image imeg) {
        Imeg = imeg;
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

    public Synagogue(String name, Image imeg, String address) {
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Id = String key = database.getReference("quiz").push().getKey();;
        Name = name;
        Imeg = imeg;
        Address = address;
    }
}

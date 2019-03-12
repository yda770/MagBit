package com.example.yehuda_da.magbit.models;

import android.media.Image;

import java.util.List;

public class Prayer {

   private int Id;
   private String Name;
   private String FatherName;
   private String Shevet;
   private Image Image;
   private String Phone;
   private String Email;
   private boolean IsActive;
   private List<Charity> Charities;

    public int getId() {
        return Id;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getShevet() {
        return Shevet;
    }

    public void setShevet(String shevet) {
        Shevet = shevet;
    }

    public android.media.Image getImage() {
        return Image;
    }

    public void setImage(android.media.Image image) {
        Image = image;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public List<Charity> getCharities() {
        return Charities;
    }

    public void setCharities(List<Charity> charities) {
        Charities = charities;
    }

    public Prayer(String name, String fatherName, String shevet, android.media.Image image, String phone, String email ) {

        Name = name;
        FatherName = fatherName;
        Shevet = shevet;
        Image = image;
        Phone = phone;
        Email = email;
        IsActive = true;

    }
}

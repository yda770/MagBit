package com.example.yehuda_da.magbit.models;

import java.util.Date;

public class Charity {

    private int Id;
    private Date Shabat;
    private int Amount;
    private String Notes;
    private boolean IsPaid;

    public int getId() {
        return Id;
    }


    public Date getShabat() {
        return Shabat;
    }

    public void setShabat(Date shabat) {
        Shabat = shabat;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public boolean isPaid() {
        return IsPaid;
    }

    public void setPaid(boolean paid) {
        IsPaid = paid;
    }

    public Charity(Date shabat, int amount, String notes, boolean isPaid) {
       // Id = id;
        Shabat = shabat;
        Amount = amount;
        Notes = notes;
        IsPaid = isPaid;
    }
}

package com.example.yehuda_da.magbit.models;

import com.example.yehuda_da.magbit.Main2Activity;
import com.google.firebase.auth.FirebaseAuth;

public class magbit_charity {
    private Charity charity;
    private Magbit magbit;
    private boolean is_magbit;
    private boolean is_charity;

    public magbit_charity(Charity charity, Magbit magbit, boolean is_magbit, boolean is_charity) {
        this.charity = charity;
        this.magbit = magbit;
        this.is_magbit = is_magbit;
        this.is_charity = is_charity;
    }

    public Charity getCharity() {
        return charity;
    }

    public Magbit getMagbit() {
        return magbit;
    }

    public boolean isIs_magbit() {
        return is_magbit;
    }

    public boolean isIs_charity() {
        return is_charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public void setMagbit(Magbit magbit) {
        this.magbit = magbit;
    }

    public void setIs_magbit(boolean is_magbit) {
        this.is_magbit = is_magbit;
    }

    public void setIs_charity(boolean is_charity) {
        this.is_charity = is_charity;
    }

}

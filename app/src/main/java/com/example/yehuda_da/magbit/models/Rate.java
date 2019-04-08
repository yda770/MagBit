package com.example.yehuda_da.magbit.models;

import com.example.yehuda_da.magbit.R;

public class Rate
{
    private String UserId;
    private float Rate;

    public Rate() {
    }

    public Rate(String userId, float rate)
    {
        UserId = userId;
        Rate = rate;
    }

    public String getUserId() {
        return UserId;
    }

    public float getRate() {
        return Rate;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setRate(float  rate) {
        Rate = rate;
    }
}

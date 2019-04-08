package com.example.yehuda_da.magbit.models;

import android.widget.MultiAutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class Charity {

    private String Id;
    private String UserId;
    private int Amount;
    private String ToHonor;
    private String UserName;
    private String UserImageUrl;
    private List<String> UsersLiked;

    public Charity() {
    }

    public Charity(int amount, String toHonor) {
        Amount = amount;
        ToHonor = toHonor;
    }


    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setToHonor(String toHonor) {
        ToHonor = toHonor;
    }

    public void setUsersLiked(List<String> usersLiked) {
        UsersLiked = usersLiked;
    }

    public String getId() {
        return Id;
    }
    public String getUserName() {
        return UserName;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public String getToHonor() {
        return ToHonor;
    }

    public List<String> getUsersLiked() {
        return UsersLiked;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void create(Magbit magbit) {
        setUserName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        setUserImageUrl(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("magbits").child(magbit.getId()).child("Charities");
        this.setId(myRef.push().getKey());
        myRef.child(this.getId()).setValue(this);
    }
}

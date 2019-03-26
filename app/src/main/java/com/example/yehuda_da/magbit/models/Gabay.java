package com.example.yehuda_da.magbit.models;

public class Gabay {

    private int Id;
    private String UserId;
    private String SynagogeId;
    private boolean IsOwner;

    public Gabay(int id, String userId, String synagogeId, boolean isOwner) {
        Id = id;
        UserId = userId;
        SynagogeId = synagogeId;
        IsOwner = isOwner;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSynagogeId() {
        return SynagogeId;
    }

    public void setSynagogeId(String synagogeId) {
        SynagogeId = synagogeId;
    }

    public boolean isOwner() {
        return IsOwner;
    }

    public void setOwner(boolean owner) {
        IsOwner = owner;
    }
}

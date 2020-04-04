package com.example.finalproject.models;

public class Favourite {
    public int FID;
    public int UserId;
    public int PropertyID;

    public Favourite(int FID, int userId, int propertyID) {
        this.FID = FID;
        UserId = userId;
        PropertyID = propertyID;
    }

    public int getFID() {
        return FID;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(int propertyID) {
        PropertyID = propertyID;
    }
}

package com.example.finalproject.models;

public class Property {
    public int PropertyID;
    public String Title;
    public int Length;
    public int Width;
    public double Longitude;
    public double Latitude;
    public int Price;
    public String Description;

    public Property(int propertyID, String title, int length, int width, double longitude, double latitude, int price, String description) {
        PropertyID = propertyID;
        Title = title;
        Length = length;
        Width = width;
        Longitude = longitude;
        Latitude = latitude;
        Price = price;
        Description = description;
    }

    public int getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(int propertyID) {
        PropertyID = propertyID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}



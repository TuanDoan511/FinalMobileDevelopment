package com.example.finalproject.models;

public class Image {
    public int ImageId;
    public int PropertyID;
    public String URL;

    public Image(int imageId, int propertyID, String URL) {
        ImageId = imageId;
        PropertyID = propertyID;
        this.URL = URL;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public int getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(int propertyID) {
        PropertyID = propertyID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

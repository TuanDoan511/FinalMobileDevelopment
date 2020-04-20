package com.example.finalproject.models;


import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    public String userUid;
    public String email;
    public String fullName;
    public String phone;
    public String avata;
    public String gender;
    public ArrayList<String> liked_data;
    public ArrayList<String> posts;

    public User() {

    }

    public User(String userUid, String email, String fullName, String phone, String gender, String avata) {
        this.userUid = userUid;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.liked_data = new ArrayList<String>();
        this.posts = new ArrayList<String>();
        this.gender = gender;
        this.avata = avata;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ward implements Serializable {
    public String id;
    public String name;


    public Ward (){

    }

    public Ward(String name, String id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String toString() {
        return this.id + "." + name ;
    }
}

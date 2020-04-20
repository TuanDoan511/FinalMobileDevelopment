package com.example.finalproject.models;

import androidx.annotation.NonNull;

public class districts {
    public String name;
    public String id;


    public districts (){

    }

    public districts(String name, String id) {
        this.name = name;
        this.id = id;
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



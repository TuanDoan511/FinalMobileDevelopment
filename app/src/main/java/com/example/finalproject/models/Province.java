package com.example.finalproject.models;


import androidx.annotation.NonNull;

import com.google.firebase.database.ServerValue;
import com.google.firebase.database.collection.ArraySortedMap;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Province implements Serializable {
    public String id;
    public String name;




    public Province() {

    }

    public Province(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String toString() {
        return this.id + "." + name ;
    }
}

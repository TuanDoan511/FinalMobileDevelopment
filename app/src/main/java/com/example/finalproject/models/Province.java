package com.example.finalproject.models;


import androidx.annotation.NonNull;

import com.google.firebase.database.ServerValue;
import com.google.firebase.database.collection.ArraySortedMap;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Province {
    public String code;
    public String id;
    public String name;
    public Object districts;




    public Province() {

    }

    public Province(String code, String id, String name, Object districts) {
        this.code = code;
        this.id = id;
        this.name = name;
        this.districts = districts;
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

    public Object getDistricts() {
        return districts;
    }

    public void setDistricts(Object districts) {
        this.districts = districts;
    }

    @NonNull
    public String toString() {
        return this.id + "." + name ;
    }
}

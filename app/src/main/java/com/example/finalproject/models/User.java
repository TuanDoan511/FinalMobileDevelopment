package com.example.finalproject.models;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class User {
    public static DatabaseReference user_root;

    public String userUid;
    public String email;
    public String fullName;
    public String phone;

    public User() {

    }

    public User(String userUid, String email, String fullName, String phone) {
        this.userUid = userUid;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
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

    public static void getFromDB(final AsyncFirebaseHandler dataHandler) {
        dataHandler.onStart();
        user_root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataHandler.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dataHandler.onFailed(databaseError);
            }
        });
    }

    public static void createUser(final User user, final String userUid) {
        user_root.child(userUid).setValue(user);
    }
}


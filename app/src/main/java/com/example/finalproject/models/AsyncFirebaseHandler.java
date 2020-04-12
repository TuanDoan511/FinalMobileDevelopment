package com.example.finalproject.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface AsyncFirebaseHandler {
    public void onStart();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);
}

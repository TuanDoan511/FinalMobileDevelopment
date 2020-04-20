package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;


import com.example.finalproject.models.districts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;



public class QuanHuyen_Activity extends AppCompatActivity {
    ArrayList<String> myArrayList = new ArrayList<>();
    ListView myListView;
    DatabaseReference mRef;
    private String quanhuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_huyen_);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(QuanHuyen_Activity.this,android.R.layout.simple_list_item_2,myArrayList);
        myListView = (ListView) findViewById(R.id.listitem_quanhuyen);
        myListView.setAdapter(myArrayAdapter);
        mRef = FirebaseDatabase.getInstance().getReference().child("Provinces").child("districts");
        quanhuyen = getIntent().getExtras().get("quan_huyen").toString();
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(districts.class).toString();
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Toast.makeText(QuanHuyen_Activity.this, quanhuyen, Toast.LENGTH_SHORT).show();

    }


}

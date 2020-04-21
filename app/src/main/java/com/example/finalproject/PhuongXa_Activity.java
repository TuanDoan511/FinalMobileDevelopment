package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.models.Province;
import com.example.finalproject.models.Ward;
import com.example.finalproject.models.districts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhuongXa_Activity extends AppCompatActivity {

    ArrayList<String> myArrayList = new ArrayList<String>();
    ArrayList<Ward> wardsArrayList = new ArrayList<>();
    ListView myListView;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuong_xa_);
        Button all_btn = findViewById(R.id.phuongxa_all);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(PhuongXa_Activity.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView) findViewById(R.id.listitem_phuongxa);
        myListView.setAdapter(myArrayAdapter);

        final Province province = (Province) getIntent().getSerializableExtra("province");
        final districts d = (districts) getIntent().getSerializableExtra("data");
        mRef = FirebaseDatabase.getInstance().getReference().child("Province").child(province.id).child("districts").child(d.id).child("wards");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String name = (String)dataSnapshot1.child("name").getValue();
                    String id = (String)dataSnapshot1.getKey();
                    Ward ward = new Ward(name, id);
                    myArrayList.add(ward.name);
                    wardsArrayList.add(ward);
                }
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                intent.putExtra("ward", wardsArrayList.get(position));
                setResult(1, intent);
                finish();
            }
        });

        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                setResult(2, intent);
                finish();
            }
        });
    }
}

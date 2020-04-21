package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.finalproject.models.Province;
import com.example.finalproject.models.Ward;
import com.example.finalproject.models.districts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



public class QuanHuyen_Activity extends AppCompatActivity {
    ArrayList<String> myArrayList = new ArrayList<String>();
    ArrayList<districts> districtsArrayList = new ArrayList<>();
    ListView myListView;
    DatabaseReference mRef;
    private String quanhuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_huyen_);
        final Button all_btn = findViewById(R.id.quanhuyen_all);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(QuanHuyen_Activity.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView) findViewById(R.id.listitem_quanhuyen);
        myListView.setAdapter(myArrayAdapter);

        final Province province = (Province) getIntent().getSerializableExtra("data");
        districts global_d = (districts) getIntent().getSerializableExtra("district");
        Ward global_ward = (Ward) getIntent().getSerializableExtra("ward");

        mRef = FirebaseDatabase.getInstance().getReference().child("Province").child(province.id).child("districts");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String name = (String)dataSnapshot1.child("name").getValue();
                    String id = (String)dataSnapshot1.getKey();
                    districts d = new districts(name, id);
                    myArrayList.add(d.name);
                    districtsArrayList.add(d);
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

                Intent intent = new Intent(getBaseContext(), PhuongXa_Activity.class);
                intent.putExtra("province", province);
                intent.putExtra("data", districtsArrayList.get(position));
                startActivityForResult(intent, 1);
            }
        });

        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("province", province);
                setResult(3, intent);
                finish();
            }
        });

        if (global_d != null) {
            Intent quanhuyen_intent = new Intent(getBaseContext(), PhuongXa_Activity.class);
            quanhuyen_intent.putExtra("province", province);
            quanhuyen_intent.putExtra("data", global_d);
            quanhuyen_intent.putExtra("ward", global_ward);
            startActivityForResult(quanhuyen_intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1){
                Province province = (Province) data.getSerializableExtra("province");
                districts d = (districts) data.getSerializableExtra("district");
                Ward ward = (Ward) data.getSerializableExtra("ward");

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                intent.putExtra("ward", ward);
                setResult(1, intent);
                finish();
            }
            else if (resultCode == 2) {
                Province province = (Province) data.getSerializableExtra("province");
                districts d = (districts) data.getSerializableExtra("district");

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                setResult(2, intent);
                finish();
            }
        }
    }
}

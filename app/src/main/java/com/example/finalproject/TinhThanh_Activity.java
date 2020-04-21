package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.finalproject.models.Province;
import com.example.finalproject.models.Ward;
import com.example.finalproject.models.districts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.ArraySortedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TinhThanh_Activity extends AppCompatActivity {
    ArrayList<String> myArrayList = new ArrayList<String>();
    ArrayList<Province> provinceArrayList = new ArrayList<Province>();
    ListView myListView;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_thanh_);
        Button all_btn = findViewById(R.id.tinhthanh_all);

        Intent data = getIntent();
        Province global_province = (Province) data.getSerializableExtra("province");
        districts global_d = (districts) data.getSerializableExtra("district");
        Ward global_ward = (Ward) data.getSerializableExtra("ward");


        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(TinhThanh_Activity.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView = (ListView) findViewById(R.id.listitem_tinhthanh);
        myListView.setAdapter(myArrayAdapter);

        mRef = FirebaseDatabase.getInstance().getReference().child("Province");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String name = (String)dataSnapshot1.child("name").getValue();
                    String id = (String)dataSnapshot1.getKey();
                    Province province = new Province(id, name);
                    myArrayList.add(name);
                    provinceArrayList.add(province);
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
                //String quanhuyen = myArrayList.get(position).

                Intent quanhuyen_intent = new Intent(getBaseContext(), QuanHuyen_Activity.class);
                quanhuyen_intent.putExtra("data", provinceArrayList.get(position));
                startActivityForResult(quanhuyen_intent, 1);
            }
        });

        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(4, intent);
                finish();
            }
        });

        if (global_province != null) {
            Intent quanhuyen_intent = new Intent(this, QuanHuyen_Activity.class);
            quanhuyen_intent.putExtra("data", global_province);
            quanhuyen_intent.putExtra("district", global_d);
            quanhuyen_intent.putExtra("ward", global_ward);
            startActivityForResult(quanhuyen_intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                Province province = (Province) data.getSerializableExtra("province");
                districts d = (districts) data.getSerializableExtra("district");
                Ward ward = (Ward) data.getSerializableExtra("ward");

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                intent.putExtra("ward", ward);
                setResult(1, intent);
                finish();
            } else if (resultCode == 2) {
                Province province = (Province) data.getSerializableExtra("province");
                districts d = (districts) data.getSerializableExtra("district");

                Intent intent = new Intent();
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                setResult(2, intent);
                finish();
            } else if (resultCode == 3) {
                Province province = (Province) data.getSerializableExtra("province");

                Intent intent = new Intent();
                intent.putExtra("province", province);
                setResult(3, intent);
                finish();
            }
        }
    }
}

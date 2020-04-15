package com.example.finalproject;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;

import java.util.ArrayList;

public class SellerDetail extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<UpLoad> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        data = new ArrayList<UpLoad>();
        RecyclerView recyclerView = findViewById(R.id.user_selling);
        data = new ArrayList<>();
        adapter = new MyAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}

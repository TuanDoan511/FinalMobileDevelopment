package com.example.finalproject;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class SellerDetail extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<ThongTin> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        data = new ArrayList<ThongTin>();
        RecyclerView recyclerView = findViewById(R.id.user_selling);
        genMockData();
        adapter = new MyAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void genMockData() {
        data = new ArrayList<>();
        data.add(new ThongTin("Bán đất nền",300000, Calendar.getInstance().getTime(),"Quận 3"));
        data.add(new ThongTin("Bán nhà",16548945,Calendar.getInstance().getTime(),"Quận 4"));
        data.add(new ThongTin("Bán nhà chung cư quận 3",45200000,Calendar.getInstance().getTime(),"Quận 10"));
    }
}

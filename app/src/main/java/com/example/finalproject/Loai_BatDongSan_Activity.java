package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Loai_BatDongSan_Activity extends AppCompatActivity {
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<Boolean>status;
    Button btnApDung;
    ArrayList<String> dataMock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai__bat_dong_san_);
        listView = findViewById(R.id.listview);
        btnApDung = findViewById(R.id.btnApDung);
        genMockData();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,android.R.id.text1,data){

            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                CheckedTextView itemView = (CheckedTextView)super.getView(position, convertView, parent);
                itemView.setChecked(status.get(position));
                return itemView;
            }

        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status.set(position,!status.get(position));
                adapter.notifyDataSetChanged();
            }
        });
    btnApDung.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataMock = new ArrayList<>();
            Intent intent =new Intent();
            for (int i = 0;i<6;i++){
                if(status.get(i)==true){
                    dataMock.add(data.get(i));
                }
            }
            intent.putExtra("LoaiBDS",dataMock);
            setResult(RESULT_OK,intent);
            finish();
        }
    });


    }

    private void genMockData() {
        data = new ArrayList<>(Arrays.asList("Tất cả","Căn hộ/Chung cư","Nhà ở","Đất","Văn phòng, mặt bằng kinh doanh"));
        status = new ArrayList<>();
        for(int i=0;i<6;i++){
            status.add(false);
        }
    }

}


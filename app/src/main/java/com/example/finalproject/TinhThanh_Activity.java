package com.example.finalproject;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class TinhThanh_Activity extends AppCompatActivity {
    ListView listViewTinhThanh;
    ArrayList<String> data_Tinh;
    ArrayAdapter<String> adapter;
    String QuanHuyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_thanh_);
        listViewTinhThanh = findViewById(R.id.listviewTinhThanh);
        genMockData();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,data_Tinh);
        listViewTinhThanh.setAdapter(adapter);
        listViewTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Chuyển data Tỉnh thành về main
                Intent intentTinh = new Intent();
                intentTinh.putExtra("TinhThanh",data_Tinh.get(position));
                setResult(RESULT_OK,intentTinh);
                if(data_Tinh.get(position)=="Tất cả"){
                    finish();
                }
                //chuyển data Huyện về Main
                intentTinh.putExtra("QuanHuyen",QuanHuyen);
                //chuyển activity sang Quan Huyen
                Intent intentHuyen = new Intent(TinhThanh_Activity.this, QuanHuyen_Activity.class);
                startActivityForResult(intentHuyen,121);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==121 && resultCode ==RESULT_OK && data !=null)
        {
          QuanHuyen = data.getStringExtra("QuanHuyen");
          Log.d("QuanHuyen",QuanHuyen);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void genMockData() {
        data_Tinh = new ArrayList<>(Arrays.asList("Tất cả","Tp.HCM","Tiền Giang","Cà Mau","Bình Thuận"));
    }
}

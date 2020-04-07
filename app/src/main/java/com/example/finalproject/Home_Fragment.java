package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Home_Fragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<ThongTin> data;
    Button btnKhoangGia,btnDienTich,btnLoaiBatDongSan,btnDiaDiem,btnsearch;
    int MinKhoangGia;
    int MaxKhoangGia;
    int DienTichMin,DienTichMax;
    ArrayList<String> dataLoaiBDS;
    String TinhThanh,QuanHuyen,PhuongXa;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_fragment,container,false);
        data = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        genMockData();
        adapter = new MyAdapter(getActivity().getApplicationContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        btnKhoangGia = rootView.findViewById(R.id.btnKhoangGia);
        btnDienTich = rootView.findViewById(R.id.btnDienTichTong);
        btnLoaiBatDongSan = rootView.findViewById(R.id.btnLoaiBDS);
        btnDiaDiem = rootView.findViewById(R.id.btnDiaDiem);
        btnsearch = rootView.findViewById(R.id.search);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterGia(MinKhoangGia,MaxKhoangGia);
            }
        });
        btnKhoangGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),KhoangGia_Activity.class);
                startActivityForResult(intent,123);

            }
        });
        btnDienTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),DienTich_Activity.class);
                startActivityForResult(intent,1234);

            }
        });
        btnLoaiBatDongSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Loai_BatDongSan_Activity.class);
                startActivityForResult(intent,12345);
            }
        });
        btnDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),TinhThanh_Activity.class);
                startActivityForResult(intent,12);
            }
        });
            return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==123 && resultCode ==RESULT_OK && data !=null)
        {
            MinKhoangGia = data.getIntExtra("giaMin",0);
            MaxKhoangGia = data.getIntExtra("giaMax",300);
            btnKhoangGia.setText("Giá từ: "+MinKhoangGia+"00,000,000 VNĐ");
        }
        if(requestCode==1234 && resultCode ==RESULT_OK && data !=null)
        {
            DienTichMin = data.getIntExtra("dientichMin",0);
            DienTichMax = data.getIntExtra("dientichMax",1000);
            btnDienTich.setText("Diện tích từ : "+DienTichMin+" m2 - "+ DienTichMax +" m2");
        }
        if(requestCode==12345 && resultCode ==RESULT_OK && data !=null){
            dataLoaiBDS = data.getStringArrayListExtra("LoaiBDS");
            btnLoaiBatDongSan.setText(dataLoaiBDS.get(0));
        }
        if(requestCode==12 && resultCode ==RESULT_OK && data !=null){
            TinhThanh = data.getStringExtra("TinhThanh");
            Log.d("TinhThanh",TinhThanh);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void genMockData() {
        data = new ArrayList<>();
        data.add(new ThongTin("Bán đất nền",21000000000L, Calendar.getInstance().getTime(),"Quận 3"));
        data.add(new ThongTin("Bán nhà",16548945,Calendar.getInstance().getTime(),"Quận 4"));
        data.add(new ThongTin("Bán nhà chung cư quận 3",45200000,Calendar.getInstance().getTime(),"Quận 10"));
        data.add(new ThongTin("Bán nhà",16548945,Calendar.getInstance().getTime(),"Quận 4"));
        data.add(new ThongTin("Bán nhà chung cư quận 3",45200000,Calendar.getInstance().getTime(),"Quận 10"));
        data.add(new ThongTin("Bán nhà",16548945,Calendar.getInstance().getTime(),"Quận 4"));
        data.add(new ThongTin("Bán nhà chung cư quận 3",45200000,Calendar.getInstance().getTime(),"Quận 10"));
        data.add(new ThongTin("Bán nhà chung cư quận 3",45200000,Calendar.getInstance().getTime(),"Quận 10"));

    }
    private void filterGia(long minPrice,long maxPrice){
        ArrayList<ThongTin> filterList = new ArrayList<>();
        for (ThongTin item : data){

            long temp = item.getGia()/Long.valueOf(100000000);
            if(temp>= minPrice && temp <=maxPrice){
                filterList.add(item);
            }
        }
        adapter.filterlist(filterList);
    }

}

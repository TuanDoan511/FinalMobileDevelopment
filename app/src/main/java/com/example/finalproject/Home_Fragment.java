package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Home_Fragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<UpLoad> data;
    Button btnKhoangGia,btnDienTich,btnLoaiBatDongSan,btnDiaDiem,btnsearch;
    int MinKhoangGia;
    int MaxKhoangGia;
    int DienTichMin,DienTichMax;
    ArrayList<String> dataLoaiBDS;
    String TinhThanh,QuanHuyen,PhuongXa;
    DatabaseReference mDataBaseRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_fragment,container,false);
        data = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDataBaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   UpLoad upLoad = postSnapshot.getValue(UpLoad.class);
                   data.add(upLoad);
               }
               adapter = new MyAdapter(getActivity(), data);

               recyclerView.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(getActivity().getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
           }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
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
    private void filterGia(long minPrice,long maxPrice){
        ArrayList<UpLoad> filterList = new ArrayList<>();
        for (UpLoad item : data){

            long temp = item.getGiaBan()/Long.valueOf(100000000);
            if(temp>= minPrice && temp <=maxPrice){
                filterList.add(item);
            }
        }
        adapter.filterlist(filterList);
    }

}

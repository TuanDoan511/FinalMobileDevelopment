package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.Province;
import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.example.finalproject.models.Ward;
import com.example.finalproject.models.districts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


import static android.app.Activity.RESULT_OK;

public class Home_Fragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<UpLoad> data;
    Button btnKhoangGia,btnDienTich,btnLoaiBatDongSan,btnDiaDiem,btnsearch;
    int MinKhoangGia=0;
    int MaxKhoangGia=300;
    int DienTichMin=0,DienTichMax=1000;
    ArrayList<String> dataLoaiBDS, dataLoaiBDSFromClick;
    Province province;
    districts d;
    Ward ward;

    DatabaseReference mDataBaseRef;
    boolean tempValue=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_fragment,container,false);
        data = new ArrayList<>();
        dataLoaiBDS = new ArrayList<>();
        dataLoaiBDSFromClick=new ArrayList<>();
        dataLoaiBDS.add("Tất cả");
        recyclerView = rootView.findViewById(R.id.recyclerView);
        final ProgressBar progressBar = rootView.findViewById(R.id.recyclerView_progress);
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    UpLoad upLoad = postSnapshot.getValue(UpLoad.class);
                    data.add(upLoad);
                }
                adapter = new MyAdapter(getActivity(),data);

                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity().getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
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
                    if(dataLoaiBDSFromClick.isEmpty()){
                        filterGia(MinKhoangGia,MaxKhoangGia,DienTichMin,DienTichMax,dataLoaiBDS);
                    }
                    else{

                        filterGia(MinKhoangGia,MaxKhoangGia,DienTichMin,DienTichMax,dataLoaiBDSFromClick);
                    }
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
                final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                },1500);
                Intent intent = new Intent(getActivity().getApplicationContext(),TinhThanh_Activity.class);
                intent.putExtra("province", province);
                intent.putExtra("district", d);
                intent.putExtra("ward", ward);
                startActivityForResult(intent,12);
            }
        });
            return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==123 && resultCode ==RESULT_OK && data !=null)
        {
            String pattern = "##,###,###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            MinKhoangGia = data.getIntExtra("giaMin",0);
            MaxKhoangGia = data.getIntExtra("giaMax",20000000);
            if (MinKhoangGia != 0 || MaxKhoangGia != 20000000)
            {
                String str_min = "";
                String str_max = "";
                if (MinKhoangGia == 0){
                    str_min = MinKhoangGia + " VNĐ đến ";
                }
                else {
                    str_min = decimalFormat.format(MinKhoangGia) + ",000 VNĐ đến ";
                }

                if (MaxKhoangGia == 20000000){
                    str_max = decimalFormat.format(MaxKhoangGia)+",000+ VNĐ";
                }
                else {
                    str_max = decimalFormat.format(MaxKhoangGia)+",000 VNĐ";
                }
                btnKhoangGia.setText("Giá từ: "+ str_min + str_max);
            }
            else {
                btnKhoangGia.setText("chọn khoảng giá");
            }
        }
        if(requestCode==1234 && resultCode ==RESULT_OK && data !=null)
        {
            DienTichMin = data.getIntExtra("dientichMin",0);
            DienTichMax = data.getIntExtra("dientichMax",1000);

            if (DienTichMin != 0 || DienTichMax != 1000)
            {
                String str_min = DienTichMin + "  đến ";;
                String str_max = "";
                if (DienTichMin == 0){
                    str_min = DienTichMin + " VNĐ đến ";
                }
                else {
                    str_min = DienTichMin + ",000 VNĐ đến ";
                }

                if (MaxKhoangGia == 20000000){
                    str_max = ",000+ VNĐ";
                }
                else {
                    str_max = ",000 VNĐ";
                }
                btnKhoangGia.setText("Giá từ: "+ str_min + str_max);
            }
            else {
                btnKhoangGia.setText("chọn khoảng giá");
            }
            btnDienTich.setText("Diện tích từ : "+DienTichMin+" m2 - "+ DienTichMax +" m2");
        }
        if(requestCode==12345 && resultCode ==RESULT_OK && data !=null){
            dataLoaiBDSFromClick = data.getStringArrayListExtra("LoaiBDS");
            btnLoaiBatDongSan.setText(dataLoaiBDSFromClick.get(0));
        }
        if(requestCode==12) {
            if (resultCode == 1) {
                province = (Province) data.getSerializableExtra("province");
                d = (districts) data.getSerializableExtra("district");
                ward = (Ward) data.getSerializableExtra("ward");

                btnDiaDiem.setText(province.name + ", " + d.name + ", " + ward.name);
            } else if (resultCode == 2) {
                province = (Province) data.getSerializableExtra("province");
                d = (districts) data.getSerializableExtra("district");

                btnDiaDiem.setText(province.name + ", " + d.name);
            } else if (resultCode == 3) {
                province = (Province) data.getSerializableExtra("province");
                btnDiaDiem.setText(province.name);

            }
            else if (resultCode == 4) {
                btnDiaDiem.setText("Toàn quốc");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void filterGia(long minPrice,long maxPrice,int minDienTich,int maxDienTich,ArrayList<String>arrLoaiBDS){
        ArrayList<UpLoad> filterList = new ArrayList<>();

        for (UpLoad item : data){
            long dienTich = item.getDienTich();
            String loaiBDS = item.getLoaiBDS();
            for (String singleValue : arrLoaiBDS){
                if(singleValue.equals(loaiBDS) || dataLoaiBDSFromClick.isEmpty() || arrLoaiBDS.get(0).equals("Tất cả") ) {
                    tempValue = true;
                    break;
                }
            }
            long temp = item.getGiaBan()/Long.valueOf(100000000);
            if(maxPrice==300 && maxDienTich==1000){
                if(temp>= minPrice  && dienTich>=minDienTich && tempValue ){
                    filterList.add(item);
                }
            }
             else if(maxDienTich==1000){
                if(temp>= minPrice  && dienTich>=minDienTich && dienTich<=maxDienTich && tempValue ){
                    filterList.add(item);
                }
            }
            else if(maxPrice==300){
                if(temp>= minPrice  && dienTich>=minDienTich && dienTich<=maxDienTich && tempValue){
                    filterList.add(item);
                }
            }

            else if(temp>= minPrice && temp <=maxPrice && dienTich>=minDienTich && dienTich<=maxDienTich && tempValue ){
                filterList.add(item);
            }
            tempValue=false;
        }
        adapter.filterlist(filterList);

    }
}

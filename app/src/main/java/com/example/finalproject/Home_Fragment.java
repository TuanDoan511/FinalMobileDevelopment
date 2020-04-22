package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


import static android.app.Activity.RESULT_OK;

public class Home_Fragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<UpLoad> data;
    Button btnKhoangGia,btnDienTich,btnLoaiBatDongSan,btnDiaDiem,btnsearch;
    Spinner LoaiBatDongSan;
    int MinKhoangGia=0;
    int MaxKhoangGia=20000000;
    int DienTichMin=0,DienTichMax=1000;
    ArrayList<String> dataLoaiBDS, dataLoaiBDSFromClick;
    String typeSelling;
    Province province;
    districts d;
    Ward ward;

    DatabaseReference mDataBaseRef;
    boolean tempValue=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.home_fragment,container,false);
        typeSelling = "Tất cả";
        data = new ArrayList<>();
        dataLoaiBDS = new ArrayList<>();;
        dataLoaiBDS.add("Tất cả");
        dataLoaiBDS.add("Căn hộ/Chung cư");
        dataLoaiBDS.add("Nhà ở");
        dataLoaiBDS.add("Đất");
        dataLoaiBDS.add("Văn phòng, mặt bằng kinh doanh");
        //dataLoaiBDSFromClick=new ArrayList<>();
        //dataLoaiBDS.add("Tất cả");
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
        LoaiBatDongSan = rootView.findViewById(R.id.LoaiBDS);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterGia();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_item, dataLoaiBDS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LoaiBatDongSan.setAdapter(adapter);
        LoaiBatDongSan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelling = dataLoaiBDS.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*btnLoaiBatDongSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Loai_BatDongSan_Activity.class);
                if(dataLoaiBDSFromClick.isEmpty()){
                    intent.putExtra("type", dataLoaiBDS);
                }
                else{

                    intent.putExtra("type", dataLoaiBDSFromClick);
                }
                startActivityForResult(intent,12345);
            }
        });*/
        btnDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        else if(requestCode==1234 && resultCode ==RESULT_OK && data !=null)
        {
            DienTichMin = data.getIntExtra("dientichMin",0);
            DienTichMax = data.getIntExtra("dientichMax",1000);

            if (DienTichMin != 0 || DienTichMax != 1000)
            {
                String str_min = DienTichMin + " m² đến ";;
                String str_max = "";

                if (DienTichMax == 1000){
                    str_max = DienTichMax + "+ m²";
                }
                else {
                    str_max = DienTichMax + " m²";
                }
                btnDienTich.setText("Diện tích từ : " + str_min + str_max);
            }
            else {
                btnDienTich.setText("diện tích tổng");
            }
        }
        /*if(requestCode==12345 && resultCode ==RESULT_OK && data !=null){
            dataLoaiBDSFromClick = data.getStringArrayListExtra("LoaiBDS");
            btnLoaiBatDongSan.setText(dataLoaiBDSFromClick.get(0));
        }*/
        else if(requestCode==12) {
            if (resultCode == 1) {
                province = (Province) data.getSerializableExtra("province");
                d = (districts) data.getSerializableExtra("district");
                ward = (Ward) data.getSerializableExtra("ward");

                btnDiaDiem.setText(province.name + ", " + d.name + ", " + ward.name);
            } else if (resultCode == 2) {
                province = (Province) data.getSerializableExtra("province");
                d = (districts) data.getSerializableExtra("district");
                ward = null;

                btnDiaDiem.setText(province.name + ", " + d.name);
            } else if (resultCode == 3) {
                province = (Province) data.getSerializableExtra("province");
                d = null;
                ward = null;

                btnDiaDiem.setText(province.name);
            }
            else if (resultCode == 4) {
                province = null;
                d = null;
                ward = null;

                btnDiaDiem.setText("Toàn Quốc");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void filterGia(){
        ArrayList<UpLoad> filterList = (ArrayList<UpLoad>) data.clone();

        for (UpLoad item : data){
            if (MinKhoangGia != 0 || MaxKhoangGia != 20000000){
                if (item.getGiaBan()/1000 < MinKhoangGia){
                    filterList.remove(item);
                    continue;
                }
                else if (item.getGiaBan()/1000 > MaxKhoangGia && MaxKhoangGia != 20000000){
                    filterList.remove(item);
                    continue;
                }
            }
            if (DienTichMin != 0 || DienTichMax != 1000){
                if (item.getDienTich() < DienTichMin){
                    filterList.remove(item);
                    continue;
                }
                else if (item.getDienTich() > DienTichMax && DienTichMax != 1000){
                    filterList.remove(item);
                    continue;
                }
            }
            if (typeSelling != "Tất cả"){
                if (!item.getLoaiBDS().equals(typeSelling)){
                    filterList.remove(item);
                    continue;
                }
            }
            String location = (String) btnDiaDiem.getText();
            if (!location.equals("Toàn Quốc")){
                String[] splited = location.split(", ");
                String[] item_location = {item.getProvince().name, item.getDistricts().name, item.getWard().name};
                for (int count = 0; count < splited.length; ++count){
                    if (!item_location[count].equals(splited[count])){
                        filterList.remove(item);
                        break;
                    }
                }
            }
        }
        adapter.filterlist(filterList);

    }
}

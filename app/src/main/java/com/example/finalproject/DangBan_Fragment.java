package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;

public class DangBan_Fragment extends Fragment  {
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    Spinner spinner,spinner2;
    Button btnGia,btnDienTich,btnXacNhan;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dangban,container,false);
        spinner = rootView.findViewById(R.id.spinner);
        genMockData();
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), data.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;

    }
    private void genMockData() {
        data = new ArrayList<>(Arrays.asList("Căn hộ/Chung cư","Nhà ở","Đất","Văn phòng, mặt bằng kinh doanh"));

    }
}

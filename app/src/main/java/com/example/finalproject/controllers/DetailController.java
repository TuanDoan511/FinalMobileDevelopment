package com.example.finalproject.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.finalproject.SellerDetail;

public class DetailController {
    public static void get_detail_seller(Button detail_btn, final Context context) {
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerDetail.class);
                context.startActivity(intent);
            }
        });
    }

    public static void toggle_like(ToggleButton like_btn, final Context context) {
        like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "unckeck", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package com.example.finalproject.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.finalproject.MainActivity;
import com.example.finalproject.SellerDetail;
import com.example.finalproject.models.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailController {
    public static void get_detail_seller(Button detail_btn, final Context context, final User seller) {
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerDetail.class);
                intent.putExtra("seller", seller);
                context.startActivity(intent);
            }
        });
    }

    public static void toggle_like(ToggleButton like_btn, final Context context, final String id_BaiDang) {
        like_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (MainActivity.user.liked_data == null) {
                    MainActivity.user.liked_data = new ArrayList<String>();
                }
                if (isChecked){
                    MainActivity.user.liked_data.add(id_BaiDang);
                }
                else {
                    MainActivity.user.liked_data.remove(id_BaiDang);
                }
                FirebaseDatabase.getInstance().getReference("User").child(MainActivity.user.getUserUid()).child("liked_data").setValue(MainActivity.user.liked_data);
            }
        });
    }
}

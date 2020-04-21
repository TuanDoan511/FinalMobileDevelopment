package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.controllers.DetailController;
import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Detail extends AppCompatActivity {

    TextView title;
    TextView price;
    TextView date;
    TextView location;
    TextView dimension;
    TextView description;
    ToggleButton like_btn;
    ViewPager images;
    TextView type;

    ImageView avata;
    TextView user_name;
    Button detail_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        final LinearLayout main = findViewById(R.id.main_detail);
        final ProgressBar progressBar = findViewById(R.id.detail_progress);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        dimension = findViewById(R.id.dimension);
        description = findViewById(R.id.description);
        like_btn = findViewById(R.id.like);
        type = findViewById(R.id.type);

        images = findViewById(R.id.image);

        avata = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        detail_btn = findViewById(R.id.detail_btn);

        Intent intent = getIntent();
        final UpLoad data = (UpLoad) intent.getSerializableExtra("data");
        ImageAdapter adapterView = new ImageAdapter(this, (ArrayList<String>) data.getmImageUrl());

        images.setAdapter(adapterView);

        final User[] seller_detail = new User[1];
        main.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(data.getId_User_BaiDang());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seller_detail[0] = dataSnapshot.getValue(User.class);
                user_name.setText(seller_detail[0].getFullName());
                Picasso.get().load(seller_detail[0].avata).placeholder(R.mipmap.ic_launcher_round).into(avata);
                main.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                DetailController.get_detail_seller(detail_btn, Detail.this, seller_detail[0]);
                type.setText(data.loaiBDS);
                location.setText("Địa chỉ: " + data.ward.name + ", " + data.districts.name + ", " + data.province.name);
                title.setText(data.getTieuDe());
                NumberFormat formatter = new DecimalFormat("#,###");
                price.setText(formatter.format(data.getGiaBan()) + " VNĐ");
                String pattern = "dd/MM/yyyy hh:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                date.setText(simpleDateFormat.format(data.getDate()));
                dimension.setText(Long.toString(data.getDienTich()) + " m²");
                description.setText(data.getMoTa());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                main.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Detail.this, "Unable to load seller profile", Toast.LENGTH_SHORT).show();
            }
        });

        detail_btn.setEnabled(intent.getBooleanExtra("enable_detail", true));
        like_btn.setVisibility(intent.getIntExtra("enable_liked", View.VISIBLE));

        if (MainActivity.user.liked_data != null){
            if (MainActivity.user.liked_data.contains(data.getId_BaiDang())){
                like_btn.setChecked(true);
            }
        }

        DetailController.get_detail_seller(detail_btn, this, seller_detail[0]);
        DetailController.toggle_like(like_btn, this, data.getId_BaiDang());
    }
}

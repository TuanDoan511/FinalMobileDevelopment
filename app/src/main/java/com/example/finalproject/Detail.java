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

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.finalproject.controllers.DetailController;
import com.example.finalproject.models.UpLoad;
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

        images = findViewById(R.id.image);

        avata = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        detail_btn = findViewById(R.id.detail_btn);

        Intent intent = getIntent();
        final UpLoad data = (UpLoad) intent.getSerializableExtra("data");
        ImageAdapter adapterView = new ImageAdapter(this, (ArrayList<String>) data.getmImageUrl());
        images.setAdapter(adapterView);

        detail_btn.setEnabled(intent.getBooleanExtra("enable_detail", true));

        DetailController.get_detail_seller(detail_btn, this);
        DetailController.toggle_like(like_btn, this);

        title.setText(data.getTieuDe());
        NumberFormat formatter = new DecimalFormat("#,###");
        price.setText(formatter.format(data.getGiaBan()) + " VNĐ");
        String pattern = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        date.setText(simpleDateFormat.format(data.getDate()));
        dimension.setText(Long.toString(data.getDienTich()) + " m²");
        description.setText(data.getMoTa());
    }
}

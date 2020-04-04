package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.controllers.DetailController;

public class Detail extends AppCompatActivity {

    TextView title;
    TextView price;
    TextView date;
    TextView location;
    TextView dimension;
    TextView description;
    ToggleButton like_btn;

    ImageView avata;
    TextView user_name;
    Button detail_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        dimension = findViewById(R.id.dimension);
        description = findViewById(R.id.description);
        like_btn = findViewById(R.id.like);


        avata = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        detail_btn = findViewById(R.id.detail_btn);

        Intent intent = getIntent();

        detail_btn.setEnabled(intent.getBooleanExtra("enable_detail", true));

        DetailController.get_detail_seller(detail_btn, this);
        DetailController.toggle_like(like_btn, this);
    }
}

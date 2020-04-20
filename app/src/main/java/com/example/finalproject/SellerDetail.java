package com.example.finalproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.Image;
import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SellerDetail extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<UpLoad> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        data = new ArrayList<UpLoad>();
        final RecyclerView recyclerView = findViewById(R.id.user_selling);
        data = new ArrayList<>();
        adapter = new MyAdapter(this, data);
        TextView empty = findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ImageView user_avatar = findViewById(R.id.user_image_detail);
        TextView user_name = findViewById(R.id.user_name_detail);
        TextView email = findViewById(R.id.seller_email);
        ImageButton call = findViewById(R.id.make_call);
        ImageButton text_message = findViewById(R.id.send_message);
        TextView gender = findViewById(R.id.gender);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("seller");
        Picasso.get().load(user.avata).placeholder(R.mipmap.ic_launcher_round).into(user_avatar);
        user_name.setText(user.getFullName());
        email.setText(user.getEmail());
        gender.setText(user.gender);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SellerDetail.this);
                alertDialog.setTitle("SERVICE");
                alertDialog.setMessage("Do you want to make a call ?");
                //alertDialog.setIcon(R.drawable.);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + user.getPhone()));
                        if (ActivityCompat.checkSelfPermission(SellerDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        if (user.posts == null) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
            final int[] count = {0};
            for (String post_id : user.posts) {
                databaseReference.child(post_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        count[0] += 1;
                        UpLoad upLoad = dataSnapshot.getValue(UpLoad.class);
                        data.add(upLoad);
                        if (count[0] == user.posts.size()){
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SellerDetail.this, "Some thing went wrong when get data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        text_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SellerDetail.this);
                alertDialog.setTitle("SERVICE");
                alertDialog.setMessage("Do you want to send message ?");
                //alertDialog.setIcon(R.drawable.);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentsms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + user.getPhone()));
                        //intentsms.putExtra("sms_body", "I want to ask some information about your selling");
                        startActivity(intentsms);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }


}

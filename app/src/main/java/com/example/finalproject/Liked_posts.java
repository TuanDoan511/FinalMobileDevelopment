package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Liked_posts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_posts);

        RecyclerView recyclerView = findViewById(R.id.liked_posts);
        final User user = MainActivity.user;
        final ArrayList<UpLoad> data = new ArrayList<>();
        final MyAdapter adapter = new MyAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        final int[] count = {0};
        if (user.liked_data == null){
            user.liked_data = new ArrayList<>();
        }
        for (String liked_post_id : user.liked_data)
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child(liked_post_id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count[0] += 1;
                    data.add(dataSnapshot.getValue(UpLoad.class));
                    if (count[0] == user.liked_data.size())
                    {
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}

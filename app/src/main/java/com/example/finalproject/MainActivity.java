package com.example.finalproject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class    MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private static User user = null;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar progressBar = findViewById(R.id.progressBar2);
        final FrameLayout frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottomnavi);

        progressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
        //get user
        if (token == "" && user == null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                token = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
        }
        if (token != "" && user == null) {
            final DatabaseReference user_from_db = FirebaseDatabase.getInstance().getReference("User").child(token);
            user_from_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home_Fragment();
                            break;
                        case R.id.nav_sell:
                            if (user == null) {
                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivityForResult(intent, 1);
                            } else {
                                selectedFragment = new DangBan_Fragment();
                            }
                            break;
                        case R.id.nav_setting:
                            selectedFragment = new Setting_Fragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                user = (User) data.getSerializableExtra("user");
                bottomNavigationView.setSelectedItemId(R.id.nav_sell);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DangBan_Fragment()).commit();

            } else {
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
            }
        }
    }

    public static User getUser() {
        if (user != null) {
            return user;
        }
        return null;
    }
}

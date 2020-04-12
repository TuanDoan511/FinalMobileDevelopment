package com.example.finalproject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.AsyncFirebaseHandler;
import com.example.finalproject.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class    MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private User user = null;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get user
        if (token == "" && user == null) {
            token = FirebaseAuth.getInstance().getUid();
        }
        if (token != "" && user == null) {
            getUser();
        }
        bottomNavigationView = findViewById(R.id.bottomnavi);
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
                bottomNavigationView.setSelectedItemId(R.id.nav_sell);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DangBan_Fragment()).commit();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
            }
        }
    }

    public void getUser() {
        User.getFromDB(new AsyncFirebaseHandler() {
            @Override
            public void onStart() {
                User.user_root = FirebaseDatabase.getInstance().getReference("User").child(token);
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                user = data.getValue(User.class);
                Log.d("test", user.getUserUid());
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}

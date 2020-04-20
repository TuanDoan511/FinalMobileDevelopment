package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TinDaDang_Activity extends AppCompatActivity implements TinDaDang_Apdater.OnItemClickListener {
    RecyclerView recyclerView;
    TinDaDang_Apdater adapter;
    ArrayList<UpLoad> data;
    DatabaseReference mDataBaseRef;
    FirebaseStorage mStorage;
    ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_da_dang);
        data = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_tindadang);
        User user = MainActivity.user;
        final String id_user = user.getUserUid();
        Log.d("GETUSER", "onCreate: "+id_user);
        mStorage = FirebaseStorage.getInstance();
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        valueEventListener = mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    UpLoad upLoad = postSnapshot.getValue(UpLoad.class);
                    if(id_user.equals(upLoad.getId_User_BaiDang())){
                        Log.d("TAG", "onDataChange: "+upLoad.getTieuDe());
                        data.add(upLoad);
                    }
                }
                adapter = new TinDaDang_Apdater(TinDaDang_Activity.this,data);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(TinDaDang_Activity.this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TinDaDang_Activity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(TinDaDang_Activity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(TinDaDang_Activity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBaseRef.removeEventListener(valueEventListener);
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onModifyClick(int position) {
        final UpLoad selectedItem = data.get(position);
        final String selectedKey = selectedItem.getId_BaiDang();
        Intent intent = new Intent(this,modify_activity.class);
        intent.putExtra("KEY",selectedKey);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        final UpLoad selectedItem = data.get(position);
        final String selectedKey = selectedItem.getId_BaiDang();
        final int size_ImageUri = selectedItem.getmImageUrl().size();

        for (int i=0;i<size_ImageUri;i++){
            StorageReference imagref = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl().get(i));
            final int finalI = i;
            imagref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if(finalI ==size_ImageUri-1){
                        mDataBaseRef.child(selectedKey).removeValue();
                    }
                }
            });

        }
        final LoadingDialog loadingDialog = new LoadingDialog(TinDaDang_Activity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        },1000);
    }
}

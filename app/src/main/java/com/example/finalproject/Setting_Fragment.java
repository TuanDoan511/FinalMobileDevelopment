package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Setting_Fragment extends Fragment {
    LinearLayout not_have_user;
    LinearLayout user_loggedin;
    TextView user_name;
    ImageView user_image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting,container,false);
        LinearLayout main = view.findViewById(R.id.main_setting);
        user_image = view.findViewById(R.id.user_image_setting);
        user_name = view.findViewById(R.id.user_info_setting);
        not_have_user = view.findViewById(R.id.not_have_user);
        user_loggedin = view.findViewById(R.id.user_loggedin);
        Button signout = view.findViewById(R.id.user_signout_setting);
        TextView login = view.findViewById(R.id.login_setting);
        TextView modify_user = view.findViewById(R.id.modify_user_password);
        TextView posts = view.findViewById(R.id.user_posts);
        TextView like = view.findViewById(R.id.user_like);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                MainActivity.user = null;
                user_image.setImageResource(R.drawable.ic_face_black_24dp);
                not_have_user.setVisibility(View.VISIBLE);
                user_loggedin.setVisibility(View.GONE);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login.class);
                startActivityForResult(intent, 1);
            }
        });
        modify_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.user == null){
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivityForResult(intent, 1);
                }
                else{
                    Intent intent = new Intent(getContext(), modifyUserActivity.class);
                    startActivity(intent);
                }
            }
        });
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.user == null){
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivityForResult(intent, 1);
                }else{
                    Intent intent = new Intent(getContext(),TinDaDang_Activity.class);
                    startActivity(intent);
                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.user == null){
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivityForResult(intent, 1);
                }else{
                    Intent intent = new Intent(getContext(), Liked_posts.class);
                    startActivity(intent);
                }
            }
        });


        if (MainActivity.user == null) {
            not_have_user.setVisibility(View.VISIBLE);
            user_loggedin.setVisibility(View.GONE);
        }
        else {
            user_name.setText(MainActivity.user.getFullName());
            Picasso.get().load(MainActivity.user.avata).placeholder(R.mipmap.ic_launcher_round).into(user_image);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                MainActivity.user = (User) data.getSerializableExtra("user");
                not_have_user.setVisibility(View.GONE);
                user_loggedin.setVisibility(View.VISIBLE);
                user_name.setText(MainActivity.user.getFullName());
                Picasso.get().load(MainActivity.user.avata).placeholder(R.mipmap.ic_launcher_round).into(user_image);
            }
        }
    }
}

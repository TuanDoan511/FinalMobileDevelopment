package com.example.finalproject;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.User;

public class Setting_Fragment extends Fragment {
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout main = getActivity().findViewById(R.id.main_setting);
        ImageView user_image = getActivity().findViewById(R.id.user_image_setting);
        LinearLayout not_have_user = getActivity().findViewById(R.id.not_have_user);

        user = MainActivity.getUser();
        Toast.makeText(getActivity(), user.getUserUid(), Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.setting,container,false);
    }
}

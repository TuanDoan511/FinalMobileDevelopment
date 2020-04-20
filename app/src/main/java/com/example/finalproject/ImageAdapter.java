package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<String> imagesUrl;

    ImageAdapter(Context context,  ArrayList<String> imagesUrl) {
        this.mContext = context;
        this.imagesUrl = imagesUrl;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView image = (ImageView)view.findViewById(R.id.image_view);
        Picasso.get().load(imagesUrl.get(position)).placeholder(R.mipmap.ic_launcher).into(image);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}

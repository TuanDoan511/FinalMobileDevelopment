package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context ctx;
    private ImageView ivGallery;
    ArrayList<Uri> mArrayUri;

    public PhotoAdapter(Context mContext, ArrayList<Uri> listData) {
        this.ctx = mContext;
        this.mArrayUri = listData;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View itemView = layoutInflater.inflate(
                R.layout.photo_item,
                parent,
                false
        );
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {


        ivGallery.setImageURI(mArrayUri.get(position));
    }

    @Override
    public int getItemCount() {
        return null==mArrayUri ? 0: mArrayUri.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGallery = itemView.findViewById(R.id.imageView3);
        }
    }
}

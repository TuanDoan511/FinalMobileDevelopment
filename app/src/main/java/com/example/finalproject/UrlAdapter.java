package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.URLViewHolder> {
    private Context context;
    private List<String> arrURL;
    private ImageView imageView;

    public UrlAdapter(Context context, List<String> arrURL) {
        this.context = context;
        this.arrURL = arrURL;
    }

    @NonNull
    @Override
    public URLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.photo_item,
                parent,
                false
        );
        return new URLViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull URLViewHolder holder, int position) {
        Picasso.get()
                .load(arrURL.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return null==arrURL ? 0: arrURL.size();
    }


    public class URLViewHolder extends RecyclerView.ViewHolder {
        public URLViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }
}

package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDe, txtGia, txtTime, txtDiaChi;
        CheckBox checkBox;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            checkBox = itemView.findViewById(R.id.checkBox);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }

    private Context context;
    private ArrayList<UpLoad> data;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.single_row,
                viewGroup,
                false
        );
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {

        viewHolder.txtTieuDe.setText(data.get(position).getTieuDe());
        NumberFormat formatter = new DecimalFormat("#,###");
        long myNumber = data.get(position).getGiaBan();
        String formattedNumber = formatter.format(myNumber);
        viewHolder.txtGia.setText(formattedNumber+ " VNĐ");

        //viewHolder.txtDiaChi.setText(data.get(position).get());
        String pattern = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        viewHolder.txtTime.setText(simpleDateFormat.format(data.get(position).getDate()));
        Picasso.get()
                .load(data.get(position).getmImageUrl().get(0))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail.class);
                if (context.getClass() == SellerDetail.class) {
                    intent.putExtra("enable_detail", false);
                }
                context.startActivity(intent);
            }
        });
    }

    public MyAdapter(Context context, ArrayList<UpLoad> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public void filterlist(ArrayList<UpLoad> filterList) {
        data = filterList;
        notifyDataSetChanged();
    }
}

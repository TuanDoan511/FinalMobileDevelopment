package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDe, txtGia, txtTime, txtDiaChi;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

    }

    private Context context;
    private ArrayList<ThongTin> data;

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
        double myNumber = data.get(position).getGia();
        String formattedNumber = formatter.format(myNumber);
        viewHolder.txtGia.setText(formattedNumber+ " VNĐ");
        viewHolder.txtDiaChi.setText(data.get(position).getDiaChi());
        String pattern = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        viewHolder.txtTime.setText(simpleDateFormat.format(data.get(position).getThoigian()));

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

    public MyAdapter(Context context, ArrayList<ThongTin> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public void filterlist(ArrayList<ThongTin> filterList) {
        data = filterList;
        notifyDataSetChanged();
    }
}

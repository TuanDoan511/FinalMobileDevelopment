package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


class TinDaDang_Apdater extends RecyclerView.Adapter<TinDaDang_Apdater.MyViewHolder> {
    private static OnItemClickListener mListener;
    private Context context;
    private ArrayList<UpLoad> data;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView txtTieuDe, txtGia, txtTime, txtDiaChi;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieuDe_TinDaDang);
            txtGia = itemView.findViewById(R.id.txtGia_TinDaDang);
            txtTime = itemView.findViewById(R.id.txtTime_TinDaDang);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi_TinDaDang);
            imageView = itemView.findViewById(R.id.imageView_TinDaDang);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem modify = menu.add(Menu.NONE,1,1,"Chỉnh sửa");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Xóa");

            modify.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()){
                        case 1:
                            mListener.onModifyClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }


    @NonNull
    @Override
    public TinDaDang_Apdater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.tindadang_singlerow,
                viewGroup,
                false
        );
        return new com.example.finalproject.TinDaDang_Apdater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TinDaDang_Apdater.MyViewHolder viewHolder, int position) {

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

    }

    public TinDaDang_Apdater(Context context, ArrayList<UpLoad> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onModifyClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}


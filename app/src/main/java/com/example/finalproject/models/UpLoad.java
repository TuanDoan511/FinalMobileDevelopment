package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class UpLoad {
    public String tieuDe;
    public long giaBan;
    public long dienTich;
    public String loaiBDS;
    public  String mImageUrl;
    public  String moTa;
    public Date date;

    public UpLoad() {

    }

    public UpLoad(String tieuDe, long giaBan, long dienTich, String loaiBDS, String mImageUrl, String moTa,Date date) {
        this.tieuDe = tieuDe;
        this.giaBan = giaBan;
        this.dienTich = dienTich;
        this.loaiBDS = loaiBDS;
        this.mImageUrl = mImageUrl;
        this.moTa= moTa;
        this.date = date;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public long getDienTich() {
        return dienTich;
    }

    public void setDienTich(long dienTich) {
        this.dienTich = dienTich;
    }

    public String getLoaiBDS() {
        return loaiBDS;
    }

    public void setLoaiBDS(String loaiBDS) {
        this.loaiBDS = loaiBDS;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}

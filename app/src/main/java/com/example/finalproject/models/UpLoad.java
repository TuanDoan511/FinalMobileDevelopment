package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UpLoad implements Serializable {
    public String tieuDe;
    public long giaBan;
    public long dienTich;
    public String loaiBDS;
    public List<String> mImageUrl;
    public  String moTa;
    public Date date;
    public  String id_BaiDang;
    public  String id_User_BaiDang;

    public UpLoad() {

    }

    public UpLoad(String id_User_BaiDang,String id_BaiDang, String tieuDe, long giaBan, long dienTich, String loaiBDS, List<String> mImageUrl, String moTa,Date date) {
        this.tieuDe = tieuDe;
        this.giaBan = giaBan;
        this.dienTich = dienTich;
        this.loaiBDS = loaiBDS;
        this.mImageUrl = mImageUrl;
        this.moTa= moTa;
        this.date = date;
        this.id_BaiDang = id_BaiDang;
        this.id_User_BaiDang=id_User_BaiDang;
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

    public List<String> getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(List<String> mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getId_BaiDang() {
        return id_BaiDang;
    }

    public void setId_BaiDang(String id_BaiDang) {
        this.id_BaiDang = id_BaiDang;
    }

    public String getId_User_BaiDang() {
        return id_User_BaiDang;
    }

    public void setId_User_BaiDang(String id_User_BaiDang) {
        this.id_User_BaiDang = id_User_BaiDang;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}

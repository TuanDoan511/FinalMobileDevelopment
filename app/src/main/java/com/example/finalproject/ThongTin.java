package com.example.finalproject;

import java.util.Date;

class ThongTin {
    public String tieuDe;
    public long gia;
    public Date thoigian;
    public String diaChi;

    public ThongTin(String tieuDe, long gia, Date thoigian, String diaChi) {
        this.tieuDe = tieuDe;
        this.gia = gia;
        this.thoigian = thoigian;
        this.diaChi = diaChi;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}

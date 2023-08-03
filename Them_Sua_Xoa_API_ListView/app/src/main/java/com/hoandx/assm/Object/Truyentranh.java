package com.hoandx.assm.Object;

import java.io.Serializable;

public class Truyentranh implements Serializable {
    String id;
    String name;
    String other;
    String mota;
    String binhluan;
    String img;
    String namxuatban;
    String anhtruyen;

    public Truyentranh(String id, String name, String other, String mota, String namxuatban, String img) {
        this.id = id;
        this.name = name;
        this.other = other;
        this.mota = mota;
        this.img = img;
        this.namxuatban = namxuatban;
    }

    public Truyentranh(String id, String name, String other, String mota, String binhluan, String img, String namxuatban, String anhtruyen) {
        this.id = id;
        this.name = name;
        this.other = other;
        this.mota = mota;
        this.binhluan = binhluan;
        this.img = img;
        this.namxuatban = namxuatban;
        this.anhtruyen = anhtruyen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNamxuatban() {
        return namxuatban;
    }

    public void setNamxuatban(String namxuatban) {
        this.namxuatban = namxuatban;
    }

    public String getAnhtruyen() {
        return anhtruyen;
    }

    public void setAnhtruyen(String anhtruyen) {
        this.anhtruyen = anhtruyen;
    }
}

package com.hoandx.assm.Object;

import java.io.Serializable;

public class Binhluan implements Serializable {
    String id;
    String idTruyen;
    String idNguoidung;
    String noidung;
    String date;

    public Binhluan(String id, String idTruyen, String idNguoidung, String noidung, String date) {
        this.id = id;
        this.idTruyen = idTruyen;
        this.idNguoidung = idNguoidung;
        this.noidung = noidung;
        this.date = date;
    }

    public Binhluan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getIdNguoidung() {
        return idNguoidung;
    }

    public void setIdNguoidung(String idNguoidung) {
        this.idNguoidung = idNguoidung;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

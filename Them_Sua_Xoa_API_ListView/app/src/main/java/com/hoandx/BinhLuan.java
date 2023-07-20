package com.hoandx;

public class BinhLuan {
    String id;
    String idUser;
    String idTruyen;
    String nameTruyen;
    String date;
    String noidung;

    public BinhLuan() {
    }

    public BinhLuan(String id, String idUser, String idTruyen, String nameTruyen, String date, String noidung) {
        this.id = id;
        this.idUser = idUser;
        this.idTruyen = idTruyen;
        this.nameTruyen = nameTruyen;
        this.date = date;
        this.noidung = noidung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getNameTruyen() {
        return nameTruyen;
    }

    public void setNameTruyen(String nameTruyen) {
        this.nameTruyen = nameTruyen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}

package com.hoandx.assm;

public class API_Class {
    public static final String url="192.168.0.100";
    public static final String API_LOGIN="http://"+url+":3000/api/user/login";
    public static final String API_SIGN="http://"+url+":3000/api/user/sign";
    public static final String API_LIST="http://"+url+":3000/api/listTruyentranh";
    public static final String API_LISTBL="http://"+url+":3000/api/listTruyentranh/binhluan/";
    public static final String API_ADDBL="http://"+url+":3000/api/listTruyentranh/binhluan/";
    public static final String API_EDITBL="http://"+url+":3000/api/listTruyentranh/binhluan/update/";
    public static final String API_DELETEBL="http://"+url+":3000/api/listTruyentranh/binhluan/delete/";
    public static final String API_LIST_IMAGE="http://"+url+":3000/api/listTruyentranh/anhtruyen/";
    public static final String API_LIST_USER="http://"+url+":3000/api/listTruyentranh/user/";
    public static final String API_EDIT_USER="http://"+url+":3000/api/listTruyentranh/user/";
}

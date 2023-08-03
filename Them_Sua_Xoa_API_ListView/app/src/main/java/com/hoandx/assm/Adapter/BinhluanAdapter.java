package com.hoandx.assm.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoandx.assm.Object.Binhluan;
import com.hoandx.assm.Object.Truyentranh;
import com.hoandx.assm.Object.User;
import com.hoandx.assm.R;


import java.util.ArrayList;

public class BinhluanAdapter extends BaseAdapter {

    ArrayList<Binhluan> list= new ArrayList<>();
    private int layout;
    private Context mContext;

    String fullnameBL;

//    public BinhluanAdapter(Context mContext, ArrayList<Binhluan> arrayList, int layout) {
//        this.mContext = mContext;
//        this.list = arrayList;
//        this.layout = layout;
//    }

    public BinhluanAdapter(Context mContext, ArrayList<Binhluan> arrayList, int layout, String fullnameBL) {
        this.mContext = mContext;
        this.list = arrayList;
        this.layout = layout;
        this.fullnameBL = fullnameBL;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Binhluan binhluan = list.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(layout, null);
        }

        TextView idNguoidung = convertView.findViewById(R.id.idNguoidungBL);
        TextView date = convertView.findViewById(R.id.dateNL);
        TextView noidung = convertView.findViewById(R.id.noidungBL);
        ImageView img = convertView.findViewById(R.id.idimgBL);




       if(fullnameBL==null){
           idNguoidung.setText(list.get(position).getIdNguoidung());
       }else {
           idNguoidung.setText(fullnameBL);
       }
        date.setText(list.get(position).getDate());
        noidung.setText(list.get(position).getNoidung());


        //Nếu bình luận cần ảnh User
        //                 Glide.with(mContext)
        //                .load(list.get(position).getImg())
        //                .into(img);
        return convertView;

    }

}

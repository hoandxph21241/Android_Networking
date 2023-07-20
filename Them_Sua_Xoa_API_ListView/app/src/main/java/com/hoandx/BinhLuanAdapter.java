package com.hoandx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BinhLuanAdapter extends BaseAdapter {

    ArrayList<BinhLuan> list= new ArrayList<>();
    private int layout;
    private Context mContext;

    public BinhLuanAdapter(Context mContext, ArrayList<BinhLuan> arrayList, int layout) {
        this.mContext = mContext;
        this.list = arrayList;
        this.layout = layout;
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
        LayoutInflater mInflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BinhLuan binhLuan = list.get(position);
        if(convertView==null){
            convertView= mInflater.inflate(layout,null);


            TextView idUser= convertView.findViewById(R.id.id_User);
            TextView idTruyen= convertView.findViewById(R.id.idTruyen);
            TextView nameTruyen= convertView.findViewById(R.id.nameTruyen);
            TextView date= convertView.findViewById(R.id.date);
            TextView noidung= convertView.findViewById(R.id.noidung);


            idUser.setText(list.get(position).getIdUser());
            idTruyen.setText(list.get(position).getIdTruyen());
            nameTruyen.setText(list.get(position).getNameTruyen());
            date.setText(list.get(position).getDate());
            noidung.setText(list.get(position).getNoidung());

        }

        return convertView;
    }
}

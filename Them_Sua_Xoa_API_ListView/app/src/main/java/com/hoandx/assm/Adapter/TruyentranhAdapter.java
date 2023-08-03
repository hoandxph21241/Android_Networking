package com.hoandx.assm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoandx.assm.Object.Truyentranh;
import com.hoandx.assm.R;

import java.util.ArrayList;

public class TruyentranhAdapter extends BaseAdapter {

    ArrayList<Truyentranh> list= new ArrayList<>();
    private int layout;
    private Context mContext;

    public TruyentranhAdapter(Context mContext, ArrayList<Truyentranh> arrayList, int layout) {
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
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Truyentranh truyentranh = list.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(layout, null);
        }

        TextView nameTruyen = convertView.findViewById(R.id.idname);
        TextView other = convertView.findViewById(R.id.idother);
        TextView mota = convertView.findViewById(R.id.idmota);
        ImageView img = convertView.findViewById(R.id.idimg);
        TextView namxuatban = convertView.findViewById(R.id.idnamxuatban);

        nameTruyen.setText(list.get(position).getName());
        other.setText(list.get(position).getOther());
        mota.setText(list.get(position).getMota());
        Glide.with(mContext)
                .load(list.get(position).getImg())
                .into(img);
        namxuatban.setText(list.get(position).getNamxuatban());

        return convertView;

    }
}

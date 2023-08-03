package com.hoandx.assm.Adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hoandx.assm.Object.Binhluan;
import com.hoandx.assm.Object.image;
import com.hoandx.assm.R;

import java.util.ArrayList;
import java.util.List;

public class imageAdapter extends BaseAdapter {

    ArrayList<image> list= new ArrayList<>();
    private int layout;
    private Context mContext;

    public imageAdapter(Context mContext, ArrayList<image> arrayList, int layout) {
        this.mContext = mContext;
       this.list = arrayList;
       this.layout = layout;
    }

//    public imageAdapter(Context mContext, List<image> arrayList) {
//        this.mContext = mContext;
//        this.list = (ArrayList<image>) arrayList;
//        this.layout = layout;
//    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int  position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(layout, null);
        }
        if (mContext == null) {
            mContext = mInflater.inflate(layout, null).getContext();
        }


        ImageView imageView1 = convertView.findViewById(R.id.image_1);
        ImageView imageView2 = convertView.findViewById(R.id.image_2);
        ImageView imageView3 = convertView.findViewById(R.id.image_3);
        ImageView imageView4 = convertView.findViewById(R.id.image_4);
        ImageView imageView5 = convertView.findViewById(R.id.image_5);

        Log.d("imageAdapter", "mContext: " + mContext);
        Log.d("imageAdapter", "getImg1: " + list.get(position).getImg1());
        Log.d("imageAdapter", "getImg2: " + list.get(position).getImg2());
        Log.d("imageAdapter", "getImg3: " + list.get(position).getImg3());
        Log.d("imageAdapter", "getImg4: " + list.get(position).getImg4());
        Log.d("imageAdapter", "getImg5: " + list.get(position).getImg5());

//        SimpleDraweeView imageView1 = convertView.findViewById(R.id.image_1);
//        SimpleDraweeView imageView2 = convertView.findViewById(R.id.image_2);
//        SimpleDraweeView imageView3 = convertView.findViewById(R.id.image_3);
//        SimpleDraweeView imageView4 = convertView.findViewById(R.id.image_4);
//        SimpleDraweeView imageView5 = convertView.findViewById(R.id.image_5);

        if(imageView1 !=null){
            Glide.with(mContext)
                    .load(list.get(position).getImg1())
                    .into(imageView1);
        }
        Glide.with(mContext)
                .load(list.get(position).getImg2())
                .into(imageView2);
        Glide.with(mContext)
                .load(list.get(position).getImg3())
                .into(imageView3);
        Glide.with(mContext)
                .load(list.get(position).getImg4())
                .into(imageView4);
        Glide.with(mContext)
                .load(list.get(position).getImg5())
                .into(imageView5);


//        imageView1.setImageURI(list.get(position).getImg1());
//        imageView2.setImageURI(list.get(position).getImg2());
//        imageView3.setImageURI(list.get(position).getImg3());
//        imageView4.setImageURI(list.get(position).getImg4());
//        imageView5.setImageURI(list.get(position).getImg5());


        return convertView;

    }
}

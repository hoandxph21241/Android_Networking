package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hoandx.assm.Adapter.imageAdapter;
import com.hoandx.assm.Object.Binhluan;
import com.hoandx.assm.Object.Truyentranh;
import com.hoandx.assm.Object.image;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Doctruyen extends AppCompatActivity {

    ArrayList<Truyentranh> listTR = new ArrayList<>();
    ArrayList<image>list=new ArrayList<>();
    ListView listView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctruyen);
        progressBar=findViewById(R.id.progessbar);

        listView=findViewById(R.id.list_truyen);

        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");

       loadImageData();
    }
    public void loadImageData() {
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("itemId");

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_Class.API_LIST_IMAGE+itemId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    image images;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        if (object.has("_id") && object.has("img1") && object.has("img2") && object.has("img3") && object.has("img4")&& object.has("img5")&& object.has("idTruyen")) {
                            images  = new image(
                                    object.getString("_id"),
                                    object.getString("idTruyen"),
                                    object.getString("img1"),
                                    object.getString("img2"),
                                    object.getString("img3"),
                                    object.getString("img4"),
                                    object.getString("img5")

                            );
                            list.add(images);
                        }
                    }
                    imageAdapter adapter = new imageAdapter(getApplicationContext(), list, R.layout.layout_item_image);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse: Lỗi", error.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
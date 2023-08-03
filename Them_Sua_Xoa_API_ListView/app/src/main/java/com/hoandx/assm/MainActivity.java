package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hoandx.assm.Adapter.TruyentranhAdapter;
import com.hoandx.assm.Object.Truyentranh;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageView icon_user;
    ProgressBar progressBar;
    ListView listView;
    ArrayList<Truyentranh> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progessbar);
        listView = findViewById(R.id.list_item);
        icon_user=findViewById(R.id.icon_user);
        loadData();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trong màn hình trước đó
                Intent intent = new Intent(MainActivity.this, ManHinhUser.class);
                startActivity(intent);
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Truyentranh clickedItem = (Truyentranh) parent.getItemAtPosition(position);
                String itemId = clickedItem.getId();

                // Trong màn hình trước đó
                Intent intent = new Intent(MainActivity.this, ManHinhChiTiet.class);
                intent.putExtra("itemId", itemId);
                intent.putExtra("dataList", list);
                startActivity(intent);


            }
        });


    }

    public void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_Class.API_LIST, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    JSONArray jsonArray = response;
                    Truyentranh truyentranh;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.has("_id") && object.has("name") && object.has("other") && object.has("mota") && object.has("namxuatban") && object.has("img")) {
                            truyentranh = new Truyentranh(
                                    object.getString("_id"),
                                    object.getString("name"),
                                    object.getString("other"),
                                    object.getString("mota"),
                                    object.getString("namxuatban"),
                                    object.getString("img")
                            );
                            list.add(truyentranh);
                        }
                    }
                    //log list
                    TruyentranhAdapter truyentranhAdapter = new TruyentranhAdapter(getApplicationContext(), list, R.layout.layout_item_truyentranh);
                    listView.setAdapter(truyentranhAdapter);


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

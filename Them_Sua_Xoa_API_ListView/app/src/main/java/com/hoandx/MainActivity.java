package com.hoandx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn,load,edit,del;
    EditText idUser,idTruyen,nameTruyen,date,noidung;

    ListView listView;

    ProgressBar progressBar;

    ArrayList<BinhLuan> list= new ArrayList<>();
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn_sub);
        idUser=findViewById(R.id.idUsers);
        idTruyen=findViewById(R.id.idTruyens);
        nameTruyen=findViewById(R.id.nameTruyens);
        date=findViewById(R.id.dates);
        noidung=findViewById(R.id.noidungs);
        listView=findViewById(R.id.list);
        progressBar=findViewById(R.id.progessbar);
        load=findViewById(R.id.btnload);
        edit=findViewById(R.id.btn_edit);
        del=findViewById(R.id.btn_delete);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BinhLuan binhLuan = (BinhLuan) listView.getAdapter().getItem(position);
                String binhLuanId = binhLuan.getId();

                idUser.setText(binhLuan.getIdUser());
                idTruyen.setText(binhLuan.getIdTruyen());
                nameTruyen.setText(binhLuan.getNameTruyen());
                date.setText(binhLuan.getDate());
                noidung.setText(binhLuan.getNoidung());


                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONObject object= new JSONObject();
                            object.put("idUser",idUser.getText().toString());
                            object.put("idTruyen",idTruyen.getText().toString());
                            object.put("nameTruyen",nameTruyen.getText().toString());
                            object.put("date",date.getText().toString());
                            object.put("noidung",noidung.getText().toString());
                            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.PUT,Api_Class.API_EDIT+binhLuanId, object, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Trả về thành công
                                    Log.d(String.valueOf(getApplicationContext()), "Thành công: "+object+response);
                                    Toast.makeText(getApplicationContext(), "UPDATE Thành Công", Toast.LENGTH_SHORT).show();
                                    idTruyen.setText("");
                                    idUser.setText("");
                                    nameTruyen.setText("");
                                    noidung.setText("");
                                    date.setText("");
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Trả về lỗi
                                    Log.e(String.valueOf(getApplicationContext()), "Lỗi: "+error);
                                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(jsonObjectRequest);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                            JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.DELETE, Api_Class.API_DELETE+binhLuanId, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue.add(objectRequest);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                });
            }
        });
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object= new JSONObject();
                            object.put("idUser",idUser.getText().toString());
                            object.put("idTruyen",idTruyen.getText().toString());
                            object.put("nameTruyen",nameTruyen.getText().toString());
                            object.put("date",date.getText().toString());
                            object.put("noidung",noidung.getText().toString());
                    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST,Api_Class.API_POST, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Trả về thành công
                            Log.d(String.valueOf(getApplicationContext()), "Thành công: "+object+response);
                            Toast.makeText(getApplicationContext(), "PUT Thành Công", Toast.LENGTH_SHORT).show();
                            idTruyen.setText("");
                            idUser.setText("");
                            nameTruyen.setText("");
                            noidung.setText("");
                            date.setText("");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Trả về lỗi
                            Log.e(String.valueOf(getApplicationContext()), "Lỗi: "+error);
                            Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Api_Class.API_GET, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            list.clear();
                            JSONArray jsonArray = response;
                            BinhLuan binhLuan;
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject object= jsonArray.getJSONObject(i);
                                if (object.has("_id") &&object.has("idUser") && object.has("idTruyen") && object.has("nameTruyen") && object.has("date") && object.has("noidung")) {
                                    binhLuan = new BinhLuan(
                                            object.getString("_id"),
                                            object.getString("idUser"),
                                            object.getString("idTruyen"),
                                            object.getString("nameTruyen"),
                                            object.getString("date"),
                                            object.getString("noidung")
                                    );
                                    list.add(binhLuan);
                                }
                            }
                            //log list
                            BinhLuanAdapter binhLuanAdapter = new BinhLuanAdapter(getApplicationContext(),list,R.layout.item_binhluan);
                            listView.setAdapter(binhLuanAdapter);


                            progressBar.setVisibility(View.INVISIBLE);
                        }catch (Exception exception ){
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
        });

    }
}
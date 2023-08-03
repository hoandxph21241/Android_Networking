package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManHinhDnDk extends AppCompatActivity {
    String username,fullname;
    RequestQueue requestQueue;
    Button btn_dn,btn_dk;
    EditText edi_username,edt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dn_dk);
        btn_dn=findViewById(R.id.btn_dangnhap);
        btn_dk=findViewById(R.id.btn_dangky);
        edi_username=findViewById(R.id.id_edt_username);
        edt_password=findViewById(R.id.id_edt_password);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, API_Class.API_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                // Đăng nhập thành công
                                Toast.makeText(getApplicationContext(), "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                                // Lưu thông tin người dùng vào SharedPreferences
                                String id = jsonObject.getString("id");
                                //  String idNguoidung = jsonObject.getString("id");
                                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", username);
                                editor.putString("fullname", fullname);
                                //  editor.putString("idNguoidung", idNguoidung);
                                editor.putString("id", id); // Lưu giá trị id vào SharedPreferences

                                editor.apply();

                                // Chuyển sang màn hình chính
                                Intent intent = new Intent(ManHinhDnDk.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // Đăng nhập thất bại
                                Toast.makeText(getApplicationContext(), "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                                // Hiển thị thông báo lỗi cho người dùng
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse: Lỗi", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", edi_username.getText().toString());
                        params.put("password", edt_password.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang màn hình đăng ký
                Intent intent = new Intent(ManHinhDnDk.this,ManHinhDangKy.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
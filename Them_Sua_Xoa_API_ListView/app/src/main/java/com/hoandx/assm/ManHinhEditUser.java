package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ManHinhEditUser extends AppCompatActivity {
    Button btn_doimk;
    EditText edt_fullname,edt_pass,edt_pass2;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_edit_user);
        btn_doimk=findViewById(R.id.btn_doimk);
        edt_fullname=findViewById(R.id.id_edt_user_fullname);
        edt_pass=findViewById(R.id.id_edt_user_password);
        edt_pass2=findViewById(R.id.id_edt_user_password2);
        progressBar=findViewById(R.id.progessbar_edt_user);

     btn_doimk.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String fullname = edt_fullname.getText().toString();
             String password = edt_pass.getText().toString();
             String password2 = edt_pass2.getText().toString();

             updateUser(fullname, password, password2);
         }
     });



    }
    public void updateUser(String fullname, String password, String password2) {
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String idND = sharedPreferences.getString("id", "");
        Log.d(String.valueOf(getApplicationContext()), "loadUser: "+idND);
        if (password.equals(password2)) {
            // Lưu thông tin người dùng
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, API_Class.API_EDIT_USER+idND,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Xử lý phản hồi từ server
                            Toast.makeText(ManHinhEditUser.this, "UPDATE Thành Công", Toast.LENGTH_SHORT).show();
                            Log.d(String.valueOf(getApplicationContext()), "onResponse: "+response);

                            // Về màn hình DK sau 5s
                            Toast.makeText(ManHinhEditUser.this, "Trở Về Màn Hình Đăng Ký Sau 5s", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Retun();
                                }
                            }, 7100);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Xử lý lỗi
                    Toast.makeText(ManHinhEditUser.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    Log.e(String.valueOf(getApplicationContext()), "onError: "+error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("fullname", String.valueOf(fullname));
                    params.put("password", String.valueOf(password));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            // Hiển thị thông báo lỗi
            Log.e(String.valueOf(getApplicationContext()), "Lỗi: EDITUSER");
        }
    }
    private void Retun(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(ManHinhEditUser.this, ManHinhDnDk.class);
        startActivity(intent);
    }
}
package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ManHinhDangKy extends AppCompatActivity {
    Button btn_back,btn_dngky;
    EditText edt_username,edt_fullname,edt_password,edt_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_ky);
        btn_back=findViewById(R.id.btn_backs);
        btn_dngky=findViewById(R.id.btn_dangkys);
        edt_username=findViewById(R.id.id_edt_usernames);
        edt_password=findViewById(R.id.id_edt_passwords);
        edt_email=findViewById(R.id.id_edt_emails);
        edt_fullname=findViewById(R.id.id_edt_fullnames);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang màn hình chính
                Intent intent = new Intent(ManHinhDangKy.this, ManHinhDnDk.class);
                startActivity(intent);
                finish();
            }
        });
        btn_dngky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Lấy thông tin đăng ký từ các trường nhập liệu
                    String username = edt_username.getText().toString();
                    String password = edt_password.getText().toString();
                    String email = edt_email.getText().toString();
                    String fullname = edt_fullname.getText().toString();

                    // Kiểm tra tính hợp lệ của thông tin đăng ký
                    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullname.isEmpty()) {
                        // Thông tin đăng ký không hợp lệ
                        // Hiển thị thông báo lỗi cho người dùng
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo một đối tượng RequestQueue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    // Tạo một đối tượng StringRequest
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, API_Class.API_SIGN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Xử lý phản hồi từ máy chủ
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    // Đăng ký thành công
                                    Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                    // Chuyển sang màn hình đăng nhập hoặc màn hình chính
                                    Intent intent = new Intent(ManHinhDangKy.this, ManHinhDnDk.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Đăng ký thất bại
                                    Toast.makeText(getApplicationContext(), "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                                    // Hiển thị thông báo lỗi cho người dùng
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Xử lý lỗi khi gửi yêu cầu đến máy chủ
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Vui Lòng Chờ", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // Đưa thông tin đăng ký vào trong yêu cầu gửi đến máy chủ
                            Map<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            params.put("email", email);
                            params.put("fullname", fullname);
                            return params;
                        }
                    };

                    // Thêm yêu cầu vào RequestQueue để gửi đến máy chủ
                    requestQueue.add(stringRequest);
            }
        });

    }
}
package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hoandx.assm.Object.Binhluan;
import com.hoandx.assm.Object.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManHinhUser extends AppCompatActivity {
     TextView usernameTv;
     TextView fullnameTv;

    TextView emailTV;
    ProgressBar progressBar;

    Button btn_kill,btn_edt;

    ArrayList<User> listUs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_user);

        usernameTv = findViewById(R.id.usernameTv);
        fullnameTv = findViewById(R.id.fullnameTv);
        emailTV = findViewById(R.id.edmaiTv);
        progressBar=findViewById(R.id.progessbar);
        btn_kill=findViewById(R.id.btn_end);
        btn_edt=findViewById(R.id.btn_edt);

        btn_kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(ManHinhUser.this, ManHinhDnDk.class);
                startActivity(intent);
            }
        });

        btn_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();

                Intent intent = new Intent(ManHinhUser.this, ManHinhEditUser.class);
                startActivity(intent);
            }
        });


loadUser();

    }
    public void loadUser(){
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String idND = sharedPreferences.getString("id", "");
        Log.d(String.valueOf(getApplicationContext()), "loadUser: "+idND);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(API_Class.API_LIST_USER + idND, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                  try {
                      Intent intent = getIntent();
                      JSONObject jsonObject = response;
                      User user;

                          JSONObject object = jsonObject;
                          if (object.has("_id") && object.has("username") && object.has("fullname") && object.has("password") && object.has("email")) {
                              user = new User(
                                      object.getString("_id"),
                                      object.getString("username"),
                                      object.getString("fullname"),
                                      object.getString("password"),
                                      object.getString("email")
                              );
                              listUs.add(user);

                          Log.d("onResponse: ", response.toString());
                              usernameTv.setText(user.getUsername());
                              fullnameTv.setText(user.getFullname());
                              emailTV.setText(user.getEmail());
                      }
                      progressBar.setVisibility(View.INVISIBLE);
                  }catch (Exception exception){
                      Log.e("Error: ", exception.toString());
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
        requestQueue.add(jsonObjectRequest);
    }

}
package com.hoandx.assm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

public class ManHinhChao extends AppCompatActivity {
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        layout=findViewById(R.id.linnerchao);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(ManHinhChao.this,ManHinhDnDk.class);
                startActivity(intent);
            }
        },3000);
    }
}
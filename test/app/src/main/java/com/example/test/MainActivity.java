package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button search;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.button7);
        search = findViewById(R.id.button2);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connMgr.getActiveNetworkInfo();

        update=findViewById(R.id.button3);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (networkInfo != null && networkInfo.isAvailable()) {
                    //取得最新資料
                }
                else {
                    Toast.makeText(com.example.test.MainActivity.this, "請連接網路", Toast.LENGTH_SHORT).show();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.MainActivity.this, com.example.test.Search.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.MainActivity.this, com.example.test.LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
package com.example.test;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Manage extends AppCompatActivity {
    Button logout;
    Button update;
    Button report;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);

        logout = findViewById(R.id.button7);
        report = findViewById(R.id.button8);
        update=findViewById(R.id.button3);
        textView=findViewById(R.id.imageView4);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connMgr.getActiveNetworkInfo();
       /* Thread thread = new Thread(mutiThread);
        thread.start();*/
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (networkInfo != null && networkInfo.isAvailable()) {
                    //連接線上資料庫並更新
                }
                else {
                    Toast.makeText(Manage.this, "請連接網路", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Manage.this, MainActivity.class);
                startActivity(intent);
                finish();
                //登出資料庫
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Manage.this, Report.class);
                startActivity(intent);
            }
        });

    }

}

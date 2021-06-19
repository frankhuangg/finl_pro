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

public class AfterLog extends AppCompatActivity {
    Button logout;
    Button search;
    Button update;
    Button report;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlog);

        logout = findViewById(R.id.button7);
        search = findViewById(R.id.button2);
        report = findViewById(R.id.button8);
        textView=findViewById(R.id.textView4);

        Intent intentt=getIntent();
        String name = intentt.getStringExtra("name");
        textView.setText(name);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connMgr.getActiveNetworkInfo();

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AfterLog.this, com.example.test.Search.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AfterLog.this, MainActivity.class);
                startActivity(intent);
                finish();
                //登出資料庫
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AfterLog.this, Report.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

    }
}
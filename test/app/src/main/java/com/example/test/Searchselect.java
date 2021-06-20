package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class Searchselect extends AppCompatActivity {
    private Button h;
    private Button p;
    private Button e;
    private Button f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchselect);

        h=findViewById(R.id.h);
        p = findViewById(R.id.p);
        f=findViewById(R.id.f);
        e=findViewById(R.id.e);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connMgr.getActiveNetworkInfo();

        h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Searchselect.this, com.example.test.SearchhName.class);
                startActivity(intent);
                finish();
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Searchselect.this, com.example.test.SearcheName.class);
                startActivity(intent);
                finish();
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Searchselect.this, SearchpName.class);
                startActivity(intent);
                finish();
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Searchselect.this, SearchfName.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
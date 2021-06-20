package com.example.test;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchfName extends AppCompatActivity {
    private Button sent;
    private EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfname);

        findViews();

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textView.getText().toString();
                if(textView.getText().toString().matches(""))
                {
                    Toast.makeText(SearchfName.this, "請輸入內容", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SearchfName.this, ViewfName.class);
                    intent.putExtra("name",name);
                    startActivity(intent);

                }

            }
        });
    }
    private void findViews() {
        sent=findViewById(R.id.sent);
        textView=findViewById(R.id.searchname);

    }
}

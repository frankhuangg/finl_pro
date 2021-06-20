package com.example.test;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchpName extends AppCompatActivity {
    private Button sent;
    private EditText textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpname);

        findViews();

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textView.getText().toString();
                if(textView.getText().toString().matches(""))
                {
                    Toast.makeText(SearchpName.this, "請輸入內容", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "回報成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchpName.this, ViewpName.class);
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

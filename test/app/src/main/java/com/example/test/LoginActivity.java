package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//即activity_login.xml
        findViews();
    }
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private ProgressBar progressBar;

    private void findViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        progressBar = findViewById(R.id.progress);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connMgr.getActiveNetworkInfo();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    if(username.getText().toString().matches("")){
                        Toast.makeText(com.example.test.LoginActivity.this, "欄位不能是空白", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "username";
                                field[1] = "password";
                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = name;
                                data[1] = pass;
                                String user = name;
                                SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
                                pref.edit()
                                        .putString("USER", user)
                                        .commit();
                                PutData putData = new PutData("http://52.203.34.134/Loginregister/login.php", "POST", field, data);//url要改成自己的本機ip
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Login Success")) {
                                            Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, AfterLog.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(com.example.test.LoginActivity.this, "登入失敗", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });

                    }
                }
                else {
                    Toast.makeText(com.example.test.LoginActivity.this, "請連接網路", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (networkInfo != null && networkInfo.isAvailable()){
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(com.example.test.LoginActivity.this, "請連接網路", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button register;
    ProgressBar progressBar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_register);
        findViews();
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name, pass;
                name = String.valueOf(username.getText().toString());
                pass= String.valueOf(password.getText().toString());
                if(name.equals("") || pass.equals("")){
                    Toast.makeText(com.example.test.RegisterActivity.this, "欄位不能是空白", Toast.LENGTH_SHORT).show();
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
                            PutData putData = new PutData("http://52.203.34.134/Loginregister/signup.php", "POST", field, data);//url要改成自己的本機ip
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"註冊失敗", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }
            }
        });
    }
    private void findViews() {
        username=(EditText) findViewById(R.id.usernameRegister);
        password=(EditText) findViewById(R.id.passwordRegister);
        register=(Button) findViewById(R.id.Register);
        progressBar=findViewById(R.id.progress);
    }

}

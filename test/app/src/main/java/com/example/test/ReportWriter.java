package com.example.test;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ReportWriter extends AppCompatActivity {
    private Button Commit;
    private EditText Views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportwriter);
        Intent intentt=getIntent();
        String name = intentt.getStringExtra("name");
        Views=findViewById(R.id.View);
        Commit=findViewById(R.id.save_button);

        Commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report = Views.getText().toString();
                System.out.println(report);
                if(Views.getText().toString().matches("")){
                    Toast.makeText(ReportWriter.this, "請輸入內容", Toast.LENGTH_SHORT).show();
                }
                else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "report";
                            field[1]="username";
                            //Creating array for data
                            String username = getSharedPreferences("test", MODE_PRIVATE)
                                    .getString("USER", "");
                            String[] data = new String[2];
                            data[0] = report;
                            data[1]=username;
                            PutData putData = new PutData("http://52.203.34.134/Loginregister/report.php" ,"POST", field, data);//url要改成自己的本機ip
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Report Success")) {
                                        Toast.makeText(getApplicationContext(), "回報成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ReportWriter.this, Report.class);
                                        intent.putExtra("name",name);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"回報失敗", Toast.LENGTH_SHORT).show();
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




}
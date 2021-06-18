package com.example.test;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Report extends AppCompatActivity {
     TextView textView;
     Button add;
     String result;
    // private ReportDb reportDb;//資料庫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        textView=findViewById(R.id.report_list);
        add = findViewById(R.id.add_button);
        //setView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.Report.this, com.example.test.ReportWriter.class);
                startActivity(intent);
            }
        });
        Thread thread = new Thread(mutiThread);
        thread.start();
    }
    private Runnable mutiThread = new Runnable(){
        public void run()
        {
            try {
                URL url = new URL("http://52.203.34.134/Loginregister/GetData.php");
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result = box; // 把存放用字串放到全域變數
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) {
                result = e.toString(); // 如果出事，回傳錯誤訊息
            }

            // 當這個執行緒完全跑完後執行
            runOnUiThread(new Runnable() {
                public void run() {
                    textView.setText(result); // 更改顯示文字
                }
            });
        }
    };
}

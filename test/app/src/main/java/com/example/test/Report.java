package com.example.test;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Report extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
     TextView textView;
     Button add;
     String result;
    // private ReportDb reportDb;//資料庫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        add = findViewById(R.id.add_button);
        //setView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.Report.this, com.example.test.ReportWriter.class);
                startActivity(intent);
            }
        });
        catchData();
       /* Thread thread = new Thread(mutiThread);
        thread.start();*/
    }
   /* private Runnable mutiThread = new Runnable(){
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
    };*/
   private void catchData(){
       String catchData = "http://52.203.34.134/Loginregister/GetData.php";
       ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
               ,"請稍候",true);
       new Thread(()->{
           try {
               URL url = new URL(catchData);
               HttpURLConnection connection = (HttpURLConnection) url.openConnection();
               InputStream is = connection.getInputStream();
               BufferedReader in = new BufferedReader(new InputStreamReader(is));
               String line = in.readLine();
               StringBuffer json = new StringBuffer();
               while (line != null) {
                   json.append(line);
                   line = in.readLine();
               }

               JSONArray jsonArray= new JSONArray(String.valueOf(json));
               for (int i =0;i<jsonArray.length();i++){
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   String report = jsonObject.getString("report");
                   String username = jsonObject.getString("username");

                   HashMap<String,String> hashMap = new HashMap<>();
                   hashMap.put("username",username);
                   hashMap.put("report",report);
                   arrayList.add(hashMap);
               }
               Log.d(TAG, "catchData: "+arrayList);

               runOnUiThread(()->{
                   dialog.dismiss();
                   RecyclerView recyclerView;
                   MyAdapter myAdapter;
                   recyclerView = findViewById(R.id.recyclerView);
                   recyclerView.setLayoutManager(new LinearLayoutManager(this));
                   recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                   myAdapter = new MyAdapter();
                   recyclerView.setAdapter(myAdapter);

               });
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }).start();
   }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvPos,tvType,tvPrice,tvCar,tvDateTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvPos = itemView.findViewById(R.id.textView_pos);
                tvType = itemView.findViewById(R.id.textView_type);
                tvPrice = itemView.findViewById(R.id.textView_price);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvType.setText("username："+arrayList.get(position).get("username"));
            holder.tvPrice.setText("report："+arrayList.get(position).get("report"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

}

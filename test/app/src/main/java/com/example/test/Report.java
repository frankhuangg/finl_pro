package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;


public class Report extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    Button add;
    PutData putData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        add = findViewById(R.id.add_button);
        Intent intentt=getIntent();
        String name = intentt.getStringExtra("name");


        //获取Bundle的信息
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.Report.this, com.example.test.ReportWriter.class);
                intent.putExtra("name",name);
                startActivity(intent);
                finish();
            }
        });


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "username";
                //Creating array for data
                String[] data = new String[1];
                data[0] = name;
                putData = new PutData("http://52.203.34.134/Loginregister/Getpointdata.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                    catchData();

                 /*       try {
                                JSONArray ja = new JSONArray(result);
                                JSONObject jo = null;
                                String Name = "";
                                String Report = "";

                                for (int i = 0; i < ja.length(); i++) {
                                    jo = ja.getJSONObject(i);
                                    Name = jo.getString("username");
                                    Report = jo.getString("report");
                                    username.setText("username：" + Name);
                                    report.setText("report：" + Report);
                                }
//                               jo=ja.getJSONObject(0);
//                                String Name=jo.getString("To_Name");
//                                String Address=jo.getString("To_Address");
//                                String Admin=jo.getString("Administration");
//                            name.setText("名字："+name);
//                            address.setText("地址："+address);
//                            admin.setText("所屬單位："+admin);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }

                }
            }
        });

    }

   private void catchData(){

       ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
               ,"請稍候",true);
       new Thread(()->{
           try {
               JSONArray jsonArray= new JSONArray(String.valueOf(putData.getResult()));

               for (int i =0;i<jsonArray.length();i++){

                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                      String report = jsonObject.getString("report");
                      String username = jsonObject.getString("username");

                          HashMap<String, String> hashMap = new HashMap<>();
                          hashMap.put("username", username);
                          hashMap.put("report", report);
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
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }).start();
   }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView username,report;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                username = itemView.findViewById(R.id.textView_username);
                report = itemView.findViewById(R.id.textView_report);
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
            holder.username.setText("username："+arrayList.get(position).get("username"));
            holder.report.setText("report："+arrayList.get(position).get("report"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }


}

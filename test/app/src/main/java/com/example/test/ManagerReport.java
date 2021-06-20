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


public class ManagerReport extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    // private ReportDb reportDb;//資料庫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerreport);

        catchData();
    }
    private void catchData(){
        String catchData = "http://52.203.34.134/Loginregister/Getreport.php";
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

            TextView UserName,Report;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                UserName = itemView.findViewById(R.id.textView_username);
                Report = itemView.findViewById(R.id.textView_report);
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
            holder.UserName.setText("username："+arrayList.get(position).get("username"));
            holder.Report.setText("report："+arrayList.get(position).get("report"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

}
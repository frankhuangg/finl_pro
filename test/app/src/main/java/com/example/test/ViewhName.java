package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
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


public class ViewhName extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    PutData putData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewhname);

        Intent intentt=getIntent();
        String name = intentt.getStringExtra("name");


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "username";
                //Creating array for data
                String[] data = new String[1];
                data[0] = name;
                putData = new PutData("http://52.203.34.134/disaster/Gethdata.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        Log.e("result",putData.getResult());
                        catchData();
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
                    String name = jsonObject.getString("name");
                    String city = jsonObject.getString("cities");
                    String town = jsonObject.getString("town");
                    String road = jsonObject.getString("street");
                    String longitude = jsonObject.getString("longitude");
                    String latitude = jsonObject.getString("latitude");

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", name);
                    hashMap.put("city", city);
                    hashMap.put("town", town);
                    hashMap.put("road", road);
                    hashMap.put("longitude", longitude);
                    hashMap.put("latitude", latitude);
                    arrayList.add(hashMap);

                }


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

            TextView name,city,town,road,longitude,latitude;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textView_name);
                city = itemView.findViewById(R.id.textView_city);
                town = itemView.findViewById(R.id.textView_town);
                road = itemView.findViewById(R.id.textView_road);
                longitude = itemView.findViewById(R.id.textView_longitude);
                latitude = itemView.findViewById(R.id.textView_latitude);

            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.name,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText("name："+arrayList.get(position).get("name"));
            holder.city.setText("city："+arrayList.get(position).get("city"));
            holder.town.setText("town："+arrayList.get(position).get("town"));
            holder.road.setText("road："+arrayList.get(position).get("road"));
            holder.longitude.setText("longitude："+arrayList.get(position).get("longitude"));
            holder.latitude.setText("latitude："+arrayList.get(position).get("latitude"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }


}

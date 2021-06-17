package com.example.test;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Report extends AppCompatActivity {
    private ListView Views;
    private Button add;
    // private ReportDb reportDb;//資料庫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        Views = (ListView) findViewById(R.id.report_list);
        add = (Button) findViewById(R.id.add_button);
        //setView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.test.Report.this, com.example.test.ReportWriter.class);
                startActivity(intent);
            }
        });
    }


  /*  private void setView(){
        if(reportDb == null) {
            //建立資料庫
        }
        //將文字連接到資料庫並顯示
    }*/
}

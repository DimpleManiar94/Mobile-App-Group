package com.example.dimple.fastpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyBill extends AppCompatActivity {
    TextView listName;
    ListView billList;
    Button pay;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        pay = findViewById(R.id.payment);
        billList = findViewById(R.id.mybilllist);
        listName  = findViewById(R.id.listName);

        Intent i = getIntent();
        list = i.getStringArrayListExtra("bill");
        listName.setText(i.getStringExtra("listName"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyBill.this, android.R.layout.simple_list_item_1, list);
        billList.setAdapter(adapter);
        


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyBill.this,"YOUR PAYMENT DONE", Toast.LENGTH_SHORT).show();
            }
        });





    }
}

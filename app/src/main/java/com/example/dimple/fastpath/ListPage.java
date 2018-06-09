package com.example.dimple.fastpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListPage extends AppCompatActivity {

    ListView innerList;
    Button btn_shop;
    ArrayList<String> list;
    String listName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_page);

        innerList = findViewById(R.id.innerList);
        btn_shop = findViewById(R.id.shop);


        final Intent myIntent = getIntent();
        list = myIntent.getStringArrayListExtra("list");
        listName = myIntent.getStringExtra("listName");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListPage.this, android.R.layout.simple_list_item_1, list);
        innerList.setAdapter(adapter);

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListPage.this, MyShopList.class);
                i.putStringArrayListExtra("dmlist", list);
                i.putExtra("listName", listName);
                startActivity(i);
            }
        });

    }
}

package com.example.dimple.fastpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BillProducts extends AppCompatActivity {
    ListView billProducts;
    ArrayList<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_products);
        list = new ArrayList<String>();

        billProducts = findViewById(R.id.products_bill);
        Intent i = getIntent();
        list = i.getStringArrayListExtra("list");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BillProducts.this, android.R.layout.simple_list_item_1, list);
        billProducts.setAdapter(adapter);

    }
}

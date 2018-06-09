package com.example.dimple.fastpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RemainingListPage extends AppCompatActivity {
    ListView innerList;
    ArrayList<String> list;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_list_page);

        innerList = findViewById(R.id.remainingInnerList);

        final Intent myIntent = getIntent();
        list = myIntent.getStringArrayListExtra("list");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RemainingListPage.this, android.R.layout.simple_list_item_1, list);
        innerList.setAdapter(adapter);


    }


}

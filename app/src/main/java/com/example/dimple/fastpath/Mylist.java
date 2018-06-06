package com.example.dimple.fastpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mylist extends AppCompatActivity {

    private Button shop;
    private ListView mylist;
    private ArrayList<Productname> arrayList = new ArrayList<>();
    private ArrayAdapter<Productname> adapter;
    private DatabaseReference DatabaseRoot;
    private FirebaseDatabase database;
    private Productname productname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        database = FirebaseDatabase.getInstance();
        DatabaseRoot = database.getReference();
        adapter = new ArrayAdapter<Productname>(this, android.R.layout.simple_list_item_1, arrayList);
        //mylist.setAdapter(adapter);
        shop = findViewById(R.id.shop);
        productname = new Productname();

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Mylist.this, Lokcation.class);
                startActivity(i);
            }
        });

        DatabaseRoot.child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("pname").getValue(String.class);
                    if (areaName != null) {
                        areas.add(areaName);
                    }
                }
                mylist = findViewById(R.id.mylist);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Mylist.this, android.R.layout.simple_list_item_1, areas);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                mylist.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                        productname = ds.getValue(Productname.class);
//                        arrayList.add(productname);
//                }
//            mylist.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}

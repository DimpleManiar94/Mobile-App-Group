package com.example.dimple.fastpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BillHistory extends AppCompatActivity {
    ListView billList;
    BillHistoryAdapter adapter;
    FirebaseDatabase database;
    Query firebaseQuery;
    ArrayList<String> list;
    ArrayList<String> innerList;
    ArrayList<String> dateList;
    String listName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_history);

        billList = findViewById(R.id.billHistory);
        list = new ArrayList<String>();
        dateList = new ArrayList<String>();
        innerList = new ArrayList<String>();
        adapter = new BillHistoryAdapter(list, this, dateList);
        billList.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseQuery = database.getReference("billHistory").orderByChild("userID").equalTo(userID);

        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                dateList.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    list.add(data.child("listName").getValue(String.class));
                    dateList.add(data.child("date").getValue(String.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        billList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listName = list.get(position);
                firebaseQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            if(data.child("listName").getValue(String.class).equals(listName)){
                                innerList.clear();
                                for(DataSnapshot list: data.child("listItems").getChildren()){

                                    innerList.add(list.getValue(String.class));
                                }
                            }
                        }
                        Intent intent = new Intent(BillHistory.this, BillProducts.class);
                        intent.putExtra("list", innerList);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

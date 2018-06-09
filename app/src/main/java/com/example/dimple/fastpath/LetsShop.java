package com.example.dimple.fastpath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LetsShop extends AppCompatActivity {

    private ListView listofLists;
    private Button addNewList;
    private FirebaseDatabase database;
    private LetsShopAdapter adapter;
    private ArrayList<String> lists;
    Query listQuery;
    String listName;
    ArrayList<String> innerList = new ArrayList<String>();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lets_shop);

        listofLists = findViewById(R.id.listOfLists);
        addNewList = findViewById(R.id.addMoreList);
        lists = new ArrayList<String>();
        adapter = new LetsShopAdapter(lists, this);
        listofLists.setAdapter(adapter);

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LetsShop.this, Howtocreatelist.class);
                startActivity(i);

            }
        });

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        listQuery = database.getReference("userList").orderByChild("userID").equalTo(userID);

        listQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lists.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    lists.add(data.child("listName").getValue(String.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listofLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getId() == R.id.delete_btn){
                    listName = lists.get(position);
                    lists.remove(position); //or some other task
                    adapter.notifyDataSetChanged();
                    listQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                if(data.child("listName").getValue(String.class) == listName){
                                    data.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    listName = lists.get(position);
                    listQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                if(data.child("listName").getValue(String.class).equals(listName)){
                                    innerList.clear();
                                    for(DataSnapshot list: data.child("listItems").getChildren()){

                                        innerList.add(list.child("item").getValue(String.class));
                                    }
                                }
                            }
                            Intent intent = new Intent(LetsShop.this, ListPage.class);
                            intent.putExtra("list", innerList);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


    }
}

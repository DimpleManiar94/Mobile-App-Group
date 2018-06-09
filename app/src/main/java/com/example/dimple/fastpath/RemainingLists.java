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

public class RemainingLists extends AppCompatActivity {
    private ListView remainingList;
    private LetsShopAdapter adapter;
    ArrayList<String> listoflist;
    FirebaseDatabase database;
    Query firebaseQuery;
    ArrayList<String> innerList = new ArrayList<String>();
    String listName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_lists);

        remainingList = findViewById(R.id.remainingList);
        listoflist = new ArrayList<String>();
        adapter = new LetsShopAdapter(listoflist, this);
        remainingList.setAdapter(adapter);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        firebaseQuery = database.getReference("remainingList").orderByChild("userID").equalTo(userID);

        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listoflist.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    listoflist.add(data.child("listName").getValue(String.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        remainingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getId() == R.id.delete_btn){
                    listName = listoflist.get(position);
                    listoflist.remove(position); //or some other task
                    adapter.notifyDataSetChanged();
                    firebaseQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                if(data.child("listName").getValue(String.class).equals(listName)){
                                    data.getRef().removeValue();
                                }
                            }
                            // Remove this listener since we only want to get this
                            // item once
                            firebaseQuery.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    listName = listoflist.get(position);
                    firebaseQuery.addValueEventListener(new ValueEventListener() {
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
                            Intent intent = new Intent(RemainingLists.this, RemainingListPage.class);
                            intent.putExtra("list", innerList);
                            startActivity(intent);

                            // Remove this listener because we only need this item once
                            firebaseQuery.removeEventListener(this);
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

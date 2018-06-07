package com.example.dimple.fastpath;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FruitsFragment extends Fragment {

    FirebaseDatabase mFireBaseDatabase;
    Query databaseFruits;
    private Context context;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fruits_fragment, container, false);
        lv = (ListView) rootView.findViewById(R.id.fruitsListView);
        context = inflater.getContext();
        setUpDatabase();
        setUpItemListener();
        return rootView;
    }

    private void setUpDatabase(){
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        databaseFruits = mFireBaseDatabase.getReference("product").orderByChild("ptid")
                .equalTo("fruits");


        databaseFruits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> fruit = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    fruit.add(snapshot.child("pname").getValue(String.class));
                }

                String[] fruits = new String[fruit.size()];
                fruit.toArray(fruits);
                System.out.println(fruits);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item_layout, fruits);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }

    private void setUpItemListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                ((CreateListFromDB) getActivity()).getDataFromFragment(selectedItem);

            }
        });
    }

    @Override
    public String toString() {
        String title = "Fruits";
        return title;
    }
}

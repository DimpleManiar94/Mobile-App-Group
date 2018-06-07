package com.example.dimple.fastpath;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DryfruitsFragment extends Fragment{

    FirebaseDatabase mFireBaseDatabase;
    Query databaseDryFruits;
    private Context context;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dryfruits_fragment, container, false);
        lv = (ListView) rootView.findViewById(R.id.dryfruitsListView);
        context = inflater.getContext();
        setUpDatabase();
        setUpItemListener();
        return rootView;
    }

    private void setUpDatabase(){
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        databaseDryFruits = mFireBaseDatabase.getReference("product").orderByChild("ptid")
                .equalTo("dryfruits");


        databaseDryFruits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> fruits = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    fruits.add(snapshot.child("pname").getValue(String.class));
                }

                String[] dryfruits = new String[fruits.size()];
                fruits.toArray(dryfruits);
                System.out.println(dryfruits);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item_layout, dryfruits);
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
        String title = "Dry Fruits";
        return title;
    }
}

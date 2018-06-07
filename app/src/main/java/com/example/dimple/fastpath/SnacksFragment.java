package com.example.dimple.fastpath;

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

public class SnacksFragment extends Fragment {
    FirebaseDatabase mFireBaseDatabase;
    Query databaseSnacks;
    private Context context;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.snacks_fragment, container, false);
        lv = (ListView) rootView.findViewById(R.id.snacksListView);
        context = inflater.getContext();
        setUpDatabase();
        setUpItemListener();
        return rootView;
    }

    private void setUpDatabase(){
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        databaseSnacks = mFireBaseDatabase.getReference("product").orderByChild("ptid")
                .equalTo("snacks");


        databaseSnacks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> snack = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snack.add(snapshot.child("pname").getValue(String.class));
                }

                String[] snacks = new String[snack.size()];
                snack.toArray(snacks);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item_layout, snacks);
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
        String title = "Snacks";
        return title;
    }
}

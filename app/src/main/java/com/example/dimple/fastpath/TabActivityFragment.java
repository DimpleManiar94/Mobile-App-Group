package com.example.dimple.fastpath;

import android.annotation.SuppressLint;
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

@SuppressLint("ValidFragment")
public class TabActivityFragment extends Fragment {

    private String productType;
    private Context context;
    private FirebaseDatabase mFirebaseDatabase;
    private Query firebaseQuery;
    private ListView fragmentListView;

    private static final String PRODUCT_TYPE = "productType";

    public static TabActivityFragment getInstance(String productType){
        Bundle bundle = new Bundle();
        bundle.putString(PRODUCT_TYPE, productType);
        TabActivityFragment fragment = new TabActivityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activity_fragment, container, false);
        fragmentListView = (ListView) rootView.findViewById(R.id.fragmentListView);
        context = inflater.getContext();
        productType = getArguments().getString(PRODUCT_TYPE);
        setUpDatabase();
        setUpItemListener();
        return rootView;

    }

    private void setUpDatabase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        firebaseQuery = mFirebaseDatabase.getReference("product").orderByChild("ptid")
                .equalTo(productType);


        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> product = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    product.add(snapshot.child("pname").getValue(String.class));
                }

                String[] products = new String[product.size()];
                product.toArray(products);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item_layout, products);
                fragmentListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }

    private void setUpItemListener(){
        fragmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                ((CreateListFromDB) getActivity()).getDataFromFragment(selectedItem);

            }
        });
    }

    @Override
    public String toString() {
        return productType;
    }
}
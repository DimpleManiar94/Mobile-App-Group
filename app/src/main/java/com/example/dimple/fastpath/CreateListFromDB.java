package com.example.dimple.fastpath;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateListFromDB extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ListView usersList;
    private ArrayList<String> listItems = new ArrayList<>();
    private MyCustomAdapter adapter;
    private Button btn_shop;
    private Context context;
    private FirebaseDatabase database;
    private Query firebaseQuery;
    private ArrayList<String> productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_list_from_db);
        context = CreateListFromDB.this;

        usersList = findViewById(R.id.usersList);
        adapter = new MyCustomAdapter(listItems, this);
        usersList.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mViewPager = (ViewPager) findViewById(R.id.mViewPager_ID);
        makeFragments();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabLayout= (TabLayout) findViewById(R.id.mTab_ID);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);

        btn_shop = findViewById(R.id.shopbtn);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context); //Read Update
                alertDialog.setTitle("List Name");
                alertDialog.setMessage("Name your list");
                final EditText input = new EditText(CreateListFromDB.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listName = input.getText().toString();
                        UserList list = new UserList(listName);
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        list.setUserID(userID);
                        for(String item: listItems){
                            ListItem item1 = new ListItem(item);
                            list.getListItems().add(item1);

                        }
                        listItems.clear();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();


                        DatabaseReference myRef = database.getReference("userList").push();

                        myRef.setValue(list);
                        Intent i = new Intent(CreateListFromDB.this, MainActivity.class);
                        startActivity(i);
                    }
                });

                alertDialog.show();
            }
        });


    }

    public void getDataFromFragment(String item){
        listItems.add(item);
        adapter.notifyDataSetChanged();

    }


    private void makeFragments(){
        database = FirebaseDatabase.getInstance();
        firebaseQuery = database.getReference("producttype").orderByChild("producttypename");
        final MyPagerAdapter pagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());

        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> productTypes = new ArrayList<String>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    productTypes.add(data.child("producttypename").getValue(String.class));
                }

                for(String type: productTypes){
                    TabActivityFragment fragment = TabActivityFragment.getInstance(type);
                    pagerAdapter.addFragment(fragment, type);
                }

                mViewPager.setAdapter(pagerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



}


package com.example.dimple.fastpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBill extends AppCompatActivity {
    TextView listName;
    ListView billList;
    Button pay;
    ArrayList<String> list;
    String strListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        pay = findViewById(R.id.payment);
        billList = findViewById(R.id.mybilllist);
        listName  = findViewById(R.id.listName);

        Intent i = getIntent();
        list = i.getStringArrayListExtra("bill");
        listName.setText(i.getStringExtra("listName"));
        strListName = i.getStringExtra("listName");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyBill.this, android.R.layout.simple_list_item_1, list);
        billList.setAdapter(adapter);



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyBill.this,"YOUR PAYMENT DONE", Toast.LENGTH_SHORT).show();
                Date date = new Date();
                DateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy");
                String strDate = dateformat.format(date);

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Bill newBill = new Bill(strListName, strDate);
                newBill.setListItems(list);
                newBill.setUserID(userID);

                FirebaseDatabase database = FirebaseDatabase.getInstance();


                DatabaseReference myRef = database.getReference("billHistory").push();

                myRef.setValue(newBill);
                Intent i = new Intent(MyBill.this, MainActivity.class);
                startActivity(i);
            }
        });





    }
}

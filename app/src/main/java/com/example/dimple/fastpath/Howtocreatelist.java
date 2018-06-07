package com.example.dimple.fastpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Howtocreatelist extends AppCompatActivity {

    private Button camera, database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtocreatelist);

        camera = findViewById(R.id.fromcamera);
        database = findViewById(R.id.fromdatabase);



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Howtocreatelist.this,MainActivity1.class );
                startActivity(i);
            }
        });



        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_DB = new Intent(Howtocreatelist.this, CreateListFromDB.class);
                startActivity(i_DB);
                //Toast.makeText(Howtocreatelist.this, "from data base", Toast.LENGTH_LONG).show();
            }
        });



    }
}

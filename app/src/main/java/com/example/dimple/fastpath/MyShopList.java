package com.example.dimple.fastpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyShopList extends AppCompatActivity {

////////

  //  dmlist




    //////
    private ListView lv;
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnselect, btndeselect, btnnext;
    private ArrayList<String> itemslist = new ArrayList<String>();
    public int length;
    private Button bill;
    private String listName;
    private ArrayList<String> remainingItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholeshop_listitem);
        bill = findViewById(R.id.bill);
        lv = (ListView) findViewById(R.id.lv);
        btnselect = (Button) findViewById(R.id.selectall);
        btndeselect = (Button) findViewById(R.id.deselectall);
        Intent i = this.getIntent();
        itemslist.clear();
        itemslist = i.getStringArrayListExtra("dmlist");
        length = itemslist.size();
        listName = i.getStringExtra("listName");
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserList remainingList = new UserList(listName);
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                remainingList.setUserID(userID);
                for(int i = 0; i < CustomAdapter.modelArrayList.size(); i++){
                    if(!CustomAdapter.modelArrayList.get(i).getSelected()){
                        String item = CustomAdapter.modelArrayList.get(i).getitems();
                        ListItem item1 = new ListItem(item);
                        remainingList.getListItems().add(item1);
                    }
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("remainingList").push();
                myRef.setValue(remainingList);


                for (int i = 0; i < CustomAdapter.modelArrayList.size(); i++){
                    if(CustomAdapter.modelArrayList.get(i).getSelected()) {
                        Intent i1 = new Intent(MyShopList.this, MainActivity.class);
                        startActivity(i1);

                       // tv.setText(tv.getText() + " " + CustomAdapter.modelArrayList.get(i).getitems());
                    }
                }
            }
        });

        modelArrayList = getModel(false);
        customAdapter = new CustomAdapter(this,modelArrayList);
        lv.setAdapter(customAdapter);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(true);
                customAdapter = new CustomAdapter(MyShopList.this,modelArrayList);
                lv.setAdapter(customAdapter);
            }
        });
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                customAdapter = new CustomAdapter(MyShopList.this,modelArrayList);
                lv.setAdapter(customAdapter);
            }
        });

    }
    private ArrayList<Model> getModel(boolean isSelect){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < length; i++){

            Model model = new Model();
            model.setSelected(isSelect);
            model.setitems(itemslist.get(i));
            list.add(model);
        }
        return list;
    }
}

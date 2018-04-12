package com.benit.kibabu.firebasedbexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity {

    private String TAG = "MenuActivity", MSG = "";
    private FirebaseAuth auth;

    DatabaseReference menuTable;
    ValueEventListener eventListener;

    String restaurantID = "";
    List<Menu> menuList = new ArrayList<>();
    List<String> menuNames = new ArrayList<>();

    ListView listView;
    ArrayAdapter<String> adapter;

    Button newBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(getIntent().hasExtra("RESID")) {
            restaurantID = getIntent().getExtras().getString("RESID");
        }else{
            finish();
            startActivity(new Intent(this, RestaurantsActivity.class));
        }

        TextView nameTV = findViewById(R.id.rName);
        nameTV.setText(restaurantID);

        newBtn = findViewById(R.id.newBtn);
        listView = findViewById(R.id.listView);

        menuNames = new ArrayList<>();
        menuNames.add("No Menu Found!");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuNames);
        listView.setAdapter(adapter);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MenuActivity.this, RestaurantsActivity.class));
            }
        });

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                Context context = MenuActivity.this;
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_menu, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText nameField = promptsView
                        .findViewById(R.id.nameField);
                final EditText priceField = promptsView
                        .findViewById(R.id.priceField);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if(TextUtils.isEmpty(nameField.getText().toString())){
                                            nameField.setError("Please fill this field");
                                        }
                                        else if(TextUtils.isEmpty(priceField.getText().toString())){
                                            priceField.setError("Please fill this field");
                                        }
                                        else {
                                            addNewMenu(nameField.getText().toString(), priceField.getText().toString());
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    //Add new menu which relate to the current Restaurant

    private void addNewMenu(String name, String p){
        double price = Double.parseDouble(p);
        showProgressDialog("Loading all Restaurant");
        boolean menuExist = false;
        if(menuTable == null){
            menuTable = FirebaseDatabase.getInstance().getReference().child("menus");
        }

        loadMenuList();

        for(Menu m: menuList){
            if(m!= null ){
                if(m.getRName().equals(name)) {
                    menuExist = true;
                    break;
                }
            }
        }
        if(!menuExist) {
            Menu m = new Menu(name, restaurantID, price);
            menuTable.push().setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loadMenuList();
                    } else {
                        Toast.makeText(MenuActivity.this
                                , "Could not add new menu!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(MenuActivity.this, "Menu exist!", Toast.LENGTH_LONG).show();
        }
    }

    //Load all the menus from the which related to the Restaurant we picked
    private void loadMenuList(){
        showProgressDialog("Loading all Menu");
        if(menuTable == null){
            menuTable = FirebaseDatabase.getInstance().getReference().child("menus");
        }
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuNames = new ArrayList<>();
                menuList = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Menu r = child.getValue(Menu.class);
                    if(r != null) {
                        if( r.getRName().equals(restaurantID)) {
                            menuList.add(r);
                            menuNames.add(r.getName() + ",  € " + r.getPrice());
                        }
                    }
                }
                if(menuNames.size()> 0){
                    adapter.clear();
                    adapter.addAll(menuNames);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MSG = "Failed to read value.";
                Log.w(TAG, MSG, databaseError.toException());
            }
        };
        menuTable.addValueEventListener(listener);
        eventListener = listener;

        hideProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMenuList();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(eventListener != null){
            menuTable.removeEventListener(eventListener);
        }
    }
}

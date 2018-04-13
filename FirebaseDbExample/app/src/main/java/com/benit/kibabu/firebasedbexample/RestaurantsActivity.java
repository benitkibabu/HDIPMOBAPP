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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends BaseActivity {

    private String TAG = "RestaurantsActivity", MSG = "";
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    DatabaseReference restaurantTable;
    ValueEventListener eventListener;
    List<Restaurant> restaurants;
    List<String> rNames;

    ListView listView;
    ArrayAdapter<String> adapter;

    Button newBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if(currentUser == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        rNames = new ArrayList<>();
        rNames.add("No Restaurant found");

        newBtn = findViewById(R.id.newBtn);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(restaurants.size()> 0) {
                    Restaurant r = restaurants.get(position);
                    Intent i = new Intent(RestaurantsActivity.this, MenuActivity.class);
                    i.putExtra("RESID",r.getName());
                    finish();
                    startActivity(i);

                }
            }
        });

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                Context context = RestaurantsActivity.this;
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_restaurant, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.rName);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if(TextUtils.isEmpty(userInput.getText().toString())){
                                            userInput.setError("Please fill this field");
                                        }else {
                                            createRestaurant(userInput.getText().toString());
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
    
    // Create a new restaurant item in the database if it not already added
    public void createRestaurant(final String name){
        boolean restaurantExist = false;
        showProgressDialog("Adding New Restaurant");
        if(restaurantTable == null){
            restaurantTable = FirebaseDatabase.getInstance().getReference().child("restaurants");
        }
        loadRestaurantsList();
        for(Restaurant r : restaurants){
            if(r != null) {
                if(r.getName().equals(name)){
                    restaurantExist = true;
                    break;
                }
            }
        }
        if(!restaurantExist){
            Restaurant re = new Restaurant(name, currentUser.getUid());
            restaurantTable.push().setValue(re).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        loadRestaurantsList();
                    }else{
                        Toast.makeText(RestaurantsActivity.this
                                , "Could not add new restaurant", Toast.LENGTH_LONG).show();
                    }
                    hideProgressDialog();
                }
            });
        }else{
            Toast.makeText(RestaurantsActivity.this, "Restaurant exist!", Toast.LENGTH_LONG).show();
        }
    }
    
    //Load all of the available restaurant from the database and add them to an arraylist
    private void loadRestaurantsList(){
        showProgressDialog("Loading all Restaurant");
        if(restaurantTable == null){
            restaurantTable = FirebaseDatabase.getInstance().getReference().child("restaurants");
        }
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rNames = new ArrayList<>();
                restaurants = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Restaurant r = child.getValue(Restaurant.class);
                    if(r != null) {
                        restaurants.add(r);
                        rNames.add(r.getName());
                    }
                }
                if(rNames.size()> 0){
                    adapter.clear();
                    adapter.addAll(rNames);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                MSG = "Failed to read value.";
                Log.w(TAG, MSG, databaseError.toException());
            }
        };
        restaurantTable.addValueEventListener(listener);
        eventListener = listener;

        hideProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRestaurantsList();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(eventListener != null){
            restaurantTable.removeEventListener(eventListener);
        }
    }
}

package com.example.scr3.customlistview;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    CustomAdapter adapter;
    List<CustomItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();

        CustomItem item = new CustomItem(R.drawable.rock, "Rock");
        CustomItem item1 = new CustomItem(R.drawable.sea, "Sea");
        CustomItem item2 = new CustomItem(R.drawable.sky, "Sky");
        items.add(item);
        items.add(item1);
        items.add(item2);


        adapter = new CustomAdapter(this, R.layout.item_layout, items);

        recyclerView.setAdapter(adapter);


        adapter.setOnClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Snackbar.make(v, "Item @ position " + position  + " was clicked",
                        Snackbar.LENGTH_LONG);
            }
        });
    }
}

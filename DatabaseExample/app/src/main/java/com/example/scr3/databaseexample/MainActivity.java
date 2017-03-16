package com.example.scr3.databaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView result;
    Button save, get;
    EditText nameTx;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
        save = (Button) findViewById(R.id.save);
        get = (Button) findViewById(R.id.get);
        nameTx = (EditText) findViewById(R.id.name);

         db = new DbHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTx.getText().toString();
                String query = "insert into user values("+name+");";

                db.insert(query);
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = db.getName();
                result.setText("Name in our Database: " + name);
            }
        });
    }

    public void DOSomething(){

    }
}

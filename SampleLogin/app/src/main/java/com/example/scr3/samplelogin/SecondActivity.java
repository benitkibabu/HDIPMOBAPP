package com.example.scr3.samplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scr3.samplelogin.helper.AppPreferenceManager;
import com.example.scr3.samplelogin.helper.DbHelper;

public class SecondActivity extends AppCompatActivity {

    AppPreferenceManager pref;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        db = new DbHelper(this);
        pref = new AppPreferenceManager(this);

        Button logout = (Button) findViewById(R.id.logoutBtn);
        Button goback = (Button) findViewById(R.id.goback);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setKeyIsLogin("isLogin", false);
                //db.removeUser();
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

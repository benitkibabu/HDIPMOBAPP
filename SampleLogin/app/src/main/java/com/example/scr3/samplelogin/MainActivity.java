package com.example.scr3.samplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scr3.samplelogin.helper.AppPreferenceManager;
import com.example.scr3.samplelogin.helper.DbHelper;

public class MainActivity extends AppCompatActivity {
    AppPreferenceManager pref;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);
        pref = new AppPreferenceManager(this);

        TextView nameT = (TextView) findViewById(R.id.nameF);
        Button logout = (Button) findViewById(R.id.logoutBtn);
        Button second = (Button) findViewById(R.id.second);

        String name = pref.getUser();

        nameT.setText(name);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setKeyIsLogin("isLogin", false);
                //db.removeUser();
            }
        });


        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(!pref.getIsLogin()){
            Intent i = new Intent(this,
                    LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}

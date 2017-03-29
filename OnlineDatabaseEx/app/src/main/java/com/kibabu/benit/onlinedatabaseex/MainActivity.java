package com.kibabu.benit.onlinedatabaseex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kibabu.benit.onlinedatabaseex.helper.AppPreference;
import com.kibabu.benit.onlinedatabaseex.helper.DbHelper;
import com.kibabu.benit.onlinedatabaseex.model.User;

public class MainActivity extends AppCompatActivity {

    Button logout;
    EditText name,username,  email;

    AppPreference pref;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = (Button) findViewById(R.id.logout_button);

        name =(EditText) findViewById(R.id.name);
        username =(EditText) findViewById(R.id.username);
        email =(EditText) findViewById(R.id.email);

        db = new DbHelper(this);
        pref = new AppPreference(this);

        if(pref.getIsLogin() == false){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        User u = db.getUser();

        if(u != null){
            name.setText(u.getName());
            username.setText(u.getUsername());
            email.setText(u.getEmail());
        }else{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeUser();
                pref.setKeyIsLogin("isLogin", false);

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}

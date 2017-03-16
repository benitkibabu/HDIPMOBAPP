package com.example.scr3.samplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scr3.samplelogin.app.User;
import com.example.scr3.samplelogin.helper.AppPreferenceManager;
import com.example.scr3.samplelogin.helper.DbHelper;

public class LoginActivity extends AppCompatActivity {

    AppPreferenceManager pref;
    DbHelper db;

    EditText eTF, pTF;
    Button logBtn, regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHelper(LoginActivity.this);
        pref = new AppPreferenceManager(this);

        eTF = (EditText) findViewById(R.id.emailF);
        pTF = (EditText) findViewById(R.id.passF);

        logBtn = (Button) findViewById(R.id.logbtn);
        regBtn = (Button) findViewById(R.id.regbtn);

        if(pref.getIsLogin()){
            Intent i = new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(i);
            finish();
        }

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eTF.getText().toString();
                String pass = pTF.getText().toString();

                User _u = db.getUser(email, pass);
                if(_u != null){
                    pref.setKeyIsLogin("isLogin", true);
                    pref.setUserName(_u.getUserName());
                    pref.setUserPass(_u.getPassword());

                    Intent i = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

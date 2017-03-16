package com.example.scr3.samplelogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scr3.samplelogin.app.User;
import com.example.scr3.samplelogin.helper.AppPreferenceManager;
import com.example.scr3.samplelogin.helper.DbHelper;

public class RegisterActivity extends AppCompatActivity {

    AppPreferenceManager pref;
    DbHelper db;

    EditText nTF, eTF, pTF;
    Button logBtn, regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DbHelper(RegisterActivity.this);
        pref = new AppPreferenceManager(this);

        nTF = (EditText) findViewById(R.id.nameF);
        eTF = (EditText) findViewById(R.id.emailF);
        pTF = (EditText) findViewById(R.id.passF);

        logBtn = (Button) findViewById(R.id.logbtn);
        regBtn = (Button) findViewById(R.id.regbtn);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nTF.getText().toString();
                String email = eTF.getText().toString();
                String pass = pTF.getText().toString();

                User u = new User();
                u.setUserName(name);
                u.setPassword(pass);
                u.setEmail(email);

                long id = db.setUser(u);
                if(id != 0){
                    User _u = db.getUser(email, pass);
                    if(_u != null){
                        pref.setKeyIsLogin("isLogin", true);
                        pref.setUserName(name);
                        pref.setUserPass(pass);

                        Intent i = new Intent(RegisterActivity.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,
                            "User not register", Toast.LENGTH_LONG).show();
                }
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}

package com.kibabu.benit.onlinedatabaseex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.kibabu.benit.onlinedatabaseex.model.User;

public class RegisterActivity extends AppCompatActivity {

    Button login, register;
    EditText name, username, email, password;


    //User Model
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}

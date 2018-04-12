package com.benit.kibabu.firebasedbexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {

    String TAG = "LoginActivity";
    EditText emailField, passwordField;
    Button signInBtn, signUpBtn;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this, RestaurantsActivity.class));
        }

        emailField =  findViewById(R.id.emailField);
        passwordField =  findViewById(R.id.passwordField);

        signInBtn =  findViewById(R.id.signInBtn);
        signUpBtn =  findViewById(R.id.signUpBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog();
                String email =  emailField.getText().toString();
                String pass =  passwordField.getText().toString();
                if(TextUtils.isEmpty((email))){
                    emailField.setError("Enter correct email");
                }else if(TextUtils.isEmpty((pass))) {
                    emailField.setError("Enter correct password");
                }else {
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        finish();
                                        startActivity(new Intent(LoginActivity.this, RestaurantsActivity.class));
                                        Log.d(TAG, "signInWithEmail:success");

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    hideProgressDialog();

                                    // ...
                                }
                            });
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = auth.getCurrentUser();
        if(currentUser != null){
            finish();
            startActivity(new Intent(LoginActivity.this, RestaurantsActivity.class));
        }
    }
}

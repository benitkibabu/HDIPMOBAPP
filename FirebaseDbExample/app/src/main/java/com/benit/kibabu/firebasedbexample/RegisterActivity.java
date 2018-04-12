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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends BaseActivity {

    EditText emailField, passwordField, nameField;
    Button signInBtn, signUpBtn;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    DatabaseReference userTable;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        userTable = FirebaseDatabase.getInstance().getReference().child("users");

        nameField =  findViewById(R.id.nameField);
        emailField =  findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        signInBtn =  findViewById(R.id.signInBtn);
        signUpBtn =  findViewById(R.id.signUpBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();

                final String name =  nameField.getText().toString();
                String email =  emailField.getText().toString();
                String pass =  passwordField.getText().toString();
                if(TextUtils.isEmpty((email))){
                    emailField.setError("Enter correct email");
                }else if(TextUtils.isEmpty((pass))) {
                    emailField.setError("Enter correct password");
                }
                else if(TextUtils.isEmpty((name))){
                    nameField.setError("Name must be set!");
                }
                else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        currentUser  = auth.getCurrentUser();
                                        addUserDetail(name, currentUser);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Register did not work", Toast.LENGTH_LONG).show();
                                    }

                                    hideProgressDialog();
                                }
                            });

                }
            }
        });
    }

    //Dealing with Database push and read data
    boolean exist = false;
    void addUserDetail(String name, FirebaseUser u){
        exist = false;
        final User user = new User(u.getUid(), name);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    User user = child.getValue(User.class);
                    if(user.getUID().equals(currentUser.getUid())){
                        exist = true;
                        break;
                    }
                }
                if(exist){
                    Toast.makeText(RegisterActivity.this, "User Already exist", Toast.LENGTH_LONG).show();
                }else{
                    userTable.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                              finish();
                              startActivity(new Intent(RegisterActivity.this, RestaurantsActivity.class));
                          }else{
                              Toast.makeText(RegisterActivity.this, "Could not add to database", Toast.LENGTH_LONG).show();
                          }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("RegisterActivity", "Failed to read value.", databaseError.toException());
            }
        };
        userTable.addValueEventListener(listener);
        eventListener = listener;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(eventListener != null){
            userTable.removeEventListener(eventListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = auth.getCurrentUser();
    }
}

package com.example.benitkibabu.firebaseexample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.benitkibabu.firebaseexample.R;
import com.example.benitkibabu.firebaseexample.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Benit Kibabu on 13/06/2017.
 */

public class CreatePostDialog extends DialogFragment {

    private DatabaseReference postReference;

    private EditText postText;
    private Button createBtn;
    private String uid, username;

    public CreatePostDialog() {
    }

    public static CreatePostDialog newInstance(String uid, String username){
        CreatePostDialog dialog = new CreatePostDialog();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("username", username);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postReference = FirebaseDatabase.getInstance().getReference().child("posts");
    }

    private void newPost(Post post){
        postReference.push().setValue(post).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getDialog().dismiss();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_post, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postText = (EditText) view.findViewById(R.id.postEditText);
        createBtn = (Button) view.findViewById(R.id.addPostBtn);

        if(this.getArguments() != null) {
            uid = getArguments().getString("uid");
            username = getArguments().getString("username");
        }else{
            getDialog().dismiss();
        }

        postText.requestFocus();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(uid, username, postText.getText().toString());
                newPost(post);
            }
        });
    }
}

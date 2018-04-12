package com.example.benitkibabu.firebaseexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benitkibabu.firebaseexample.R;
import com.example.benitkibabu.firebaseexample.adapters.PostListViewAdapter;
import com.example.benitkibabu.firebaseexample.fragments.CreatePostDialog;
import com.example.benitkibabu.firebaseexample.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPostActivity extends BaseActivity {

    private ListView listView;
    private List<Post> postList = new ArrayList<>();
    private PostListViewAdapter adapter;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private DatabaseReference postReference;
    private ValueEventListener eventListener;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        setTitle("Posts");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(ViewPostActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        postReference = FirebaseDatabase.getInstance().getReference().child("posts");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {

        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        listView = (ListView) findViewById(R.id.postListView);
        adapter = new PostListViewAdapter(this, R.layout.post_item, postList);
        listView.setAdapter(adapter);
    }

    private void ShowDialog(){
        String uid = user.getUid();
        String name = "";
        if(user.getDisplayName() == null)
            name = "unknown";
        else
            name = user.getDisplayName();

        FragmentManager fm = getSupportFragmentManager();
        CreatePostDialog editNameDialogFragment = CreatePostDialog.newInstance(uid, name);
        editNameDialogFragment.show(fm, "fragment_create_post");
    }

    private void showMyPost(){
        List<Post> myPosts = new ArrayList<>();
        for(Post p : postList){
            if(p.getUid().equals(getUid())){
                myPosts.add(p);
            }
        }

        adapter.updateList(myPosts);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        else if(id == R.id.action_new){
            ShowDialog();
            return true;
        }
        else if(id == R.id.action_mypost){
            showMyPost();
            return true;
        }
        else if(id == R.id.action_refresh){
            adapter.updateList(postList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);

        ValueEventListener postEvents = new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Log.d("child", child.getValue(Post.class).toMap().toString());

                    Post p = child.getValue(Post.class);
                    Log.d("P", p.getPost());
                    postList.add(p);
                }
                adapter.updateList(postList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewPostActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        postReference.addValueEventListener(postEvents);

        eventListener = postEvents;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post p = dataSnapshot.getValue(Post.class);
                String title = p.getUsername() + " has added a new post";
                String content = p.getPost();
                //NewMessageNotification.notify(ViewPostActivity.this,p.getUsername(), title,content , 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postReference.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
        if(eventListener != null){
            postReference.removeEventListener(eventListener);
        }
        if(mChildEventListener != null){
            postReference.removeEventListener(mChildEventListener);
        }
    }
}

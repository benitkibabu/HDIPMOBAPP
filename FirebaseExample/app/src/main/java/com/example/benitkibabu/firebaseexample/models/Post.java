package com.example.benitkibabu.firebaseexample.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Benit Kibabu on 13/06/2017.
 */

@IgnoreExtraProperties
public class Post {
    String uid;
    String username;
    String post;

    public Post() {
    }

    public Post(String uid, String username, String post) {
        this.uid = uid;
        this.username = username;
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public String getPost() {
        return post;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("post", post);
        return result;
    }
}

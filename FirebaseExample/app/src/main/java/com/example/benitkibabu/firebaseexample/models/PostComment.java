package com.example.benitkibabu.firebaseexample.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Benit Kibabu on 13/06/2017.
 */
@IgnoreExtraProperties
public class PostComment {
    String postId;
    String uid;
    String comment;

    public PostComment() {
    }

    public PostComment(String postId, String uid, String comment) {
        this.postId = postId;
        this.uid = uid;
        this.comment = comment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        return result;
    }
}

package com.example.scr3.samplelogin.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by scr3 on 15/03/2017.
 */

public class AppPreferenceManager {

    Context context;
    SharedPreferences pref;
    Editor editor;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SAMPLELOGIN";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_USER = "username";
    private static final String KEY_USER_PASS = "password";

    public AppPreferenceManager(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setKeyIsLogin(String tag, boolean value){
        editor = pref.edit();
        editor.putBoolean(tag, value);
        editor.apply();
    }

    public void setUserName(String val){
        editor = pref.edit();
        editor.putString(KEY_USER, val);
        editor.apply();
    }
    public void setUserPass(String val){
        editor = pref.edit();
        editor.putString(KEY_USER_PASS, val);
        editor.apply();
    }

    public String getUser() {

        return pref.getString(KEY_USER, "null");
    }

    public String getUserPass() {
        return pref.getString(KEY_USER_PASS, "null");
    }

    public boolean getIsLogin(){
        boolean val = pref.getBoolean(KEY_IS_LOGIN, false);
        return val;
    }

    public boolean getIsLogin(String tag){
        boolean val = pref.getBoolean(tag, false);
        return val;
    }
}

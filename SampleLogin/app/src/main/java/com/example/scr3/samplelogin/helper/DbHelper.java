package com.example.scr3.samplelogin.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.scr3.samplelogin.app.User;

/**
 * Created by scr3 on 15/03/2017.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "sampleDB";
    private static final int DB_VERSION = 1;

    private final String TB_NAME = "user";
    private final String TB_ID = "id";
    private final String TB_UNAME = "username";
    private final String TB_UEMAIL = "email";
    private final String TB_UPASSWORD = "password";

    private final String CREATE_U_TB = "CREATE TABLE "+ TB_NAME
            + "("+ TB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TB_UNAME + " TEXT, "
            +TB_UEMAIL + " TEXT, "
            +TB_UPASSWORD + " TEXT)";


    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_U_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    public long setUser(User u){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_UNAME, u.getUserName());
        values.put(TB_UEMAIL, u.getEmail());
        values.put(TB_UPASSWORD, u.getPassword());
        long id = db.insert(TB_NAME, null, values);
        db.close();

        return id;
    }

    public User getUser(String email, String password){
        User u = null;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TB_NAME
                + " WHERE " + TB_UEMAIL + "='" + email + "' AND "
                + TB_UPASSWORD + "="+ password;

        Cursor c = db.rawQuery(query, null);

        if(c!= null && c.getCount()>0 && c.moveToFirst()){
            u = new User();
            u.setId(c.getInt(c.getColumnIndex(TB_ID)));
            u.setUserName(c.getString(c.getColumnIndex(TB_UNAME)));
            u.setEmail(c.getString(c.getColumnIndex(TB_UEMAIL)));
            u.setPassword(c.getString(c.getColumnIndex(TB_UPASSWORD)));

            db.close();
        }else{
            u = null;
        }

        db.close();
        return u;
    }

    public int removeUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = db.delete(TB_NAME,null, null);
        db.close();
        return id;
    }

}

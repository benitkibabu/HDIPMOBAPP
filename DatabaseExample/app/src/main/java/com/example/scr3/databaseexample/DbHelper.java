package com.example.scr3.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scr3 on 22/02/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "ncigodb";
    private static final int DB_VERSION = 1;

    private String TBName = "user";

    private String TBID = "id";
    private String USNAME = "user_name";

    private String createUserTB = "CREATE TABLE "+ TBName
            + "("+TBID + " INTEGER primary key AUTOINCREMENT, "
            + USNAME + " TEXT)";

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBName);
        onCreate(db);
    }

    //Insert into dabatase
    public void insert(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(USNAME, query);
        long id = db.insert(TBName, null, v);
        db.close();
    }

    //retrieve first element in database
    public String getName(){
        String name = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT "+ USNAME +" FROM "+ TBName , null);

        if(c != null){
            if(c.getCount() > 0){
                c.moveToFirst();
                name = c.getString(c.getColumnIndex(USNAME));
                db.close();
            }
        }

        return name;
    }
}

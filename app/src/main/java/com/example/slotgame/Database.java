package com.example.slotgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Login Database";// Database Name

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users (username TEXT primary key, password TEXT, score TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");

    }

    //Query for add data to the database
    public Boolean addData(String username, String password){         //to add phrases
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = database.insert("users",null,values);
        return result != -1;
    }

    //check user name is correct
    public Boolean checkUsername(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from users where username = ?", new String[] {username});

        return cursor.getCount() > 0;
    }

    //check username and password correct
    public Boolean CheckUsernamePassword(String username, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Query for check score with username
    public Cursor checkScore(String name, String score){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("UPDATE users SET score = '"+ score + "' WHERE username = '"+ name + "'");

        return null;
    }

    //Query for add score to the database
    public Cursor addScore(String score){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("UPDATE users SET score = '"+score +"'WHERE username = ?");

        return null;
    }

  /*  //Query for get score from database
    public Boolean getScore(String score){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE score = '"+score+"'", new String[] {score});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }*/
}

package com.example.app_fittingroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_fittingroom.javabean.User;

import java.sql.SQLClientInfoException;

public class MYsqliteopenhelper extends SQLiteOpenHelper {
    private  static  final String DB_Name="MYsqlite.db";
    private  static  final String create_user="create table users(name varchar(32),password varchar(32))";
    public MYsqliteopenhelper(@Nullable Context context) {
        super(context, DB_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long register(User u){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put("name",u.getName());
        cv.put("password",u.getPassword());
        long users=db.insert("users",null,cv);
        return users;
    }
    public Boolean login(String name,String password){
        SQLiteDatabase db1= getWritableDatabase();
        boolean result =false;
        Cursor users=db1.query("users",null,"name like ?",new String[]{name},null,null,null);
        if (users !=null){
            while(users.moveToNext()){
                String password1=users.getString(1);
                result=password1.equals(password);
                return result;
            }
        }
        return false;
    }
}
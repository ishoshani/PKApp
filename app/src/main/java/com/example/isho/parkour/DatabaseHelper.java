package com.example.isho.parkour;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.sql.SQLClientInfoException;

/**
 * Created by isho on 3/1/16.
 */
class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME= "pkDB";
    private static final String USERDB = "CREATE TABLE IF NOT EXISTS  USER (ID Int, Name varchar(20),PicID varchar(20))";
    private static final String SPOTDB = "CREATE TABLE IF NOT EXISTS  Spots (spotID Int,Name varchar(20), stars int, latitude double, longitude double);";

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERDB);
        db.execSQL(SPOTDB);
    }
    public void onUpgrade(SQLiteDatabase db, int v, int w){

    }
    public void addUser(String name, String PicID){
        Cursor idhelper=getReadableDatabase().rawQuery("Select Count(*) From User", null);
        idhelper.moveToNext();
        int maxid=idhelper.getInt(0);

        String insertUser="INSERT into USER VALUES ("+maxid+", '"+name+"', '"+ PicID+maxid+"');";
        Log.i("myLogs",insertUser);
        getWritableDatabase().execSQL(insertUser);
    }
    public boolean checkUser(){
        Cursor q=getReadableDatabase().rawQuery("Select Count(*) From User", null);
        q.moveToNext();
        Log.i("myLogs", "got cursor" + q.toString());
        int users=q.getInt(0);
        if(users>0){
            return true;
        }
        return false;
    }
    public String getUserName(int id){
        Cursor q=getReadableDatabase().rawQuery("Select Name From User Where id=" + id + ";", null);
        q.moveToNext();
        Log.i("myLogs","got cursor"+q.toString());
        Log.i("myLogs","got info "+q.getString(0));
        Log.i("myLogs", "at positoin " + q.getPosition());
        String name = q.getString(0);
        return name;
    }
    public Bitmap getProfilePic(String Filename, Context context){
        Bitmap image;
        image= BitmapFactory.decodeFile(Filename);
        return image;

    }

}

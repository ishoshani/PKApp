package com.example.isho.parkour;
import android.graphics.Bitmap;
/**
 * Created by isho on 2/15/16.
 */
public class User {
    String name;
    Bitmap profilepic;
    public User(String username, Bitmap profilepicture){
        name=username;
        profilepic=profilepicture;
    }
    public String getName(){
        return name;
    }
    public Bitmap getProfilepic(){
        return profilepic;
    }

}

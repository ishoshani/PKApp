package com.example.isho.parkour;

import java.io.File;
import android.graphics.Bitmap;
/**
 * Created by isho on 2/15/16.
 */
public class Feature {
    String name;
    Bitmap picture;
    public Feature(String n,Bitmap p){
        name = n;
        picture = p;
    }

    public String getName() {
        return name;
    }
    public Bitmap getPicture() {
        return picture;
    }
}

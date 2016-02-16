package com.example.isho.parkour;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
/**
 * Created by isho on 2/15/16.
 */
public class PKspot {
    LatLng coords;
    String Name;
    int stars;
    ArrayList users;
    ArrayList features;
    ArrayList comments;
    public PKspot(String n, double lat, double lng){
        Name=n;
        coords=new LatLng(lat,lng);
        stars=0;
        users= new ArrayList<User>();
        features= new ArrayList<Feature>();
        comments= new ArrayList<Comment>();
    }

    public String getName() {
        return Name;
    }

    public LatLng getCoords() {
        return coords;
    }

    public int addstar(){
        stars++;
        return stars;
    }
    public void addUser(User U){
        users.add(U);
    }
    public void addFeature(Feature f){
        features.add(f);
    }
    public void addComment(Comment c){
        comments.add(c);
    }

}

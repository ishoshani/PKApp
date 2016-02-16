package com.example.isho.parkour;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.Manifest.permission;
import android.widget.Button;
import android.location.Location;
import android.graphics.Bitmap;
import android.widget.SearchView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public ArrayList<PKspot> pKspots;
    public SearchView search;
    public Button TagButton;
    public Button AddButton;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        pKspots= new ArrayList<PKspot>();
        PKspot testSpot1=new PKspot("Keller Park",45.512918, -122.679250);
        pKspots.add(testSpot1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (SearchView)findViewById(R.id.mapSearch);
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("myLogs", search.getQuery().toString());
                if (search.getQuery().toString() == "") {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if(getApplicationContext().checkCallingPermission(permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                        Location location = lm.getLastKnownLocation(lm.GPS_PROVIDER);
                        LatLng myLL = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLL));
                        mMap.setMyLocationEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(4));
                    }
                }


            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double density=getApplicationContext().getResources().getDisplayMetrics().density;
        int topdp=50;
        int botdp=90;
        mMap.setPadding(0,(int)(topdp*density),0,(int)(botdp*density));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("myLogs", "InfoWindowClickRegistered!");
                PKspot chosen = null;
                for (PKspot spot : pKspots) {
                    Log.i("myLogs", marker.getTitle() + " checking against spot " + spot.getName());
                    if (marker.getTitle().compareTo(spot.getName()) == 0) {
                        Log.i("myLogs", "found matching spot");
                        chosen = spot;
                        break;
                    }
                }
                Intent goToDetails = new Intent(getApplicationContext(), DetailActivity.class);
                Log.i("myLogs", "created Intent");
                Log.i("myLogs", "Spot is " + chosen.getName());
                goToDetails.putExtra("title", chosen.getName());
                goToDetails.putExtra("stars", chosen.stars);
                goToDetails.putExtra("lat", chosen.coords.latitude);
                goToDetails.putExtra("long", chosen.coords.longitude);
                Log.i("myLogs", "Attempting new Activity");
                startActivity(goToDetails);
            }

        });

        for(PKspot pk : pKspots) {
            LatLng currentSpot = pk.getCoords();
            String name = pk.getName();
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_beenhere_black_18dp))
                    .position(currentSpot)
                    .title(name));
        }
        if (getApplicationContext().checkCallingPermission(permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(lm.GPS_PROVIDER);
            if (location!=null) {
                LatLng myLL = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLL));
            }
            else{
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.512918, -122.679250),(float)15.3));
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.isho.parkour/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMap Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.isho.parkour/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}

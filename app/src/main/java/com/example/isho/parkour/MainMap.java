package com.example.isho.parkour;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.LocationManager;
import android.net.Uri;
import android.nfc.Tag;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.Manifest.permission;
import android.widget.Button;
import android.location.Location;
import android.graphics.Bitmap;
import android.widget.EditText;
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
import java.util.LinkedList;
import java.util.jar.Manifest;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {




    private GoogleMap mMap;
    View CoordinatorLayoutView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public ArrayList<PKspot> pKspots;
    public SearchView search;
    public Button TagButton;
    public Button AddButton;
    public Marker Tagged;
    public LinkedList <Marker>newMarker;
    private GoogleApiClient client;
    private Context context;
    private String m_Text;
    private DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db=new DatabaseHelper(this);
        context=this;
        setContentView(R.layout.activity_main_map);
        newMarker=new LinkedList<Marker>();
        pKspots= new ArrayList<PKspot>();
        PKspot testSpot1=new PKspot("Keller Park",45.512918, -122.679250);
        pKspots.add(testSpot1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        CoordinatorLayoutView=findViewById(R.id.snackbarPosition);

        search = (SearchView)findViewById(R.id.mapSearch);
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("myLogs", search.getQuery().toString());
                    }
        });
        TagButton = (Button)findViewById(R.id.TagButton);
        AddButton = (Button)findViewById(R.id.AddButton);
        TagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(Tagged==null) {
                   Snackbar.make(CoordinatorLayoutView, "You need to select a spot first", Snackbar.LENGTH_LONG)
                   .show();
               }else{
                       Snackbar.make(CoordinatorLayoutView,"Thank you for Sharing!",Snackbar.LENGTH_LONG)
                       .show();
                   }

            }

        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("myTags","Add Button has been pressed, current spot is at"+ToAdd.toString());
                if(newMarker.isEmpty()){
                    Snackbar.make(CoordinatorLayoutView, "You need to select a place on the map first", Snackbar.LENGTH_LONG)
                            .show();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Create A new Spot!");
                    final EditText input= new EditText(getApplicationContext());
                    input.setHint("Name your Spot!");
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setTextColor(ContextCompat.getColor(context, R.color.notifyColor));
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Marker m=newMarker.pop();
                            LatLng newCoords=m.getPosition();
                            m_Text = input.getText().toString();
                            String name = m_Text;
                            PKspot newSpot = new PKspot(name, newCoords.latitude, newCoords.longitude);
                            pKspots.add(newSpot);
                            mapRefresh();
                            Snackbar.make(CoordinatorLayoutView, "Thank you for adding this spot!", Snackbar.LENGTH_LONG)
                                    .show();
                            dialog.dismiss();

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            }
        });
        String username=db.getUserName(0);
        Snackbar.make(CoordinatorLayoutView, "Welcome " + username, Snackbar.LENGTH_LONG).show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
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

        Log.i("myLogs","Got Permissions! Attempting to enable my location");
        mMap.setMyLocationEnabled(true);
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
                chosen.stars = 2;
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Tagged = marker;
                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("myLogs", "registered a map click at " + latLng.toString());
                if (!newMarker.isEmpty()) {
                    Marker remove = (Marker) newMarker.pop();
                    remove.remove();
                }
                Marker m = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag_black_18dp))
                        .position(latLng)
                        .anchor((float) 0.3, 1)
                        .title("newSpot"));

                newMarker.push(m);
            }
        });
        mapRefresh();


        }
    public void mapRefresh() {
        mMap.clear();
        for (PKspot pk : pKspots) {
            LatLng currentSpot = pk.getCoords();
            String name = pk.getName();
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_beenhere_black_18dp))
                    .position(currentSpot)
                    .title(name));

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
        String username=db.getUserName(0);
        Snackbar.make(CoordinatorLayoutView,"Welcome "+username,Snackbar.LENGTH_LONG).show();
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

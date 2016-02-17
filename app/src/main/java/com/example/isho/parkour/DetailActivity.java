package com.example.isho.parkour;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageButton;
import android.Manifest.permission;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    String title;
    int stars;
    GoogleMap mMap;
    DetailFragment Details;
    LatLng place;
    TextView viewTitle;
    TextView starCount;
    ImageButton UserLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getIntent().getStringExtra("title");
        stars=getIntent().getIntExtra("stars", 0);
        place=new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("long",0.0));

        setContentView(R.layout.activity_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailmap);
        mapFragment.getMapAsync(this);

        Details=(DetailFragment)getSupportFragmentManager().findFragmentById(R.id.detail);
        Details.setSpot(title);
        viewTitle= (TextView)findViewById(R.id.PlaceTitle);
        starCount=(TextView)findViewById(R.id.StarsCount);
        UserLike=(ImageButton)findViewById(R.id.starButton);
        viewTitle.setText(title);
        String StarsString=""+stars;
        starCount.setText(StarsString);
        UserLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton button = (ImageButton)v;
                button.setImageTintList(getColorStateList(R.color.button_red2));
                stars=stars+1;
                String StarsString=""+stars;
                starCount.setText(StarsString);
                button.setClickable(false);
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap=googleMap;
        if(getApplicationContext().checkCallingPermission(permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.addMarker(new MarkerOptions()
                .position(place)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_beenhere_black_18dp))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,(float)19.35));
    }
}

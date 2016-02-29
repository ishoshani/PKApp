package com.example.isho.parkour;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
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

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;


public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, PageFragment.OnFragmentInteractionListener {
    String title;
    int stars;
    GoogleMap mMap;

    LatLng place;
    TextView viewTitle;
    TextView starCount;
    ImageButton UserLike;


    PageFragment.OnFragmentInteractionListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), DetailActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        title=getIntent().getStringExtra("title");
        stars=getIntent().getIntExtra("stars", 0);
        place=new LatLng(getIntent().getDoubleExtra("lat",0.0),getIntent().getDoubleExtra("long",0.0));


       /*
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailmap);
        mapFragment.getMapAsync(this);

        Details=(DetailFragment1)getSupportFragmentManager().findFragmentById(R.id.detail);
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
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

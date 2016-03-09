package com.example.isho.parkour;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageButton;
import android.Manifest.permission;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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
import android.widget.ViewSwitcher;

import java.net.URI;
import java.util.List;


public class DetailActivity extends FragmentActivity implements OnMapReadyCallback, PageFragment.OnFragmentInteractionListener {
    String title;
    int stars;
    GoogleMap mMap;

    LatLng place;
    TextView viewTitle;
    TextView starCount;
    ImageButton UserLike;
    ScrollView scroller;
    Button TagButton;
    Button FeatureButton;
    Button CommentButton;
    RelativeLayout tagWheel;
    RelativeLayout featureWheel;
    DatabaseHelper db;
    Button FinishButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        db=new DatabaseHelper(this);
        title = getIntent().getStringExtra("title");
        Log.i("Checking title", "" + title);
        stars = getIntent().getIntExtra("stars", 0);
        place = new LatLng(getIntent().getDoubleExtra("lat", 0.0), getIntent().getDoubleExtra("long", 0.0));
        scroller = (ScrollView) findViewById(R.id.detailScroller);
        viewTitle = (TextView) findViewById(R.id.PlaceTitle);
        starCount = (TextView) findViewById(R.id.StarsCount);
        UserLike = (ImageButton) findViewById(R.id.starButton);
        TagButton = (Button) findViewById(R.id.UserButton);

        FeatureButton = (Button) findViewById(R.id.featureButton);
        CommentButton = (Button) findViewById(R.id.commentButton);
        tagWheel = (RelativeLayout) findViewById(R.id.userswheel);
        featureWheel = (RelativeLayout) findViewById(R.id.featurewheel);
        TagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Bitmap img=db.getProfilePic("profile0.png",DetailActivity.this);
                ImageView tagger=new ImageView(DetailActivity.this);
                tagger.setImageBitmap(img);
                RelativeLayout.LayoutParams ll=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                tagger.setLayoutParams(ll);
                featureWheel.addView(tagger);
            }
        });
        viewTitle.setText(title);
        String StarsString = "" + stars;
        starCount.setText(StarsString);
        UserLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton button = (ImageButton) v;
                stars = stars + 1;
                String StarsString = "" + stars;
                starCount.setText(StarsString);
                button.setClickable(false);
            }
        });


        FinishButton = (Button) findViewById(R.id.btn_finish);
        FinishButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent end = new Intent(DetailActivity.this,FinishActivity.class);
                                                startActivity(end);
                                            }
                                        }

        );


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getApplicationContext().checkCallingPermission(permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.addMarker(new MarkerOptions()
                        .position(place)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_beenhere_black_18dp))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, (float) 19.35));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Detail Page", // TODO: Define a title for the content shown.
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
                "Detail Page", // TODO: Define a title for the content shown.
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

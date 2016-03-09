package com.example.isho.parkour;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    private GoogleApiClient client;
    FloatingActionButton bp;
    View CoordinatorLayoutView;

    Button b1;
    EditText text1;
    ImageView iv;
    Bitmap image;
    String userName;
    DatabaseHelper db;
    boolean image_taken=false;
    boolean name_input=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermissions();
        db=new DatabaseHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int MY_REQUEST_CODE=0;
        iv=(ImageView)findViewById(R.id.camerabox);
        bp=(FloatingActionButton)findViewById(R.id.camerabutton);
        b1=(Button)findViewById(R.id.Button1);
        text1=(EditText)findViewById(R.id.userbox);
        CoordinatorLayoutView=findViewById(R.id.LoginsnackbarPosition);

        bp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        text1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("mylogs", "action in editor");
                    if(image_taken);{
                        b1.setBackgroundColor(getColor(R.color.button_red));
                    }
                    name_input=true;
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(name_input&&image_taken) {
                    userName = text1.getText().toString();
                    Log.i("myLogs","username is "+userName);
                    db.addUser(userName,"profile");
                    Intent intent = new Intent(getApplicationContext(), MainMap.class);
                    startActivity(intent);
                }
                else if(name_input){
                    Snackbar.make(CoordinatorLayoutView, "You need to take a profile pic", Snackbar.LENGTH_LONG)
                            .show();
                }
                else{
                    Snackbar.make(CoordinatorLayoutView, "You need to pick a username", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
        image= bp;
        if(name_input){
            b1.setBackgroundColor(ContextCompat.getColor(this, R.color.button_red));
        }
        image_taken=true;
        try {
            FileOutputStream file = openFileOutput("Profile0.png", MODE_PRIVATE);
            bp.compress(Bitmap.CompressFormat.PNG, 100, file);
            file.close();
        }catch (FileNotFoundException e){
            Log.i("mylogs","could not write file");
        }catch(IOException e){
            Log.i("mylogs","could not close file");
        }catch (NullPointerException e){
            Snackbar.make(CoordinatorLayoutView,"No Image Taken!",Snackbar.LENGTH_LONG);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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
    public void checkPermissions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check Permissions");
        final TextView input= new TextView(this);
        input.setText("Please make sure you've given this app the needed permissions");
        input.setTextColor(ContextCompat.getColor(this, R.color.notifyColor));
        builder.setView(input);
        builder.setPositiveButton("To Permissions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Already Given", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}

package com.example.isho.parkour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.content.Intent;

public class SplashActivity extends AppCompatActivity {

    private static boolean splashLoaded = false;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new DatabaseHelper(getApplicationContext());

        if(!splashLoaded) {
            setContentView(R.layout.activity_splash);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!db.checkUser()) {//Check if a prime user exists
                        Intent mGoToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        mGoToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(mGoToMainActivity);
                        finish();
                    }
                    else {//if a prime user exists, skip login
                        Intent mSkipLogin = new Intent(SplashActivity.this, MainMap.class);
                        mSkipLogin.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(mSkipLogin);
                        finish();
                    }
                }
            }, secondsDelayed * 1000);

            splashLoaded = true;
        }
        else {
            if(!db.checkUser()) {//Check if a prime user exists
                Intent mGoToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                mGoToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mGoToMainActivity);
                finish();
            }
            else{//if a prime user exists, skip login
                Intent mSkipLogin = new Intent(SplashActivity.this, MainMap.class);
                mSkipLogin.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mSkipLogin);
                finish();
            }
            }


        }
    }


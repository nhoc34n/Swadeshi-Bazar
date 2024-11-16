package com.example.swadeshibazar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Make sure your splash layout is correct

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Delay for 3 seconds and then start the WelcomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // Close SplashActivity so user can't navigate back to it
            }
        }, SPLASH_TIMEOUT);
    }
}
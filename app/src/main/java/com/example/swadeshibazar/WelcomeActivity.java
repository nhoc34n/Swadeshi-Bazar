package com.example.swadeshibazar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Ensure this matches activity_welcome.xml

        // Initialize buttons
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignup = findViewById(R.id.buttonSignup);

        // Set onClick listeners for Login and Sign-Up buttons
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(activity_login.class);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(signup.class);
            }
        });
    }

    /**
     * Opens the given activity class with error handling.
     *
     * @param activityClass The activity class to open.
     */
    private void openActivity(Class<?> activityClass) {
        try {
            Intent intent = new Intent(WelcomeActivity.this, activityClass);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error opening activity: " + activityClass.getSimpleName(), e);
        }
    }
}

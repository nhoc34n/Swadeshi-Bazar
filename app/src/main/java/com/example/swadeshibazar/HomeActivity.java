package com.example.swadeshibazar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private Button buyButton, sellButton;
    private LinearLayout seedsButton, cropsButton, livestockButton, soilButton, vegetableButton, homemadeButton;
    private LinearLayout homeButton, buySellButton, getLoanButton, accountButton;

    private FirebaseAuth mAuth;

    // Constants for SharedPreferences
    public static final String PREFS_NAME = "UserPreferences";
    public static final String KEY_USER_TYPE = "KEY_USER_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if user is logged in
        if (currentUser == null) {
            // Redirect to LoginActivity if the user is not logged in
            Intent loginIntent = new Intent(HomeActivity.this, activity_login.class);
            startActivity(loginIntent);
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        // Retrieve user type from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userType = sharedPreferences.getString(KEY_USER_TYPE, "Consumer"); // Default to "Consumer" if not found
        Toast.makeText(this, "Logged in as " + userType, Toast.LENGTH_SHORT).show();

        // Initialize UI components
        buyButton = findViewById(R.id.buyButton);
        sellButton = findViewById(R.id.sellButton);
        seedsButton = findViewById(R.id.seedsButton);
        cropsButton = findViewById(R.id.cropsButton);
        livestockButton = findViewById(R.id.livestockButton);
        soilButton = findViewById(R.id.soilButton);
        vegetableButton = findViewById(R.id.vegetableButton);
        homemadeButton = findViewById(R.id.homemadeButton);

        // Initialize Bottom Navigation Buttons
        homeButton = findViewById(R.id.homeButton);
        buySellButton = findViewById(R.id.buySellButton);
        getLoanButton = findViewById(R.id.getLoanButton);
        accountButton = findViewById(R.id.accountButton);

        // Set up click listeners for top section buttons
        buyButton.setOnClickListener(v -> navigateToBuy());
        sellButton.setOnClickListener(v -> navigateToSell());

        // Set up click listeners for category buttons
        seedsButton.setOnClickListener(v -> showCategory("Seeds"));
        cropsButton.setOnClickListener(v -> showCategory("Crops"));
        livestockButton.setOnClickListener(v -> showCategory("Livestock"));
        soilButton.setOnClickListener(v -> showCategory("Soil"));
        vegetableButton.setOnClickListener(v -> showCategory("Vegetables"));
        homemadeButton.setOnClickListener(v -> showCategory("Homemade"));

        // Bottom navigation button listeners
        homeButton.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
        });

        buySellButton.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Buy/Sell section selected", Toast.LENGTH_SHORT).show();
        });

        getLoanButton.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Loan section selected", Toast.LENGTH_SHORT).show();
            // Add navigation logic to Loan section
        });

        accountButton.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Account section selected", Toast.LENGTH_SHORT).show();
            // Add navigation logic to Account section
        });
    }

    private void navigateToBuy() {
        Toast.makeText(HomeActivity.this, "Navigating to Buy Products", Toast.LENGTH_SHORT).show();
        // Add navigation logic to Buy Activity or Buy Product screen
    }

    private void navigateToSell() {
        Toast.makeText(HomeActivity.this, "Navigating to Sell Products", Toast.LENGTH_SHORT).show();
        // Add navigation logic to Sell Activity or Sell Product screen
    }

    private void showCategory(String category) {
        Toast.makeText(this, "Selected category: " + category, Toast.LENGTH_SHORT).show();
        // Add navigation logic for selected category
    }
}

package com.example.swadeshibazar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button buyButton, sellButton;
    private GridLayout sellGridLayout,buyGridLayout;
    private LinearLayout  topBar,homeButton, buySellButton, getLoanButton, accountButton;
    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_USER_TYPE = "KEY_USER_TYPE";

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve user type from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userType = sharedPreferences.getString(KEY_USER_TYPE, "Consumer");

        // Initialise UI components
        buyButton = findViewById(R.id.buyButton);
        sellButton = findViewById(R.id.sellButton);
        sellGridLayout = findViewById(R.id.sellGridLayout);
        buyGridLayout = findViewById(R.id.buyGridLayout);

        // Set initial visibility for the sell grid layout
        buyGridLayout.setVisibility(View.GONE);
        sellGridLayout.setVisibility(View.GONE);

        // Set click listeners for Buy and Sell buttons with style updates
        buyButton.setOnClickListener(v -> {
            buyGridLayout.setVisibility(View.VISIBLE); // Show Buy Grid
            sellGridLayout.setVisibility(View.GONE); // Hide Sell Grid
            updateButtonStyles(buyButton, sellButton); // Update styles
        });
        sellButton.setOnClickListener(v -> {
            if (userType.equals("Farmer")) {
                sellGridLayout.setVisibility(View.VISIBLE); // Show Sell Grid
                buyGridLayout.setVisibility(View.GONE); // Hide Buy Grid
                updateButtonStyles(sellButton, buyButton); // Update styles
                Toast.makeText(this, "Sell mode enabled", Toast.LENGTH_SHORT).show();
            } else {
                sellGridLayout.setVisibility(View.GONE); // Prevent showing Sell Grid
                Toast.makeText(this, "Only farmers can add items to sell", Toast.LENGTH_SHORT).show();
            }
        });


        // Set click listeners for each category in the sell grid
        findViewById(R.id.buyProductSeed).setOnClickListener(v -> showCategoryMessage("Buy Seeds"));
        findViewById(R.id.buyProductCrops).setOnClickListener(v -> showCategoryMessage("Buy Crops"));
        findViewById(R.id.buyProductLivestocks).setOnClickListener(v -> showCategoryMessage("Buy Livestocks"));
        findViewById(R.id.buyProductVegetables).setOnClickListener(v -> showCategoryMessage("Buy Vegetables"));
        findViewById(R.id.buyProductHomemade).setOnClickListener(v -> showCategoryMessage("Buy Homemade"));

        findViewById(R.id.addSeedButton).setOnClickListener(v -> showCategoryMessage("Seed"));
        findViewById(R.id.addCropsButton).setOnClickListener(v -> showCategoryMessage("Crops"));
        findViewById(R.id.addLivestockButton).setOnClickListener(v -> showCategoryMessage("Livestocks"));
        findViewById(R.id.addVegetableButton).setOnClickListener(v -> showCategoryMessage("Vegetables"));
        findViewById(R.id.addHomemadeButton).setOnClickListener(v -> showCategoryMessage("Homemade"));
    }

    /**
     * Updates button styles to make the selected button green and the other white.
     *
     * @param activeButton   The button to highlight (green).
     * @param inactiveButton The button to reset (white).
     */

    private void updateButtonStyles(Button activeButton, Button inactiveButton) {
        // Highlight active button
        activeButton.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        activeButton.setTextColor(getResources().getColor(R.color.white));

        // Reset inactive button to default
        inactiveButton.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        inactiveButton.setTextColor(getResources().getColor(R.color.green));
    }





    /**
     * Displays a toast message for the selected category in the sell grid.
     *
     * @param category Name of the category
     */
    private void showCategoryMessage(String category) {
        Toast.makeText(this, "Selected: " + category, Toast.LENGTH_SHORT).show();
        // Add navigation logic or further functionality for the selected category
    }
}

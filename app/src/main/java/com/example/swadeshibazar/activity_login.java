package com.example.swadeshibazar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login extends AppCompatActivity {

    private RadioGroup userTypeGroup;
    private RadioButton radioFarmer;
    private RadioButton radioConsumer;
    private EditText editEmail;
    private EditText editPassword;
    private TextView textViewRegister;
    private FirebaseAuth mAuth;
    private void saveUserTypeToPreferences(String userType) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_USER_TYPE", userType);
        editor.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        userTypeGroup = findViewById(R.id.userTypeGroup);
        radioFarmer = findViewById(R.id.radioFarmer);
        radioConsumer = findViewById(R.id.radioConsumer);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textViewRegister = findViewById(R.id.textViewRegister);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set OnClickListener for the Login button
        findViewById(R.id.buttonLogin).setOnClickListener(v -> login());

        // Set clickable span for "Register"
        setRegisterClickableSpan();
    }

    private void setRegisterClickableSpan() {
        String fullText = "Are you a new user? Register";
        SpannableString spannableString = new SpannableString(fullText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(activity_login.this, signup.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.sky_blue));
            }
        };

        int startIndex = fullText.indexOf("Register");
        int endIndex = startIndex + "Register".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewRegister.setText(spannableString);
        textViewRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void login() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Ensure a user type is selected
        int selectedUserTypeId = userTypeGroup.getCheckedRadioButtonId();
        if (selectedUserTypeId == -1) {
            Toast.makeText(this, "Select a user type", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = findViewById(selectedUserTypeId);
        String userType = selectedRadioButton.getText().toString();

        // Validate email and password
        if (email.isEmpty()) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign in with Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserTypeToPreferences(userType);

                        Toast.makeText(activity_login.this, "Login successful as " + userType, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_login.this, HomeActivity.class); // Ensure HomeActivity is implemented
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(activity_login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
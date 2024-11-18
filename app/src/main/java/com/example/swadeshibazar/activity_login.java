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
    private EditText editEmail, editPassword;
    private TextView textViewRegister;
    private FirebaseAuth mAuth;

    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_USER_TYPE = "KEY_USER_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialise UI components
        userTypeGroup = findViewById(R.id.userTypeGroup);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textViewRegister = findViewById(R.id.textViewRegister);

        // Initialise Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set onClick listeners
        findViewById(R.id.buttonLogin).setOnClickListener(v -> login());
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

        // Ensure user type is selected
        int selectedUserTypeId = userTypeGroup.getCheckedRadioButtonId();
        if (selectedUserTypeId == -1) {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = findViewById(selectedUserTypeId);
        String userType = selectedRadioButton.getText().toString();

        // Validate input fields
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
                        if (user != null) {
                            saveUserTypeToPreferences(userType);
                            navigateToHome(userType);
                        } else {
                            Toast.makeText(activity_login.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity_login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserTypeToPreferences(String userType) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_TYPE, userType);
        editor.apply();
    }

    private void navigateToHome(String userType) {
        Toast.makeText(this, "Login successful as " + userType, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity_login.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

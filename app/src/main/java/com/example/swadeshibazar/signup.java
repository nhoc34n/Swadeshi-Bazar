package com.example.swadeshibazar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private EditText editName, editEmail, editNumber, editPassword, editConfirmPassword;
    private RadioGroup userTypeGroup;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize UI components
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editNumber = findViewById(R.id.editNumber);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        ImageButton buttonSignup = findViewById(R.id.buttonSignup);

        // Set OnClickListener for Sign-Up button
        buttonSignup.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String number = editNumber.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        // Ensure a user type is selected
        int selectedUserTypeId = userTypeGroup.getCheckedRadioButtonId();
        if (selectedUserTypeId == -1) {
            Toast.makeText(this, "Select a user type", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRadioButton = findViewById(selectedUserTypeId);
        String userType = selectedRadioButton.getText().toString();

        // Validate input fields
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        saveUserToDatabase(name, email, number, userType);
                    } else {
                        Toast.makeText(signup.this, "Sign-Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToDatabase(String name, String email, String number, String userType) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        User user = new User(userId, name, email, number, userType);

        databaseReference.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(signup.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signup.this, activity_login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(signup.this, "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // User class for storing user details in Firebase Realtime Database
    public static class User {
        public String userId, name, email, number, userType;

        public User() {
            // Default constructor required for Firebase
        }

        public User(String userId, String name, String email, String number, String userType) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.number = number;
            this.userType = userType;
        }
    }
}

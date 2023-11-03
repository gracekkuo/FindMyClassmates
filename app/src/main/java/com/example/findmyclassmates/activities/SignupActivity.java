package com.example.findmyclassmates.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmyclassmates.R;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText reenterPassEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = findViewById(R.id.firstNameEditText);
        lastNameText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.uscEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        reenterPassEditText = findViewById(R.id.reenterPasswordEditText);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user's input from the EditText fields
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String reenter = reenterPassEditText.getText().toString();

                if (!isValidEmail(email)) {
                    // Show an error message for invalid email
                    Toast.makeText(SignupActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }
                if (!password.equals(reenter)) {
                    // Show an error message for invalid email
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }

                //insert into db, if valid return 200


                int responseCode = 200;
                if (responseCode == 200) {
                    // Start the MainActivity
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                } else {
                    // Show an error for unsuccessful login
                    Toast.makeText(SignupActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        // Regex pattern for basic email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }
}
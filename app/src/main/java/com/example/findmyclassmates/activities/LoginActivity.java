package com.example.findmyclassmates.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.activities.mainFeatures.TabbedFeatures;
import com.example.findmyclassmates.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the EditTexts by their IDs
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            // Get the user's input from the EditText fields
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!isValidEmail(email)) {
                // Show an error message for invalid email
                Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return; // Don't proceed further
            }

//            User user = new User();
//            user.setEmail(email);
//            user.setPassword(password);

//            viewModel.login(user).observe(this, isSuccessful -> {
//                //Toast.makeText(LoginActivity.this, isSuccessful ? "True": "False", Toast.LENGTH_SHORT).show();
//                if (isSuccessful) startActivity(new Intent(this, TabbedFeatures.class));
//                else Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
//            });
        });

        TextView signinLink = findViewById(R.id.signinButton);
        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
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
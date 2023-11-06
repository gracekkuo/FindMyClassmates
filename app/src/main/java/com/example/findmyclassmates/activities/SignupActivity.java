package com.example.findmyclassmates.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText reenterPassEditText;
    private EditText studentIdText;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = findViewById(R.id.firstNameEditText);
        lastNameText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.uscEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        reenterPassEditText = findViewById(R.id.reenterPasswordEditText);
        studentIdText = findViewById(R.id.studentIdEditText);


        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
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
                String studentId = studentIdText.getText().toString();

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

                System.out.println("validated input ");

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign up successful, update user profile
                                FirebaseUser user = mAuth.getCurrentUser();
                                System.out.println("sign up sucessful");

                                if (user != null) {
                                    // Update user profile
                                    user.updateProfile(new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(firstName + " " + lastName)
                                                    .build())
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    // Profile updated successfully
                                                    // Write user information to the Realtime Database
                                                    writeUserDataToDatabase(user.getUid(), studentId, firstName, lastName, email);
                                                    // Redirect to the main activity
                                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                }
                                            });
                                }
                                /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(firstName + " " + lastName)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                // Profile updated
                                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            }
                                        });
                                 */
                            } else {
                                // Sign up failed
                                Toast.makeText(SignupActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    private boolean isValidEmail(String email) {
        // Regex pattern for basic email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }

    private void writeUserDataToDatabase(String userID, String studentId, String firstName, String lastName, String email) {
        usersRef.child(userID).child("firstName").setValue(firstName);
        usersRef.child(userID).child("lastName").setValue(lastName);
        usersRef.child(userID).child("studentID").setValue(studentId);
        usersRef.child(userID).child("email").setValue(email);
        usersRef.child(userID).child("profilePicture").setValue("");
        usersRef.child(userID).child("enrolledClasses").setValue("");
        usersRef.child(userID).child("blockedIDs").setValue("");
        usersRef.child(userID).child("chats").setValue("");
    }
}
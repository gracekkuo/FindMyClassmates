package com.example.findmyclassmates.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.activities.mainFeatures.CourseViewFragment;
import com.example.findmyclassmates.activities.mainFeatures.TabbedFeatures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.findmyclassmates.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText reenterPassEditText;
    private EditText studentIdText;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference usersRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView profileImageView;
    private boolean uploadImage;
    private Uri uri;

    private Checker checker;

    private TextView signup_tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        uploadImage = false;
        checker = new Checker();
        firstNameText = findViewById(R.id.firstNameEditText);
        lastNameText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.uscEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        reenterPassEditText = findViewById(R.id.reenterPasswordEditText);
        studentIdText = findViewById(R.id.studentIdEditText);
        signup_tv_result = findViewById(R.id.signup_tv_result);

        Button uploadPictureButton = findViewById(R.id.uploadPictureButton);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profileImageView = findViewById(R.id.show_user_profile); // Reference to your ImageView
        ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                uri = result.getData().getData();
                if (uri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        profileImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        uploadPictureButton.setOnClickListener(v -> {
            System.out.println("clicked");
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            mGetContent.launch(galleryIntent);
        });

        //put rest of information into user object
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

                if (!checker.isValidEmail(email)) {
                    signup_tv_result.setText("false");
                    Toast.makeText(SignupActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }
                if (!checker.isValidPasswordReenter(password, reenter)) {
                    signup_tv_result.setText("false");
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }

                if (!checker.isValidStudentId(studentId)){
                    signup_tv_result.setText("false");
                    Toast.makeText(SignupActivity.this, "StudentId is less than 10 characters long.", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }

                if (!checker.isUSCValidEmail(email)){
                    signup_tv_result.setText("false");
                    Toast.makeText(SignupActivity.this, "Not USC email address", Toast.LENGTH_SHORT).show();
                    return; // Don't proceed further
                }

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
                                                    // Redirect to the course view activity
                                                    startActivity(new Intent(SignupActivity.this, TabbedFeatures.class));
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



    private void writeUserDataToDatabase(String userID, String studentId, String firstName, String lastName, String email) {
        usersRef.child(userID).child("firstName").setValue(firstName);
        usersRef.child(userID).child("lastName").setValue(lastName);
        usersRef.child(userID).child("studentID").setValue(studentId);
        usersRef.child(userID).child("email").setValue(email);
        usersRef.child(userID).child("enrolledClasses").setValue("");
        usersRef.child(userID).child("blockedIDs").setValue("");
        usersRef.child(userID).child("chats").setValue("");
        usersRef.child(userID).child("UID").setValue(userID);

        uploadImageToFirebaseStorage(uri);
    }
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        StorageReference ref = storageReference.child("profile_pictures/" + mAuth.getCurrentUser().getUid());
        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the uploaded image URL
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Save the image URL to the database
                        String imageUrl = uri.toString();
                        saveImageUrlToDatabase(imageUrl);
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show());
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        FirebaseUser user = mAuth.getCurrentUser();
        System.out.println("here?");
        if (user != null) {
            usersRef.child(user.getUid()).child("profilePicture").setValue(imageUrl)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("you uploaded image");
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to save image URL to the database.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
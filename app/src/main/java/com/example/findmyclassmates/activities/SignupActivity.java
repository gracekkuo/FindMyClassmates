package com.example.findmyclassmates.activities;

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
import android.widget.Toast;

import com.example.findmyclassmates.R;

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

//    new vars
    EditText emailEt,passwordEt;
    Button signUp;
    String email,password;
    FirebaseAuth auth;
    ImageView showUserProfile;
    private final Integer PICK_IMAGE_REQUEST=1;
    Bitmap bitmap;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = findViewById(R.id.firstNameEditText);
        lastNameText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.uscEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        reenterPassEditText = findViewById(R.id.reenterPasswordEditText);

        initView();
        defineView();
        addCLicklistener();

//        Button signupButton = findViewById(R.id.signupButton);
//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the user's input from the EditText fields
//                String firstName = firstNameText.getText().toString();
//                String lastName = lastNameText.getText().toString();
//                String email = emailEditText.getText().toString();
//                String password = passwordEditText.getText().toString();
//                String reenter = reenterPassEditText.getText().toString();
//
//                if (!isValidEmail(email)) {
//                    // Show an error message for invalid email
//                    Toast.makeText(SignupActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
//                    return; // Don't proceed further
//                }
//                if (!password.equals(reenter)) {
//                    // Show an error message for invalid email
//                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//                    return; // Don't proceed further
//                }
//
//                //insert into db, if valid return 200
//
//
//                int responseCode = 200;
//                if (responseCode == 200) {
//                    // Start the MainActivity
//                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                } else {
//                    // Show an error for unsuccessful login
//                    Toast.makeText(SignupActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    private boolean isValidEmail(String email) {
        // Regex pattern for basic email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }

    private void defineView(){
        emailEt=findViewById(R.id.uscEmailEditText);
        passwordEt=findViewById(R.id.passwordEditText);
        signUp=findViewById(R.id.signupButton);
        showUserProfile=findViewById(R.id.show_user_profile);

    }
    private void initView(){
        auth=FirebaseAuth.getInstance();
    }

    private boolean validate(){
        boolean isValid=false;
        email=emailEt.getText().toString();
        password=passwordEt.getText().toString();
        if(TextUtils.isEmpty(email))
            emailEt.setError("Required");
        else if(TextUtils.isEmpty(password))
            passwordEt.setError("Required");
        else
            isValid=true;
        return isValid;
    }
    private void addCLicklistener(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    registerUserToDatabse();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }
            }
        });

//        for PFP
        showUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                    //show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                    //always show the choose (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }
    private void registerUserToDatabse(){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(SignupActivity.this, "succesfully created user::email is:"+ task.getResult().getUser().getEmail(), Toast.LENGTH_SHORT).show();

                addUserInDatabse(task.getResult().getUser());
            }
        });

    }

    private void addUserInDatabse(FirebaseUser firebaseUser){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] data = bytes.toByteArray();

        // NEED HELP GETTING PROFILE PICTURE UPLOAD WORKING

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = databaseReference.child("users");

        Map<String, Object> userData = new HashMap<>();
        userData.put("User ID", firebaseUser.getUid().toString());
        userData.put("first name", firstNameText.getText().toString());
        userData.put("last name", lastNameText.getText().toString());
        userData.put("email", emailEditText.getText().toString());
        userData.put("password", passwordEditText.getText().toString());


        System.out.println("here");
        userRef.push().setValue(userData, (error, ref) -> {
            if (error == null) {
                System.out.println("user added successfully");
            } else {
                System.err.println("user addition failed: " + error.getMessage());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "hey you selected image" + bitmap, Toast.LENGTH_SHORT).show();
                showUserProfile.setImageBitmap(bitmap);
                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

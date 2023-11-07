package com.example.findmyclassmates.activities.mainFeatures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaveReviewActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String dept;
    private String courseID;
    private EditText response1, response2, response3, response4, response5;
    private Button submitButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        Intent intent = getIntent();
        dept = intent.getStringExtra("dept");
        courseID = intent.getStringExtra("courseID");
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth


        // Initialize your EditText fields and the submit button
        response1 = findViewById(R.id.response1);
        response2 = findViewById(R.id.response2);
        response3 = findViewById(R.id.response3);
        response4 = findViewById(R.id.response4);
        response5 = findViewById(R.id.response5);
        submitButton = findViewById(R.id.submitReview);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any field is empty
                if (isEmpty(response1) || isEmpty(response2) || isEmpty(response3) ||
                        isEmpty(response4) || isEmpty(response5)) {
                    showPopup("Please fill in all fields.");
                } else {
                    // Check if response2 is a valid integer between 1 and 5
                    int score;
                    try {
                        score = Integer.parseInt(response2.getText().toString());
                        if (score < 1 || score > 5) {
                            showPopup("Score must be an integer between 1 and 5.");
                        } else {
                            // All fields are populated correctly
                            // You can proceed with further actions or submit the form.
                            addReviewsToDB addReviewsToDB = new addReviewsToDB();
                            //TODO: make user the actual user's name
                            String uid = mAuth.getCurrentUser().getUid();
                            addReviewsToDB.addReview(uid, databaseReference, dept, courseID, response1.getText().toString(), Integer.parseInt(response2.getText().toString()),
                                    response3.getText().toString(), response4.getText().toString(), response5.getText().toString(), 0, 0);
                            finish();
                        }
                    } catch (NumberFormatException e) {
                        showPopup("Score must be a valid integer.");
                    }
                }
            }
        });
    }

    private boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    private void showPopup(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
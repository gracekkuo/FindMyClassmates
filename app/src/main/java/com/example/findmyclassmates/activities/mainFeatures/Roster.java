package com.example.findmyclassmates.activities.mainFeatures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Roster extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String dept;
    private String courseID;
    Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        Intent intent = getIntent();
        dept = intent.getStringExtra("dept");
        courseID = intent.getStringExtra("courseID");
        context = this;

        TextView title = findViewById(R.id.courseTitleTextView);
        title.setText(dept + " - " + courseID + " Roster");

        // Reference to the LinearLayout in your XML layout
        LinearLayout textContainer = findViewById(R.id.textContainer);


        databaseReference= FirebaseDatabase.getInstance().getReference("courseRoster");
        Query query = databaseReference.orderByChild("deptCourseID").equalTo(dept+courseID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deptCourseSnapshot : dataSnapshot.getChildren()) {
                    String deptCourseID = deptCourseSnapshot.getKey();

                    Map<String, Student> studentsMap = new HashMap<>();
                    if (deptCourseSnapshot.child("students").getValue() != null) {
                        studentsMap = (Map<String, Student>) deptCourseSnapshot.child("students").getValue();

                        for (Map.Entry<String, Student> entry : studentsMap.entrySet()) {
                            Map<String, Object> studentData = (Map<String, Object>) entry.getValue();
                            String email = (String) studentData.get("email");
                            String firstName = (String) studentData.get("firstName");
                            String lastName = (String) studentData.get("lastName");


                            String print = firstName + " " + lastName + "\t\t" + email;

                            TextView textView = new TextView(context);
                            textView.setText(print);
                            textView.setTextSize(18);
                            textView.setPadding(0, 8, 0, 8); // Add padding for spacing

                            // Add an OnClickListener to the TextView
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mAuth = FirebaseAuth.getInstance();
                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    //set initial title and visibility
                                    if (currentUser != null) {
                                        String userID = currentUser.getUid();
                                        mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    // Retrieve user information from the database
                                                    userEmail = dataSnapshot.child("email").getValue(String.class);
                                                    if (userEmail.equals(email))
                                                    {
                                                        showPopup("This is you.");
                                                    }
                                                    else{
                                                        // Create an Intent to start a new activity
                                                        Intent intent = new Intent(context, ViewProfile.class);
                                                        // Put the email as an extra in the Intent
                                                        intent.putExtra("email", email);
                                                        // Start the new activity
                                                        context.startActivity(intent);
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                // Handle errors
                                            }
                                        });
                                    }


                                }
                            });

                            // Add the TextView to the LinearLayout
                            textContainer.addView(textView);

                        }

                    }
                    else {
                        //this should never happen bc the accessing person should be in the roster
                        TextView textView = new TextView(context);
                        textView.setText("No Students");
                        textView.setTextSize(18);
                        textView.setPadding(0, 8, 0, 8); // Add padding for spacing

                        // Add the TextView to the LinearLayout
                        textContainer.addView(textView);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });


        // Create and add TextViews dynamically
        /*for (int i = 1; i <= 20; i++) {
            TextView textView = new TextView(this);
            textView.setText("This is TextView #" + i);
            textView.setTextSize(18);
            textView.setPadding(0, 8, 0, 8); // Add padding for spacing

            // Add the TextView to the LinearLayout
            textContainer.addView(textView);
        }*/
    }

    private void showPopup(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
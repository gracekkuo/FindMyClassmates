package com.example.findmyclassmates.activities.mainFeatures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewProfile extends AppCompatActivity {

    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewStudentID;
    private Button buttonChat;
    private Button buttonBlock;
    private DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        textViewFirstName = findViewById(R.id.textViewFirstName);
        textViewLastName = findViewById(R.id.textViewLastName);
        textViewStudentID = findViewById(R.id.textViewStudentID);
        buttonChat = findViewById(R.id.buttonChat);
        buttonBlock = findViewById(R.id.buttonBlock);


        // Create a query to find the user with the specified email
        Query query = usersRef.orderByChild("email").equalTo(email);
        //System.out.println(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("got snapshot");
                // Check if the email exists in the database
                if (dataSnapshot.exists()) {
                    //System.out.println("snapshot exists");
                    // Iterate through the results
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Deserialize the user object
                        User user = userSnapshot.getValue(User.class);
                        // Now you have the user object
                        //System.out.println("got user" +user.getFirstName());
                        textViewFirstName.setText(user.getFirstName());
                        textViewLastName.setText(user.getLastName());
                        textViewStudentID.setText(user.getStudentID());

                        String toBlockUID =userSnapshot.child("UID").getValue(String.class);

                        mAuth = FirebaseAuth.getInstance();
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

                        currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            String userID = currentUser.getUid();
                            mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot currUserSnapshot) {
                                    if (currUserSnapshot.exists()) {
                                        // Retrieve user information from the database
                                        String blockedIDs=currUserSnapshot.child("blockedIDs").getValue(String.class);
                                        if(blockedIDs.contains(","+toBlockUID))
                                        {
                                            buttonBlock.setText("UnBlock User");
                                        }
                                        else {
                                            buttonBlock.setText("Block User");
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
                } else {
                    // Handle the case where the email was not found
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the new activity
                //TODO: this needs to be fixed to go to the right place
                Intent chatIntent = new Intent(ViewProfile.this, TabbedFeatures.class);

                // Pass the email as an extra in the Intent
                chatIntent.putExtra("email", email);

                // Start the new activity
                startActivity(chatIntent);
            }
        });

        buttonBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = usersRef.orderByChild("email").equalTo(email);
                //System.out.println(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //System.out.println("got snapshot");
                        // Check if the email exists in the database
                        if (dataSnapshot.exists()) {
                            //System.out.println("snapshot exists");
                            // Iterate through the results
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                // Now you have the user object
                                String toBlockUID =userSnapshot.child("UID").getValue(String.class);
                                mAuth = FirebaseAuth.getInstance();
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

                                currentUser = mAuth.getCurrentUser();
                                if (currentUser != null) {
                                    String userID = currentUser.getUid();
                                    mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot currUserSnapshot) {
                                            if (currUserSnapshot.exists()) {
                                                // Retrieve user information from the database

                                                String blockedIDs=currUserSnapshot.child("blockedIDs").getValue(String.class);
                                                if(blockedIDs.contains(","+toBlockUID))
                                                {
                                                    buttonBlock.setText("Block User");
                                                    currUserSnapshot.child("blockedIDs").getRef().setValue(blockedIDs.replaceAll(","+toBlockUID, ""));
                                                }
                                                else {
                                                    buttonBlock.setText("Unblock User");
                                                    currUserSnapshot.child("blockedIDs").getRef().setValue(blockedIDs += "," + toBlockUID);
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
                        } else {
                            // Handle the case where the email was not found
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                    }
                });
            }
        });

    }
}
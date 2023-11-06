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
    private DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

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



    }
}
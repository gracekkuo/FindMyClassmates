package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView textViewFirstName;
    private EditText editTextFirstName;
    private TextView textViewLastName;
    private EditText editTextLastName;
    private TextView textViewStudentID;
    private EditText editTextStudentID;

    private TextView textViewStatus;
    private EditText editTextStatus;

    private Button buttonSave;
    private Button buttonCancel;
    private Button buttonLogout;
    private TextView invalidBlank;
    private TextView invalidStudentID;
    private TextView invalidStatus;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private ImageView profileImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(/*String param1, String param2*/) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profile_image_display);
        textViewFirstName = view.findViewById(R.id.textViewFirstName);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        textViewLastName = view.findViewById(R.id.textViewLastName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        textViewStudentID = view.findViewById(R.id.textViewStudentID);
        editTextStudentID = view.findViewById(R.id.editTextStudentID);

        textViewStatus = view.findViewById(R.id.textViewStatus);
        editTextStatus = view.findViewById(R.id.editTextStatus);

        buttonSave = view.findViewById(R.id.buttonSave);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        invalidBlank = view.findViewById(R.id.invalidBlank);
        invalidStudentID = view.findViewById(R.id.invalidStudentID);
        invalidStatus = view.findViewById(R.id.invalidStatus);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user information from the database
                        String firstName = dataSnapshot.child("firstName").getValue(String.class);
                        String lastName = dataSnapshot.child("lastName").getValue(String.class);
                        String studentID = dataSnapshot.child("studentID").getValue(String.class);

                        // Set retrieved data to the respective views
                        textViewFirstName.setText(firstName);
                        textViewLastName.setText(lastName);
                        textViewStudentID.setText(studentID);
                        textViewStatus.setText("Undergraduate Student");

                        if (dataSnapshot.child("profilePicture").exists()) {
                            String profilePictureUrl = dataSnapshot.child("profilePicture").getValue(String.class);

                            // Load the profile picture into the ImageView using Glide or other image loading libraries
                            Glide.with(getContext())
                                    .load(profilePictureUrl)
                                    .placeholder(R.drawable.ic_add_profile) // Placeholder image
                                    .error(R.drawable.ic_add_profile) // Error image if loading fails
                                    .into(profileImageView);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        // Set click listener for the TextView to enable editing
        textViewFirstName.setOnClickListener(v -> enableEditing(textViewFirstName, editTextFirstName));
        textViewLastName.setOnClickListener(v -> enableEditing(textViewLastName, editTextLastName));
        textViewStudentID.setOnClickListener(v -> enableEditing(textViewStudentID, editTextStudentID));
        textViewStatus.setOnClickListener(v -> enableEditing(textViewStatus, editTextStatus));

        // Set click listener for the "Save" button
        buttonSave.setOnClickListener(v -> saveChanges());
        // Set click listener for the "Cancel" button
        buttonCancel.setOnClickListener(v -> cancelChanges());
        // Initially, the buttons are hidden
        buttonSave.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);

        buttonLogout.setOnClickListener(v -> logoutUser());


        return view;
        //return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish(); // Optional: Finish the current activity
        }
    }


    private void enableEditing(TextView textView, EditText editText) {
        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText(textView.getText());
        editText.requestFocus();
        buttonSave.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
    }

    private void saveChanges() {
        invalidBlank.setVisibility(View.GONE);
        invalidStudentID.setVisibility(View.GONE);
        invalidStatus.setVisibility(View.GONE);
        if(editTextFirstName.getVisibility()==View.VISIBLE)
        {
            String newFirstName = editTextFirstName.getText().toString().trim();
            if (newFirstName.isEmpty())
            {
                //set textview visible
                invalidBlank.setVisibility(View.VISIBLE);
            }
            else {
            textViewFirstName.setText(newFirstName);
            }
        }
        if(editTextLastName.getVisibility()==View.VISIBLE)
        {
            String newLastName = editTextLastName.getText().toString().trim();
            if (newLastName.isEmpty())
            {
                //set textview visible
                invalidBlank.setVisibility(View.VISIBLE);
            }
            else {
                textViewLastName.setText(newLastName);
            }
        }
        if(editTextStudentID.getVisibility()==View.VISIBLE)
        {
            String newStudentID = editTextStudentID.getText().toString().trim();
            if (newStudentID.isEmpty())
            {
                //set textview visible
                invalidBlank.setVisibility(View.VISIBLE);
            }
            if (newStudentID.length()!=10)
            {
                //set textview visible
                invalidStudentID.setVisibility(View.VISIBLE);
            }
            else {
                try {
                    Integer.parseInt(newStudentID);
                    //System.out.println("The string is an integer: " + number);
                    textViewStudentID.setText(newStudentID);
                } catch (NumberFormatException e) {
                    //set textview visible
                    invalidStudentID.setVisibility(View.VISIBLE);
                    //System.out.println("The string is not an integer.");
                }
            }
        }
        if(editTextStatus.getVisibility()==View.VISIBLE) {
            String newStatus = editTextStatus.getText().toString().trim();
            if (newStatus.isEmpty())
            {
                //set textview visible
                invalidBlank.setVisibility(View.VISIBLE);
            }
            else if(!newStatus.equals("Undergraduate Student") && !newStatus.equals("Graduate Student") &&
                    !newStatus.equals("Faculty") && !newStatus.equals("Staff")) {
                //not equal to any of the valid statuses
                invalidStatus.setVisibility(View.VISIBLE);
            }
            else {
                textViewStatus.setText(newStatus);
            }
        }
        // Hide the EditText field
        editTextFirstName.setVisibility(View.INVISIBLE);
        editTextLastName.setVisibility(View.INVISIBLE);
        editTextStudentID.setVisibility(View.INVISIBLE);
        editTextStatus.setVisibility(View.INVISIBLE);
        // Show the TextView
        textViewFirstName.setVisibility(View.VISIBLE);
        textViewLastName.setVisibility(View.VISIBLE);
        textViewStudentID.setVisibility(View.VISIBLE);
        textViewStatus.setVisibility(View.VISIBLE);
        //make buttons invisible
        buttonSave.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.INVISIBLE);

        //save changes in firebase
        if (currentUser != null) {
            String userID = currentUser.getUid();
            mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user information from the database
                        dataSnapshot.child("firstName").getRef().setValue(textViewFirstName.getText().toString());
                        dataSnapshot.child("lastName").getRef().setValue(textViewLastName.getText().toString());
                        dataSnapshot.child("studentID").getRef().setValue(textViewStudentID.getText().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

    public void cancelChanges() {
        // Hide the EditText field
        editTextFirstName.setVisibility(View.INVISIBLE);
        editTextLastName.setVisibility(View.INVISIBLE);
        editTextStudentID.setVisibility(View.INVISIBLE);
        editTextStatus.setVisibility(View.INVISIBLE);
        // Show the TextView
        textViewFirstName.setVisibility(View.VISIBLE);
        textViewLastName.setVisibility(View.VISIBLE);
        textViewStudentID.setVisibility(View.VISIBLE);
        textViewStatus.setVisibility(View.VISIBLE);
        //make buttons invisible
        buttonSave.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
    }

}
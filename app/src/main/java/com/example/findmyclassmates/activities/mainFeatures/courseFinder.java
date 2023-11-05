package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Course;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.*;

public class courseFinder {
    private DatabaseReference databaseReference;
    public Course course;

    public courseFinder(DatabaseReference databaseReference) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("courses");
    }

    public Course findCourse(String departmentToFind, String courseIDToFind, Context context) {
        // Create a query to find the course based on department and courseID
        Query query = databaseReference.orderByChild("dept").equalTo(departmentToFind);

        course = new Course("", "", "", "", "", 0, "", "");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    Course newCourse = courseSnapshot.getValue(Course.class);
                    //System.out.println(newCourse.getCourseID());

                    // Check if the course matches the courseID
                    if (newCourse.getCourseID().equals(courseIDToFind)) {
                        System.out.println("found" + newCourse.getCourseID());
                        course = newCourse;
                        System.out.println("set course to newcourse" + course.getCourseID());
                        System.out.println("what's being returned" + course.getCourseID());

                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                        View view= LayoutInflater.from(context).inflate(com.example.findmyclassmates.R.layout.course_info_popup, null);

                        // Initialize and set the data in the pop-up
                        TextView courseID = view.findViewById(com.example.findmyclassmates.R.id.textViewCourseID);
                        courseID.setText("Course ID: " + course.getDept() + " - "+course.getCourseID());

                        TextView courseName = view.findViewById(com.example.findmyclassmates.R.id.textViewCourseName);
                        courseName.setText("Course Name: " + course.getName());

                        TextView professor = view.findViewById(com.example.findmyclassmates.R.id.textViewProfessor);
                        professor.setText("Professor: "+course.getProf());

                        TextView time = view.findViewById(com.example.findmyclassmates.R.id.textViewTime);
                        time.setText("Time: " +course.getTime());

                        TextView units = view.findViewById(com.example.findmyclassmates.R.id.textViewUnits);
                        units.setText("Units: " +course.getUnits());

                        Button registerButton = view.findViewById(com.example.findmyclassmates.R.id.buttonRegister);
                        registerButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Check the current text of the button
                                Button rosterButton = view.findViewById(com.example.findmyclassmates.R.id.buttonRoster);
                                if (registerButton.getText().toString().equals("Register")) {
                                    // If the text is "Register," change it to "Drop Class"
                                    registerButton.setText("Drop Class");
                                    rosterButton.setVisibility(view.VISIBLE);
                                    // TODO: update the database
                                } else {
                                    // If the text is "Drop Class," change it back to "Register"
                                    registerButton.setText("Register");
                                    rosterButton.setVisibility(view.GONE);
                                    // TODO: update the database
                                }
                            }
                        });

                        Button reviewsButton = view.findViewById(R.id.buttonReviews);
                        reviewsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: direct to page with students
                            }
                        });

                        Button rosterButton = view.findViewById(R.id.buttonRoster);
                        rosterButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO: direct to page with students
                            }
                        });
                        // Create a BottomSheetDialog
                        bottomSheetDialog.setContentView(view);
                        bottomSheetDialog.show();
                        return;
                        // You've found the course that matches both conditions
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Course retrieval failed: " + databaseError.getMessage());
            }
        });

        return course;

    }


    public static void main(String[] args) {
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        // Create an instance of CourseFinder
        courseFinder courseFinder = new courseFinder(databaseReference);

        // Define the department and courseID you want to find
        String departmentToFind = "CSCI";
        String courseIDToFind = "102";

        // Call the method to find the course
        //courseFinder.findCourse(departmentToFind, courseIDToFind, context);
    }
}

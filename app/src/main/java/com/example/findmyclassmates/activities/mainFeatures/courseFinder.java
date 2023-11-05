package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Course;
import com.example.findmyclassmates.models.Student;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class courseFinder {
    private DatabaseReference databaseReference;
    private DatabaseReference rosterReference;
    public Course course;
    public String firstName="testFirstName";
    public String lastName="testLastName";
    public String email="test@gmail.com";

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
                                    rosterReference=FirebaseDatabase.getInstance().getReference("courseRoster");
                                    Query query = rosterReference.orderByChild("deptCourseID").equalTo(departmentToFind+courseIDToFind);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot deptCourseSnapshot : dataSnapshot.getChildren()) {
                                                String deptCourseID = deptCourseSnapshot.getKey();

                                                // Create a new student object
                                                Student student = new Student(firstName, lastName, email);

                                                // Add the student to the deptCourseID's students map
                                                Map<String, Student> studentsMap = new HashMap<>();
                                                if (deptCourseSnapshot.child("students").getValue() != null) {
                                                    studentsMap = (Map<String, Student>) deptCourseSnapshot.child("students").getValue();
                                                }

                                                // Generate a unique key for the student
                                                String studentKey = rosterReference.child("students").push().getKey();

                                                // Add the student to the map
                                                studentsMap.put(studentKey, student);

                                                // Update the students map in the database
                                                deptCourseSnapshot.child("students").getRef().setValue(studentsMap);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Handle any errors here
                                        }
                                    });
                                } else {
                                    // If the text is "Drop Class," change it back to "Register"
                                    registerButton.setText("Register");
                                    rosterButton.setVisibility(view.GONE);
                                    // TODO: update the database
                                    rosterReference=FirebaseDatabase.getInstance().getReference("courseRoster");
                                    Query query = rosterReference.orderByChild("deptCourseID")
                                            .equalTo(departmentToFind+courseIDToFind);
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot deptCourseSnapshot : dataSnapshot.getChildren()) {
                                                String deptCourseID = deptCourseSnapshot.getKey();

                                                // Retrieve the students map from the deptCourseID node
                                                DataSnapshot studentsSnapshot = deptCourseSnapshot.child("students");
                                                Map<String, Object> studentsMap = (Map<String, Object>) studentsSnapshot.getValue();

                                                if (studentsMap != null) {
                                                    // Find the student by email
                                                    String studentKeyToRemove = null;
                                                    for (Map.Entry<String, Object> entry : studentsMap.entrySet()) {
                                                        Map<String, Object> studentData = (Map<String, Object>) entry.getValue();
                                                        String studentEmail = (String) studentData.get("email");
                                                        if (studentEmail != null && studentEmail.equals(email)) {
                                                            studentKeyToRemove = entry.getKey();
                                                            break;
                                                        }
                                                    }

                                                    if (studentKeyToRemove != null) {
                                                        // Remove the student from the map
                                                        studentsMap.remove(studentKeyToRemove);

                                                        // Update the students map in the database
                                                        deptCourseSnapshot.child("students").getRef().setValue(studentsMap);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            System.err.println("Course retrieval failed: " + databaseError.getMessage());
                                        }
                                    });
                                }
                            }
                        });

                        Button reviewsButton = view.findViewById(R.id.buttonReviews);
                        reviewsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ReviewsActivity.class);
                                intent.putExtra("dept", course.getDept());
                                intent.putExtra("courseID", course.getCourseID());
                                context.startActivity(intent);
                            }
                        });

                        Button rosterButton = view.findViewById(R.id.buttonRoster);
                        rosterButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, Roster.class);
                                intent.putExtra("dept", course.getDept());
                                intent.putExtra("courseID", course.getCourseID());
                                context.startActivity(intent);
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

}

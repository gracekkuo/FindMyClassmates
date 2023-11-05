package com.example.findmyclassmates.activities.mainFeatures;

import com.example.findmyclassmates.models.Course;
import com.google.firebase.database.*;

public class courseFinder {
    private DatabaseReference databaseReference;

    public courseFinder(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void findCourse(String departmentToFind, String courseIDToFind, CourseCallback callback) {
        // Create a query to find the course based on department and courseID
        Query query = databaseReference.child("courses")
                .orderByChild("dept_courseID")
                .equalTo(departmentToFind + "_" + courseIDToFind);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Course found
                    for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                        // Get the course data
                        String dept = courseSnapshot.child("dept").getValue(String.class);
                        String courseID = courseSnapshot.child("courseID").getValue(String.class);
                        String name = courseSnapshot.child("name").getValue(String.class);
                        String prof = courseSnapshot.child("prof").getValue(String.class);
                        String time = courseSnapshot.child("time").getValue(String.class);
                        int units = courseSnapshot.child("units").getValue(Integer.class);
                        String roster = courseSnapshot.child("roster").getValue(String.class);
                        String reviews = courseSnapshot.child("reviews").getValue(String.class);

                        Course course = new Course(dept, courseID, name, prof, time, units, roster, reviews);

                        // Invoke the callback with the found course
                        callback.onCourseFound(course);
                        return; // Exit the loop after finding the first matching course
                    }
                } else {
                    callback.onCourseNotFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCourseRetrievalFailed(databaseError.getMessage());
            }
        });
    }

    // Define the CourseCallback interface
    public interface CourseCallback {
        void onCourseFound(Course course);

        void onCourseNotFound();

        void onCourseRetrievalFailed(String errorMessage);
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
        //courseFinder.findCourse(departmentToFind, courseIDToFind);
    }
}

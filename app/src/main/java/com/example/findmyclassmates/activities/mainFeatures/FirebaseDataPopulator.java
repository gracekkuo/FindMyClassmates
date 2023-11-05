package com.example.findmyclassmates.activities.mainFeatures;

import com.example.findmyclassmates.models.DeptCourseID;
import com.example.findmyclassmates.models.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDataPopulator {

    public static void main() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // Add the database entries
        String[] departments = {
                "CSCI", "CSCI", "CSCI", "CSCI", "CSCI", "CSCI",
                "BUAD", "BUAD", "BUAD", "BUAD", "BUAD", "BUAD",
                "DANC", "DANC", "DANC", "DANC", "DANC", "DANC",
                "ITP", "ITP", "ITP", "ITP", "ITP", "ITP",
                "DSCI", "DSCI", "DSCI", "DSCI", "DSCI", "DSCI"
        };

        String[] courseIDs = {
                "102", "103", "104", "170", "201", "270",
                "301", "302", "304", "306", "307", "310",
                "101", "110", "120", "131", "140", "150",
                "101", "104", "115", "116", "125", "165",
                "351", "352", "454", "510", "525", "529"
        };

        for (int i = 0; i < departments.length; i++) {
            String deptCourseID=departments[i]+courseIDs[i];
            populateCourseRoster(databaseReference, deptCourseID);
        }
    }

    public static void populateCourseRoster(DatabaseReference databaseReference, String deptCourseID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference courseRosterRef = database.getReference("courseRoster");

        // Create and populate a sample DeptCourseID with students
        DeptCourseID newDeptCourse = new DeptCourseID();
        newDeptCourse.setDeptCourseID(deptCourseID);
        Student student1 = new Student("John", "Doe", "john.doe@example.com");
        Student student2 = new Student("Jane", "Doe", "jane.doe@example.com");

        // You can add more students here if needed

        // Create a map to associate students with their unique IDs
        Map<String, Student> students = new HashMap<>();
        students.put(courseRosterRef.push().getKey(), student1);
        students.put(courseRosterRef.push().getKey(), student2);
        // Set the students for the DeptCourseID
        newDeptCourse.setStudents(students);

        // Generate a unique key for the new DeptCourseID
        String newDeptCourseIDKey = courseRosterRef.push().getKey();

        // Set the DeptCourseID in the courseRoster reference with the unique key
        courseRosterRef.child(newDeptCourseIDKey).setValue(newDeptCourse);
    }
}
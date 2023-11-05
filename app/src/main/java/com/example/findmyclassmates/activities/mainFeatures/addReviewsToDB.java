package com.example.findmyclassmates.activities.mainFeatures;

//import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class addReviewsToDB {
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
            String dept = departments[i];
            String courseID = courseIDs[i];

            // Generate unique review comments
            String reviewComment1 = "it was not that bad";
            String reviewComment2 = "it can get tough sometimes";

            // Add the first review
            addReview(databaseReference, dept, courseID, reviewComment1, 4, "mandatory", "accepts late homework", "nothing else to say", 0, 0, "John Doe");

            // Add the second review
            addReview(databaseReference, dept, courseID, reviewComment2, 5, "not sure", "late policy in syllabus", "Other comments", 0, 0, "Jane Doe");
        }

    }

    private static void addReview(DatabaseReference databaseReference, String dept, String courseID, String one, int two, String three, String four, String five, int upvotes, int downvotes, String user ) {
        DatabaseReference reviewRef = databaseReference.child("reviews");

        //1. The workload of the class
        //2. The score they would like to give to this class
        //3. If the professor checks attendance
        //4. If the professor allows late homework submission
        //5. Other comments

        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("dept", dept);
        reviewData.put("courseID", courseID);
        reviewData.put("one", one);
        reviewData.put("two", two);
        reviewData.put("three", three);
        reviewData.put("four", four);
        reviewData.put("five", five);
        reviewData.put("upvotes", upvotes);
        reviewData.put("downvotes", downvotes);
        reviewData.put("user", user);

        reviewRef.push().setValue(reviewData, (error, ref) -> {
            if (error == null) {
                System.out.println("Review added successfully");
            } else {
                System.err.println("Review addition failed: " + error.getMessage());
            }
        });
    }
}
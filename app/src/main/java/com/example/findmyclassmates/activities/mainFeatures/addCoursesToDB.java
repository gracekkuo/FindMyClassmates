package com.example.findmyclassmates.activities.mainFeatures;

//import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class addCoursesToDB {
    public static void main() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // Add the database entries
        addCourse(databaseReference, "CSCI", "102", "Fundamentals of Computation", "Mohammad Reza Rajati", "TTH 3:00-4:20", 4, "", "");
        addCourse(databaseReference, "CSCI", "103", "Introduction to Programming", "Mark Redekopp", "MW: 10:00-11:20", 4, "", "");
        addCourse(databaseReference, "CSCI", "104", "Data Structures and Object Oriented Design", "Andrew Goodney", "MW: 10:00-11:20", 4, "", "");
        addCourse(databaseReference, "CSCI", "170", "Discrete Methods in Computer Science", "Aaron Cote", "TTH 3:00-4:20", 4, "", "");
        addCourse(databaseReference, "CSCI", "201", "Principles of Software Development", "Marco Papa", "TTH 11:00-12:50", 4, "", "");
        addCourse(databaseReference, "CSCI", "270", "Introduction to Algorithms and Theory of Computing", "David Kempe", "MW: 2:00-3:20", 4, "", "");
        addCourse(databaseReference, "BUAD", "301", "Technology Entrepreneurship", "Anthony Borquez", "MW: 10:00-11:50", 4, "", "");
        addCourse(databaseReference, "BUAD", "302", "Communication Strategy in Business", "Yijia Guo", "TTH: 12:00-1:50", 4, "", "");
        addCourse(databaseReference, "BUAD", "304", "Organizational Behavior and Leadership", "Thomas Cummings", "TTH: 2:00-3:50", 4, "", "");
        addCourse(databaseReference, "BUAD", "306", "Business Finance", "Miao Zhang", "MW: 12:00-1:50", 4, "", "");
        addCourse(databaseReference, "BUAD", "307", "Marketing Fundamentals", "Lars Perner", "MW: 2:00-3:50", 4, "", "");
        addCourse(databaseReference, "BUAD", "310", "Applied Business Statistics", "Dennis Shen", "MWF: 11:00-11:50", 4, "", "");
        addCourse(databaseReference, "DANC", "101", "Colloquium I: Joining the Creative Community", "Jenniffer Lott", "MWF: 10:00-10:50", 4, "", "");
        addCourse(databaseReference, "DANC", "110", "Dance Technique I", "Dante Rose", "TTH: 3:00-4:20", 4, "", "");
        addCourse(databaseReference, "DANC", "120", "Repertory and Performance I", "Bruce McCormick", "MW: 2:00-3:20", 4, "", "");
        addCourse(databaseReference, "DANC", "131", "Composition: Movement Invention and Syntax", "Sabela Grimes", "MW: 10:00-11:20", 4, "", "");
        addCourse(databaseReference, "DANC", "140", "Dance and Health", "Patrick Corbin", "MWF: 11:00-11:50", 4, "", "");
        addCourse(databaseReference, "DANC", "150", "Dance and New Media", "Betsy Struxness", "TTH 11:00-12:50", 4, "", "");
        addCourse(databaseReference, "ITP", "101", "Introduction to Business Information Technologies", "Gregg Ibbotson", "TTH 3:00-4:20", 4, "", "");
        addCourse(databaseReference, "ITP", "104", "Introduction to Web Development", "Zune Nguyen", "TTH 11:00-12:50", 4, "", "");
        addCourse(databaseReference, "ITP", "115", "Programming in Python", "Kristof Aldenderfer", "MW: 2:00-3:20", 4, "", "");
        addCourse(databaseReference, "ITP", "116", "Accelerated Programming in Python", "Sinan Seyman", "MW: 10:00-11:50", 4, "", "");
        addCourse(databaseReference, "ITP", "125", "Introduction to Information Security", "Pierson Clair", "TTH: 12:00-1:50", 4, "", "");
        addCourse(databaseReference, "ITP", "165", "Introduction to C++ Programming", "Matthew Whiting", "TTH: 2:00-3:50", 4, "", "");
        addCourse(databaseReference, "DSCI", "351", "Foundations of Data Management", "Wensheng Wu", "MW: 10:00-11:20", 4, "", "");
        addCourse(databaseReference, "DSCI", "352", "Applied Machine Learning and Data Mining", "Fred Morstatter", "MW: 10:00-11:20", 4, "", "");
        addCourse(databaseReference, "DSCI", "454", "Data Visualization and User Interface Design", "David Chan", "TTH 3:00-4:20", 4, "", "");
        addCourse(databaseReference, "DSCI", "510", "Principles of Programming for Data Science", "Tanya Rustov", "TTH 11:00-12:50", 4, "", "");
        addCourse(databaseReference, "DSCI", "525", "Trusted System Design, Analysis, and Development", "Clifford Neuman", "MW: 2:00-3:20", 4, "", "");
        addCourse(databaseReference, "DSCI", "529", "Security and Privacy", "Luca Luceri", "MW: 10:00-11:50", 4, "", "");

    }

    public static void addCourse(DatabaseReference databaseReference, String dept, String courseID, String name, String prof, String time, int units, String rosterKey, String reviewsKey) {
        DatabaseReference courseRef = databaseReference.child("courses");

        Map<String, Object> courseData = new HashMap<>();
        courseData.put("dept", dept);
        courseData.put("courseID", courseID);
        courseData.put("name", name);
        courseData.put("prof", prof);
        courseData.put("time", time);
        courseData.put("units", units);
        courseData.put("roster", rosterKey);
        courseData.put("reviews", reviewsKey);

        courseRef.push().setValue(courseData, (error, ref) -> {
            if (error == null) {
                System.out.println("Course added successfully");
            } else {
                System.err.println("Course addition failed: " + error.getMessage());
            }
        });
    }
}


package com.example.findmyclassmates.models;

public class Student {
    private String firstName;
    private String lastName;
    private String email;

    public Student() {
        // Required default constructor for Firebase
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

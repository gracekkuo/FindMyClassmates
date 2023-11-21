package com.example.findmyclassmates.activities;

public class Checker {

    public boolean isValidPasswordReenter (String password, String reenter){
        return password.equals(reenter);
    }
    public boolean isValidEmail(String email) {
        // Regex pattern for basic email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }

    public boolean isValidStudentId(String id){
        return id.length() == 10;
    }

    public boolean isUSCValidEmail(String email) {
        // Regex pattern for basic email validation and checking for the domain @usc.edu
        String emailPattern = "[a-zA-Z0-9._-]+@usc\\.edu";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }
}

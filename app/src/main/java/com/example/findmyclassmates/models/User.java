package com.example.findmyclassmates.models;

import java.io.Serializable;

public class User implements Serializable{
    private String blockedIDs;
    private String chats;
    private String email;
    private String enrolledClasses;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String status;
    private String studentID;

    // Default constructor is required for Firebase deserialization.
    public User() {
        // Default constructor required by Firebase.
    }

    public User(String blockedIDs, String chats, String email, String enrolledClasses,
                String firstName, String lastName, String profilePicture, String status, String studentID) {
        this.blockedIDs = blockedIDs;
        this.chats = chats;
        this.email = email;
        this.enrolledClasses = enrolledClasses;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.status = status;
        this.studentID = studentID;
    }

    // Getter and Setter methods for each field.

    public String getBlockedIDs() {
        return blockedIDs;
    }

    public void setBlockedIDs(String blockedIDs) {
        this.blockedIDs = blockedIDs;
    }

    public String getChats() {
        return chats;
    }

    public void setChats(String chats) {
        this.chats = chats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrolledClasses() {
        return enrolledClasses;
    }

    public void setEnrolledClasses(String enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}

//public class User implements Serializable {
//    private String profilePic;
//    private String userID;
//
//    String status;
//
//    private int id;
//    String firstName;
//    String lastName;
//    String email;
//    String usertype;
//    String profilePhoto;
//    String password;
//
//    public User(String emailAddress, String userID, String profilePic, String status) {
//        this.email = emailAddress;
//        this.userID = userID;
//        this.profilePic = profilePic;
//        this.status = status;
//    }
//
//    public User() {
//
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userId) {
//        this.userID = userId;
//    }
//
//    public String getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(String profilePic) {
//        this.profilePic = profilePic;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsertype() {
//        return usertype;
//    }
//
//    public void setUsertype(String usertype) {
//        this.usertype = usertype;
//    }
//
//    public String getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public void setProfilePhoto(String profilePhoto) {
//        this.profilePhoto = profilePhoto;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}

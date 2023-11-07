package com.example.findmyclassmates.models;

public class Review {
    private String dept;
    private String courseID;
    private String one;
    private int two;
    private String three;
    private String four;
    private String five;
    private int upvotes;
    private int downvotes;
    private String user;
    private String uid;
    private String userId;

    public Review() {
        // Default constructor required for Firebase database
    }

    public Review(String dept, String courseID, String one, int two, String three, String four, String five, int upvotes, int downvotes, String user, String userId) {
        this.dept = dept;
        this.courseID = courseID;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.user = user;
        this.userId = userId;
    }

    public String getUserId() {return userId; };
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getFive() {
        return five;
    }

    public void setFive(String five) {
        this.five = five;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}


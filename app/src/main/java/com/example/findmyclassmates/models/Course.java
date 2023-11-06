package com.example.findmyclassmates.models;

public class Course {
    private String dept;
    private String courseID;
    private String name;
    private String prof;
    private String time;
    private int units;
    private String roster;
    private String reviews;

    public Course() {
        // Default constructor required for Firebase
    }

    public Course(String dept, String courseID, String name, String prof, String time, int units, String roster, String reviews) {
        this.dept = dept;
        this.courseID = courseID;
        this.name = name;
        this.prof = prof;
        this.time = time;
        this.units = units;
        this.roster = roster;
        this.reviews = reviews;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getRoster() {
        return roster;
    }

    public void setRoster(String roster) {
        this.roster = roster;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}

package com.example.findmyclassmates.models;

import java.util.Map;

public class DeptCourseID {
    private String deptCourseID;
    private Map<String, Student> students;

    public DeptCourseID() {
        // Required default constructor for Firebase
    }

    public String getDeptCourseID() {
        return deptCourseID;
    }

    public void setDeptCourseID(String deptCourseID) {
        this.deptCourseID = deptCourseID;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }
}

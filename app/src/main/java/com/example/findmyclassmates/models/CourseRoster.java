package com.example.findmyclassmates.models;

import java.util.Map;

public class CourseRoster {
    private Map<String, DeptCourseID> deptCourseIDs;

    public CourseRoster() {
        // Required default constructor for Firebase
    }

    public Map<String, DeptCourseID> getDeptCourseIDs() {
        return deptCourseIDs;
    }

    public void setDeptCourseIDs(Map<String, DeptCourseID> deptCourseIDs) {
        this.deptCourseIDs = deptCourseIDs;
    }
}


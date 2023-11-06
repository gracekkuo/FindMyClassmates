package com.example.findmyclassmates.activities.mainFeatures;

public class DepartmentItem {
    private String departmentName;
    private String[] subItems;

    public DepartmentItem(String departmentName, String[] subItems) {
        this.departmentName = departmentName;
        this.subItems = subItems;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String[] getSubItems() {
        return subItems;
    }

}


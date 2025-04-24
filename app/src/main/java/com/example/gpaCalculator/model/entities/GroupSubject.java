package com.example.gpaCalculator.model.entities;

public class GroupSubject {
    private String groupName;
    private String subjectName;

    public GroupSubject(String groupName, String subjectName) {
        this.groupName = groupName;
        this.subjectName = subjectName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
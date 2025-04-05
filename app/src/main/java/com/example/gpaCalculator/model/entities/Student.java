package com.example.gpaCalculator.model.entities;
public class Student extends User {
    private String studentId;
    private String university;
    private String faculty;
    private int groupId;

    // Constructor
    public Student(int id, String username, String email, String passwordHash, String phone, String signupDate,
                   int enrollmentId, String studentId, String university, String faculty, int groupId) {
        super(id, username, email, passwordHash, phone, signupDate, enrollmentId);
        this.studentId = studentId;
        this.university = university;
        this.faculty = faculty;
        this.groupId = groupId;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
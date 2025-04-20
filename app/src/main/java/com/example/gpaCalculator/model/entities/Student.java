package com.example.gpaCalculator.model.entities;

public class Student extends User {
    private String studentId; // Unique Student ID
    private String firstName;
    private String lastName;
    private String birthdate; // Birthdate as String
    private String gender; // "Male" or "Female"
    private String university;
    private String faculty;
    private String groupId; // Foreign Key to Groups Table (as String)

    // Constructor
    public Student(String id, String username, String email, String passwordHash, String phone, String signupDate,String roleType,
                   String studentId, String firstName, String lastName, String birthdate, String gender,
                   String university, String faculty, String groupId) {

        super(id, username, email, passwordHash, phone, signupDate,roleType); // Call to superclass constructor
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", groupId='" + groupId + '\'' +
                "} " + super.toString();
    }
}
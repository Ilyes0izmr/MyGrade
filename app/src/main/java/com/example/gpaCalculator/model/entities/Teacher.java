package com.example.gpaCalculator.model.entities;

public class Teacher extends User {
    private String professorId; // Unique Professor ID
    private String firstName;
    private String lastName;
    private String birthdate; // Birthdate as String
    private String gender; // "Male" or "Female"
    private String hoursPerWeek; // Hours per week as String

    // Constructor
    public Teacher(String id, String username, String email, String passwordHash, String phone, String signupDate,String roleType
                   ,String professorId, String firstName, String lastName, String birthdate, String gender, String hoursPerWeek) {
        super(id, username, email, passwordHash, phone, signupDate , roleType); // Call to superclass constructor
        this.professorId = professorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.hoursPerWeek = hoursPerWeek;
    }

    // Getters and Setters
    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
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

    public String getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(String hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "professorId='" + professorId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", hoursPerWeek='" + hoursPerWeek + '\'' +
                "} " + super.toString();
    }
}
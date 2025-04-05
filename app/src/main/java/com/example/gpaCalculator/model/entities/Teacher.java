package com.example.gpaCalculator.model.entities;
public class Teacher extends User {
    private String professorId;
    private int hoursPerWeek;

    // Constructor
    public Teacher(int id, String username, String email, String passwordHash, String phone, String signupDate,
                   int enrollmentId, String professorId, int hoursPerWeek) {
        super(id, username, email, passwordHash, phone, signupDate, enrollmentId);
        this.professorId = professorId;
        this.hoursPerWeek = hoursPerWeek;
    }

    // Getters and Setters
    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
}
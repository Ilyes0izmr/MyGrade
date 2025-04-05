package com.example.gpaCalculator.model.entities;


public class Admin extends User {
    private String adminUnivId;

    // Constructor
    public Admin(int id, String username, String email, String passwordHash, String phone, String signupDate,
                 int enrollmentId, String adminUnivId) {
        super(id, username, email, passwordHash, phone, signupDate, enrollmentId);
        this.adminUnivId = adminUnivId;
    }

    // Getters and Setters
    public String getAdminUnivId() {
        return adminUnivId;
    }

    public void setAdminUnivId(String adminUnivId) {
        this.adminUnivId = adminUnivId;
    }
}
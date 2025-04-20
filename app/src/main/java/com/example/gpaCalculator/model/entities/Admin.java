package com.example.gpaCalculator.model.entities;

public class Admin extends User {
    private String adminUnivId; // University-specific Admin ID

    // Constructor
    public Admin(String id, String username, String email, String passwordHash, String phone, String signupDate,String roleType,
                 String adminUnivId) {
        super(id, username, email, passwordHash, phone, signupDate,roleType); // Call to superclass constructor
        this.adminUnivId = adminUnivId;
    }

    // Getters and Setters
    public String getAdminUnivId() {
        return adminUnivId;
    }

    public void setAdminUnivId(String adminUnivId) {
        this.adminUnivId = adminUnivId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminUnivId='" + adminUnivId + '\'' +
                "} " + super.toString();
    }
}
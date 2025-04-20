package com.example.gpaCalculator.model.entities;

public class User {
    private String id; // Primary Key (as String)
    private String username;
    private String email;
    private String passwordHash; // Password hash
    private String phone;
    private String signupDate; // Signup date as String
    private String roleType; // Add this field

    // Constructor
    public User(String id, String username, String email, String passwordHash, String phone, String signupDate,String roleType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.signupDate = signupDate;
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSignupDate() {
        return signupDate;
    }
    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", phone='" + phone + '\'' +
                ", signupDate='" + signupDate + '\'' +
                '}';
    }
}
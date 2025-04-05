package com.example.gpaCalculator.model.entities;

public class Group {
    private int id;
    private String name;
    private String level;
    private String specialty;
    private String department;

    // Constructor
    public Group(int id, String name, String level, String specialty, String department) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.specialty = specialty;
        this.department = department;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
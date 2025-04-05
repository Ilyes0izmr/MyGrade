package com.example.gpaCalculator.controller;

import android.content.Context;
import com.example.gpaCalculator.model.entities.User;
import com.example.gpaCalculator.model.entities.Student;
import com.example.gpaCalculator.model.entities.Teacher;
import com.example.gpaCalculator.model.entities.Admin;


public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void startSession(User user) {
        this.currentUser = user;
    }

    public void endSession() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUserRole() {
        if (currentUser instanceof Student) {
            return "Student";
        } else if (currentUser instanceof Teacher) {
            return "Teacher";
        } else if (currentUser instanceof Admin) {
            return "Admin";
        } else {
            return "Unknown";
        }
    }
}
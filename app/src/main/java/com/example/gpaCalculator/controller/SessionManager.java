package com.example.gpaCalculator.controller;

import com.example.gpaCalculator.model.entities.User;

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

    /**
     * Starts a session by assigning the current user.
     *
     * @param user The logged-in user.
     */
    public void startSession(User user) {
        this.currentUser = user;
    }

    /**
     * Ends the session by clearing the current user.
     */
    public void endSession() {
        this.currentUser = null;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The current user, or null if no user is logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Determines the role of the currently logged-in user.
     *
     * @return The role of the user (e.g., "student", "teacher", "admin"), or "Unknown" if no user is logged in.
     */
    public String getUserRole() {
        if (currentUser != null) {
            return currentUser.getRoleType(); // Use the role_type from the User object
        }
        return "Unknown";
    }

    /**
     * Retrieves the ID of the currently logged-in user.
     *
     * @return The user ID, or -1 if no user is logged in.
     */
    public String getUserId() {
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }
}
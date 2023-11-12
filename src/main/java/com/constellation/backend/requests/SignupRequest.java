package com.constellation.backend.requests;

import com.constellation.backend.userService.User;

public class SignupRequest {
    private User user;
    private String password;

    // No-arg constructor
    public SignupRequest() {
    }

    // Constructor with all fields
    public SignupRequest(User user, String password) {
        this.user = user;
        this.password = password;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
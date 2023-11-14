package com.constellation.backend.requests;

public class ForgotPasswordRequest {
    private String username;
    private String password;

    public ForgotPasswordRequest() {
        this.username = "";
        this.password = "";
    }

    public ForgotPasswordRequest(String username, String email) {
        super();
        this.username = username;
        this.password = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String email) {
        this.password = email;
    }
}

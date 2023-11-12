package com.constellation.backend.userService;

public class UsernamePasswordMismatchException extends Exception {

    public UsernamePasswordMismatchException(String message) {
        super(message);
    }
}


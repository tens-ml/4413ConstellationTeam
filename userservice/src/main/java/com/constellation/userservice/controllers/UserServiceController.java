package com.constellation.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {
    @GetMapping("/")
    public String get() {
        return "Hello world!";
    }
}

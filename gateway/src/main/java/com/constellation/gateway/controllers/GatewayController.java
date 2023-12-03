package com.constellation.gateway.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
    @PostMapping("/users/login")
    public ResponseEntity<?> gateway() {
        System.out.println("pgwy2");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public String postGateway() {
        System.out.println("pgwy");
        return "post";
    }
}
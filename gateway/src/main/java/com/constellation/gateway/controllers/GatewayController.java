package com.constellation.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
    @GetMapping("/")
    public String gateway() {
        return "Hello from Gateway";
    }
}

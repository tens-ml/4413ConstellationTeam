package com.constellation.bidservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidServiceController {
    @GetMapping("/")
    public String get() {
        return "Hello world!";
    }
}

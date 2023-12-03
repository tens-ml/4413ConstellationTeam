package com.constellation.paymentservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentServiceController {
    @GetMapping("/")
    public String get() {
        return "Hello world!";
    }
}

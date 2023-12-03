package com.constellation.catalogservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogServiceController {
    @GetMapping("/")
    public String get() {
        return "Hello world!";
    }
}

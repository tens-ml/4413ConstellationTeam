package com.constellation.gateway.controllers;

import com.constellation.gateway.requests.ChangePasswordRequest;
import com.constellation.gateway.requests.LoginRequest;
import com.constellation.gateway.requests.SellItemRequest;
import com.constellation.gateway.requests.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GatewayController {
    private final RestTemplate restTemplate = new RestTemplate();
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> gateway(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(System.getenv("USERSERVICE_URL") + "/login", loginRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(System.getenv("USERSERVICE_URL") + "/", signupRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists with this username");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(System.getenv("USERSERVICE_URL") + "/" + username, HttpMethod.PUT, new HttpEntity<>(changePasswordRequest), String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find user");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/items")
    public ResponseEntity<String> getItems(@RequestParam(required = false) String search) {
        try {
            String url = System.getenv("CATALOGSERVICE_URL") + "/";
            if (search != null && !search.trim().isEmpty()) {
                url += "?search=" + search;
            }
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find items");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/items")
    public ResponseEntity<String> sellItem(@RequestBody SellItemRequest sellItemRequest) {
        System.out.println(sellItemRequest.getAuctionEnd());
        try {
            System.out.println("tries : " + System.getenv("CATALOGSERVICE_URL"));
            ResponseEntity<String> response = restTemplate.postForEntity(System.getenv("CATALOGSERVICE_URL") + "/" , sellItemRequest, String.class);
            System.out.println("response : " + response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't sell item");
        }
    }

}
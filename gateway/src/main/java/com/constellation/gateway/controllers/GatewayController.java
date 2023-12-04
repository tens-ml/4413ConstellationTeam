package com.constellation.gateway.controllers;

import com.constellation.gateway.requests.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    Dotenv dotenv = Dotenv.configure().load();

    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> gateway(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(dotenv.get("USERSERVICE_URL") + "/login", loginRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }

    @PostMapping("/")
    public String postGateway() {
        System.out.println("pgwy");;
        return "post";
    }
}
package com.constellation.gateway.controllers;

import com.constellation.gateway.requests.*;
import com.constellation.gateway.responses.EnrichedItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.json.JSONArray;
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

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> gateway(@RequestBody LoginRequest loginRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    System.getenv("USERSERVICE_URL") + "/login", loginRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    System.getenv("USERSERVICE_URL") + "/", signupRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists with this username");
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    System.getenv("USERSERVICE_URL") + "/" + username, HttpMethod.PUT,
                    new HttpEntity<>(changePasswordRequest), String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value="/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getItem(@PathVariable Integer id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    System.getenv("CATALOGSERVICE_URL") + "/" + id, String.class);
            JSONObject item = new JSONObject(response.getBody());
            enrichItem(item);
            return ResponseEntity.ok(item.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find item");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/items")
    public ResponseEntity<String> getItems(@RequestParam(required = false) String search) {
        try {
            String url = System.getenv("CATALOGSERVICE_URL") + "/";
            if (search != null && !search.trim().isEmpty()) {
                url += "?search=" + search;
            }
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JSONArray items = new JSONArray(response.getBody());
            enrichItems(items);

            return ResponseEntity.ok(items.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find items");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/items")
    public ResponseEntity<String> sellItem(@RequestBody SellItemRequest sellItemRequest) {
        System.out.println(sellItemRequest.getAuctionEnd());
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    System.getenv("CATALOGSERVICE_URL") + "/" , sellItemRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't sell item");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/bids")
    private ResponseEntity<String> placeBid(@RequestBody BidRequest bidRequest) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    System.getenv("BIDSERVICE_URL"), bidRequest, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't place bid");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/payments")
    private ResponseEntity<Integer> makePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            ResponseEntity<Integer> response = restTemplate.postForEntity(
                    System.getenv("PAYMENTSERVICE_URL"), paymentRequest, Integer.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't make payment");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/payments/{id}")
    private ResponseEntity<String> getPayment(@PathVariable Integer id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    System.getenv("PAYMENTSERVICE_URL") + "/" + id, String.class);

            JSONObject payment = new JSONObject(response.getBody());
            enrichPayment(payment);
            return ResponseEntity.ok(payment.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find payment");
        }
    }
    private void enrichItems(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            enrichItem(item);
        }
    }

    private void enrichItem(JSONObject item) {
        try {
            ResponseEntity<String> bidResponse = restTemplate.getForEntity(
                    System.getenv("BIDSERVICE_URL") + "/highest/" + item.getInt("id"), String.class);
            item.put("highestBid", new JSONObject(bidResponse.getBody()).getBigDecimal("price"));
            item.put("highestBidderId", new JSONObject(bidResponse.getBody()).getInt("userId"));
        } catch (Exception e) {
            item.put("highestBid", -1);
        }
    }

   // enrich payment with user and item details
   private void enrichPayment(JSONObject payment) {
       try {
           ResponseEntity<String> userResponse = restTemplate.getForEntity(
                   System.getenv("USERSERVICE_URL") + "/" + payment.getInt("userId"), String.class);
           payment.put("user", new JSONObject(userResponse.getBody()));
       } catch (Exception e) {
           payment.put("user", new JSONObject());
       }
       try {
           ResponseEntity<String> itemResponse = restTemplate.getForEntity(
                   System.getenv("CATALOGSERVICE_URL") + "/" + payment.getInt("itemId"), String.class);
           payment.put("item", new JSONObject(itemResponse.getBody()));
       } catch (Exception e) {
           payment.put("item", new JSONObject());
       }
   }
}
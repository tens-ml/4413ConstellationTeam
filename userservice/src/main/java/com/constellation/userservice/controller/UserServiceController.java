package com.constellation.userservice.controller;

import com.constellation.userservice.model.User;
import com.constellation.userservice.repository.UserRepository;
import com.constellation.userservice.requests.ChangePasswordRequest;
import com.constellation.userservice.requests.LoginRequest;
import com.constellation.userservice.requests.SignupRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class UserServiceController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody SignupRequest signupRequest) {
        String encryptedPassword = encrypt(signupRequest.getPassword());
        User user = User.fromSignupRequest(signupRequest, encryptedPassword);
        try {
            User createdUser = userRepository.save(user);
            return ResponseEntity.ok().body(createdUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @PutMapping("/")
    public ResponseEntity<User> update(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String encryptedNewPassword = encrypt(changePasswordRequest.getNewPassword());

        // Right now we just use update for "forgot-password" functionality
        Optional<User> userOpt = userRepository.findByUsername(changePasswordRequest.getUsername());
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldnt find user");
        }

        User user = userOpt.get();
        user.setPassword(encryptedNewPassword);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @GetMapping("/{id}")
    public User read(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login")
    public User authenticateUser(@RequestBody LoginRequest loginRequest) {
        String encryptedPassword = encrypt(loginRequest.getPassword());
        Optional<User> userOpt = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), encryptedPassword);

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }

        User user = userOpt.get();
        user.setPassword(null);
        return user;
    }

    private static String encrypt(String input) {
        try {
            return toHexString(getSHA(input));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    /**
     * Converts an input string into an array of bytes using SHA-512 encryption
     *
     * @param input is the input string that is to be converted into byte
     * @return the byte form of the input string
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(input.getBytes(StandardCharsets.UTF_8)); // convert the input string into bytes and SHA-512
    }

    /**
     * Converts an input array of bytes and then converts it to a string
     *
     * @param hash is the array of input bytes
     * @return the encrypted string
     */
    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}


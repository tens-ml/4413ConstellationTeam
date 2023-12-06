package com.constellation.userservice.model;

import com.constellation.userservice.requests.SignupRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table

public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique=true, nullable = false)
    private String username;

    private String password;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
    private String country;

    // From method that will take a SignupRequest object and return a User object
    public static User fromSignupRequest(SignupRequest signupRequest, String password) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(password);
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setStreetAddress(signupRequest.getStreetAddress());
        user.setPostalCode(signupRequest.getPostalCode());
        user.setProvince(signupRequest.getProvince());
        user.setCity(signupRequest.getCity());
        user.setCountry(signupRequest.getCountry());
        return user;
    }
}
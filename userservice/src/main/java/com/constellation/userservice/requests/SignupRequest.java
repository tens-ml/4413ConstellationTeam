package com.constellation.userservice.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private String province;
}

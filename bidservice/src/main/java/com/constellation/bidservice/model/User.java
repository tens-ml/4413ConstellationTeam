package com.constellation.bidservice.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private String country;
}
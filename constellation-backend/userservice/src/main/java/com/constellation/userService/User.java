package com.constellation.userService;

public class User {
    private String username, firstName, lastName, streetAddress, streetNumber, postalCode, city, country;
    private int id;

    /**
     * Gets the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the street address of the user.
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the street address of the user.
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets the street number of the user.
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the street number of the user.
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Gets the postal code of the user.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the user.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country of the user.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the user.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the id of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }
}

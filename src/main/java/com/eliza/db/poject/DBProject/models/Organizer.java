package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Organizer {

    private int id;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String name;

    @NotEmpty(message = "The address must not be empty")
    @Size(min = 2, max = 200, message = "The address must be between 2 and 200 characters long")
    private String address;

    @NotEmpty(message = "The email must not be empty")
    @Email(message = "The email must be a valid email address")
    @Size(min = 2, max = 100, message = "The email must be between 2 and 100 characters long")
    private String email;

    public Organizer(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Organizer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

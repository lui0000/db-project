package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Creator {

    private int id;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String name;

    @NotEmpty(message = "The email must not be empty")
    @Email(message = "The email must be a valid email address")
    @Size(min = 2, max = 100, message = "The email must be between 2 and 100 characters long")
    private String email;

    @NotEmpty(message = "The country must not be empty")
    @Size(min = 2, max = 50, message = "The country must be between 2 and 100 characters long")
    private String country;

    @Min(value = 16, message = "The creator cannot be under 16")
    private int age;

    private int numberOfWorks;

    public Creator(String name, String email, String country, int age, int numberOfWorks) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.age = age;
        this.numberOfWorks = numberOfWorks;
    }

    public Creator() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public  String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumberOfWorks() {
        return numberOfWorks;
    }

    public void setNumberOfWorks(int numberOfWorks) {
        this.numberOfWorks = numberOfWorks;
    }
}

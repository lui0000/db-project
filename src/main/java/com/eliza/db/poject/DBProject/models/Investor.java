package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Investor {

    private int id;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String name;

    @NotEmpty(message = "The email must not be empty")
    @Email(message = "The email must be a valid email address")
    @Size(min = 2, max = 100, message = "The email must be between 2 and 100 characters long")
    private String email;


    private int investmentAmount;

    public Investor(String name, String email, int investmentAmount) {
        this.name = name;
        this.email = email;
        this.investmentAmount = investmentAmount;
    }

    public Investor() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(int investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
}

package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Staff {

    private int staffId;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters long")
    private String name;

    private int phoneNumber;

    @NotEmpty(message = "The role must not be empty")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters long")
    private String role;

    private int organizerId;

    public Staff(String name, int phoneNumber, String role, int organizerId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.organizerId=organizerId;
    }

    public Staff() {
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }
}

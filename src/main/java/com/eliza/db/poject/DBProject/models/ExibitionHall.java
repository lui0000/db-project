package com.eliza.db.poject.DBProject.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class ExibitionHall {

    private int id;
    private int serialNumber;
    private int capacity;

    public ExibitionHall(int serialNumber, int capacity) {
        this.serialNumber = serialNumber;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

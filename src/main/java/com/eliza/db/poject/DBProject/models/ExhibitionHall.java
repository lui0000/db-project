package com.eliza.db.poject.DBProject.models;

public class ExhibitionHall {

    private int id;
    private int serialNumber;
    private int capacity;
    private int organizerId;

    public ExhibitionHall(int serialNumber, int capacity, int organizerId) {
        this.serialNumber = serialNumber;
        this.capacity = capacity;
        this.organizerId = organizerId;
    }

    public ExhibitionHall() {
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

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;

    }
}

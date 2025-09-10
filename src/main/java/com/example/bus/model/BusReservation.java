package com.example.bus.model;

public class BusReservation {
    private int id;
    private int tripId;
    private String name;
    private String phone;
    private String publicCode;

    public BusReservation(int id, int tripId, String name, String phone, String publicCode) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.phone = phone;
        this.publicCode = publicCode;
    }

    public int getId() { return id; }
    public int getTripId() { return tripId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getPublicCode() { return publicCode; }
}
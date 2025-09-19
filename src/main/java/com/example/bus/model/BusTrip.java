package com.example.bus.model;

import java.time.LocalDateTime;

public class BusTrip {
    private int id;
    private int companyId;
    private String fromArea;
    private String toArea;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime limitTime;
    private int capacity;
    private int availableSeats;

    public BusTrip() {}

    public BusTrip(int id, int companyId, String fromArea, String toArea,
                   LocalDateTime departureTime, LocalDateTime arrivalTime,
                   LocalDateTime limitTime, int capacity) {
        this.id = id;
        this.companyId = companyId;
        this.fromArea = fromArea;
        this.toArea = toArea;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.limitTime = limitTime;
        this.capacity = capacity;
        this.availableSeats = capacity;
    }

    public int getId() { return id; }
    public int getCompanyId() { return companyId; }
    public String getFromArea() { return fromArea; }
    public String getToArea() { return toArea; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public LocalDateTime getLimitTime() { return limitTime; }
    public int getCapacity() { return capacity; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public String getType() {
        int hour = departureTime.getHour();
        if (hour >= 18 || hour <= 2) {
            return "夜行";
        } else {
            return "昼行";
        }
    }


    public void reserveSeat() {
        if (availableSeats > 0) availableSeats--;
    }

    public void cancelSeat() {
        if (availableSeats < capacity) availableSeats++;
    }
}

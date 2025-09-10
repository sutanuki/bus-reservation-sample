package com.example.bus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BusTripDto {
    private int id;
    private String companyName;
    private String fromArea;
    private String toArea;
    private String departureTime;
    private String arrivalTime;
    private String type;
    private int availableSeats;
    private int capacity;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public BusTripDto(BusTrip trip, String companyName) {
        this.id = trip.getId();
        this.companyName = companyName;
        this.fromArea = trip.getFromArea();
        this.toArea = trip.getToArea();
        this.departureTime = format(trip.getDepartureTime());
        this.arrivalTime = format(trip.getArrivalTime());
        this.type = trip.getType();
        this.availableSeats = trip.getAvailableSeats();
        this.capacity = trip.getCapacity();
    }

    private String format(LocalDateTime dt) {
        return (dt != null) ? dt.format(FORMATTER) : "";
    }

    public String getCompanyName() { return companyName; }
    public int getId() { return id; }
    public String getFromArea() { return fromArea; }
    public String getToArea() { return toArea; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getType() { return type; }
    public int getAvailableSeats() { return availableSeats; }
    public int getCapacity() { return capacity; }
}

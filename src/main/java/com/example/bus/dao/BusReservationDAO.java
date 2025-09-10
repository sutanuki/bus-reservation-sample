package com.example.bus.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.bus.model.BusReservation;
import com.example.bus.model.BusTrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BusReservationDAO {
    private static final String DATA_FILE = "data/reservations.json";

    private static Map<Integer, BusReservation> reservations = new HashMap<>();
    private static final AtomicInteger sequence = new AtomicInteger(1);

    private final BusTripDAO tripDAO = new BusTripDAO();
    private final Gson gson = new Gson();

    public BusReservationDAO() {
        load();
    }

    public synchronized BusReservation add(int tripId, String name, String phone) {
        int id = sequence.getAndIncrement();
        String publicCode = UUID.randomUUID().toString().substring(0, 8);
        BusReservation r = new BusReservation(id, tripId, name, phone, publicCode);
        reservations.put(id, r);

        save();
        return r;
    }

    public Optional<BusReservation> findById(int id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public Optional<BusReservation> findByPublicCode(String code) {
        return reservations.values().stream()
                .filter(r -> r.getPublicCode().equals(code))
                .findFirst();
    }

    public List<BusReservation> findByTripId(int tripId) {
        return reservations.values().stream()
                .filter(r -> r.getTripId() == tripId)
                .toList();
    }

    public List<BusReservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public synchronized boolean cancel(String code, String phone) {
        Optional<BusReservation> match = reservations.values().stream()
                .filter(r -> r.getPublicCode().equals(code) && r.getPhone().equals(phone))
                .findFirst();

        if (match.isPresent()) {
            BusReservation r = match.get();
            reservations.remove(r.getId());

            save();
            return true;
        }
        return false;
    }

    public synchronized void delete(int id) {
        BusReservation r = reservations.remove(id);
        if (r != null) {
            tripDAO.findById(r.getTripId()).ifPresent(BusTrip::cancelSeat);
        }
        save();
    }

    private void save() {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs(); // dataディレクトリ作成
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(reservations, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) return;

            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<Integer, BusReservation>>() {}.getType();
                reservations = gson.fromJson(reader, type);
                if (reservations == null) reservations = new HashMap<>();

                int maxId = reservations.keySet().stream().max(Integer::compareTo).orElse(0);
                sequence.set(maxId + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

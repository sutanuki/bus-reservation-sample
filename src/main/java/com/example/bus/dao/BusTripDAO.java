package com.example.bus.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.example.bus.model.BusTrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BusTripDAO {
    private static final Map<Integer, BusTrip> trips = new HashMap<>();
    private static final AtomicInteger sequence = new AtomicInteger(1);

    private static final Path STORAGE_FILE = Paths.get("data/trips.json");

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (com.google.gson.JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            LocalDateTime.parse(json.getAsString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (com.google.gson.JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                            ctx.serialize(src.toString()))
            .setPrettyPrinting()
            .create();

    static {
        load();
    }

    public BusTrip add(int companyId, String fromArea, String toArea,
                       LocalDateTime departure, LocalDateTime arrival,
                       LocalDateTime limit, int capacity) {
        int id = sequence.getAndIncrement();
        BusTrip trip = new BusTrip(id, companyId, fromArea, toArea, departure, arrival, limit, capacity);
        trips.put(id, trip);
        save();
        return trip;
    }

    public Optional<BusTrip> findById(int id) {
        return Optional.ofNullable(trips.get(id));
    }

    public List<BusTrip> findAll() {
        return new ArrayList<>(trips.values());
    }

    public List<BusTrip> findByCompanyId(int companyId) {
        return trips.values().stream()
                .filter(t -> t.getCompanyId() == companyId)
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        trips.remove(id);
        save();
    }

    public List<BusTrip> search(String fromArea, String toArea,
                                LocalDateTime date, String type) {
        return trips.values().stream()
                .filter(t -> (fromArea == null || t.getFromArea().equalsIgnoreCase(fromArea)))
                .filter(t -> (toArea == null || t.getToArea().equalsIgnoreCase(toArea)))
                .filter(t -> (date == null || t.getDepartureTime().toLocalDate().equals(date.toLocalDate())))
                .filter(t -> (type == null || t.getType().equals(type)))
                .collect(Collectors.toList());
    }

    private static void save() {
        try {
            Files.createDirectories(STORAGE_FILE.getParent());
            try (Writer w = Files.newBufferedWriter(STORAGE_FILE)) {
                gson.toJson(trips.values(), w);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        if (Files.exists(STORAGE_FILE)) {
            try (Reader r = Files.newBufferedReader(STORAGE_FILE)) {
                Type listType = new TypeToken<List<BusTrip>>() {}.getType();
                List<BusTrip> loaded = gson.fromJson(r, listType);
                if (loaded != null) {
                    for (BusTrip t : loaded) {
                        trips.put(t.getId(), t);
                        sequence.set(Math.max(sequence.get(), t.getId() + 1));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

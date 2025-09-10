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
import java.util.concurrent.atomic.AtomicInteger;

import com.example.bus.model.BusCompany;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BusCompanyDAO {
    private static final String DATA_FILE = "data/companies.json";

    private static Map<Integer, BusCompany> companies = new HashMap<>();
    private static final AtomicInteger sequence = new AtomicInteger(1);

    private final Gson gson = new Gson();

    public BusCompanyDAO() {
        load();
    }

    public synchronized BusCompany create(String companyName, String loginId, String password) {
        int id = sequence.getAndIncrement();
        BusCompany c = new BusCompany(id, companyName, loginId, password);
        companies.put(id, c);
        save();
        return c;
    }

    public Optional<BusCompany> findByLoginId(String loginId) {
        return companies.values().stream()
                .filter(c -> c.getLoginId().equals(loginId))
                .findFirst();
    }

    public Optional<BusCompany> findById(int id) {
        return Optional.ofNullable(companies.get(id));
    }

    public Optional<BusCompany> findByLogin(String loginId, String password) {
        return companies.values().stream()
                .filter(c -> c.getLoginId().equals(loginId) && c.getPassword().equals(password))
                .findFirst();
    }

    public List<BusCompany> findAll() {
        return new ArrayList<>(companies.values());
    }

    public synchronized void delete(int id) {
        companies.remove(id);
        save();
    }

    private void save() {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(companies, writer);
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
                Type type = new TypeToken<Map<Integer, BusCompany>>() {}.getType();
                companies = gson.fromJson(reader, type);
                if (companies == null) companies = new HashMap<>();

                int maxId = companies.keySet().stream().max(Integer::compareTo).orElse(0);
                sequence.set(maxId + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

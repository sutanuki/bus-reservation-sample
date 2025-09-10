package com.example.bus.model;

public class BusCompany {
    private int id;
    private String companyName;
    private String loginId;
    private String password;

    public BusCompany(int id, String companyName, String loginId, String password) {
        this.id = id;
        this.companyName = companyName;
        this.loginId = loginId;
        this.password = password;
    }

    public int getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getLoginId() { return loginId; }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    public void setPassword(String password) { this.password = password; }
}
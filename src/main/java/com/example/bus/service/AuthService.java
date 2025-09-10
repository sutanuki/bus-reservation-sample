package com.example.bus.service;

import java.util.Optional;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.model.BusCompany;
import com.example.bus.model.UserRole;

import jakarta.servlet.http.HttpSession;

public class AuthService {
    private final BusCompanyDAO companyDAO;

    public AuthService(BusCompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public boolean loginCompany(HttpSession session, String loginId, String password) {
        Optional<BusCompany> c = companyDAO.findByLoginId(loginId);
        if (c.isPresent() && c.get().getPassword().equals(password)) {
            session.setAttribute("role", UserRole.COMPANY);
            session.setAttribute("companyId", c.get().getId());
            session.setAttribute("companyName", c.get().getCompanyName());
            return true;
        }
        return false;
    }



    public boolean loginAdmin(HttpSession session, String loginId, String password) {
        if ("admin".equals(loginId) && "admin".equals(password)) {
            session.setAttribute("role", UserRole.ADMIN);
            return true;
        }
        return false;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public static UserRole role(HttpSession session) {
        Object r = session.getAttribute("role");
        return r instanceof UserRole ? (UserRole) r : UserRole.GUEST;
    }
}
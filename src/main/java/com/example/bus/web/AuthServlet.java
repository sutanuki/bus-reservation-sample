package com.example.bus.web;

import java.io.IOException;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.service.AuthService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth/login", "/auth/logout"})
public class AuthServlet extends HttpServlet {
    private BusCompanyDAO companyDAO;
    private AuthService authService;

    @Override public void init() {
        ServletContext app = getServletContext();
        synchronized (app) {
            companyDAO = (BusCompanyDAO) app.getAttribute("companyDAO");
            if (companyDAO == null) { companyDAO = new BusCompanyDAO(); app.setAttribute("companyDAO", companyDAO); }
        }
        authService = new AuthService(companyDAO);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/auth/login".equals(path)) {
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        } else if ("/auth/logout".equals(path)) {
            authService.logout(req.getSession());
            resp.sendRedirect(req.getContextPath() + "/top");
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String target = req.getParameter("target");
        String id = req.getParameter("loginId");
        String pw = req.getParameter("password");
        boolean ok = false;
        if ("company".equals(target)) ok = authService.loginCompany(req.getSession(), id, pw);
        else if ("admin".equals(target)) ok = authService.loginAdmin(req.getSession(), id, pw);

        if (ok) {
            if ("company".equals(target)) resp.sendRedirect(req.getContextPath() + "/company");
            else resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            req.setAttribute("error", "ログインに失敗しました");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }
}
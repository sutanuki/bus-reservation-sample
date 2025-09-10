package com.example.bus.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.dao.BusReservationDAO;
import com.example.bus.dao.BusTripDAO;
import com.example.bus.model.BusCompany;
import com.example.bus.model.BusReservation;
import com.example.bus.model.BusTripDto;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BusCompanyDAO companyDAO;
    private BusTripDAO tripDAO;
    private BusReservationDAO reservationDAO;

    @Override
    public void init() {
        ServletContext app = getServletContext();
        synchronized (app) {
            companyDAO = (BusCompanyDAO) app.getAttribute("companyDAO");
            if (companyDAO == null) {
                companyDAO = new BusCompanyDAO();
                app.setAttribute("companyDAO", companyDAO);
            }
            tripDAO = (BusTripDAO) app.getAttribute("tripDAO");
            if (tripDAO == null) {
                tripDAO = new BusTripDAO();
                app.setAttribute("tripDAO", tripDAO);
            }
            reservationDAO = (BusReservationDAO) app.getAttribute("reservationDAO");
            if (reservationDAO == null) {
                reservationDAO = new BusReservationDAO();
                app.setAttribute("reservationDAO", reservationDAO);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BusCompany> companies = companyDAO.findAll();
        List<BusTripDto> trips = tripDAO.findAll().stream()
                .filter(t -> t.getDepartureTime().isAfter(LocalDateTime.now()))
                .map(trip -> {
                    String companyName = companyDAO.findById(trip.getCompanyId())
                            .map(BusCompany::getCompanyName)
                            .orElse("不明");
                    return new BusTripDto(trip, companyName);
                })
                .collect(Collectors.toList());

        List<BusReservation> reservations = reservationDAO.findAll();

        req.setAttribute("companies", companies);
        req.setAttribute("trips", trips);
        req.setAttribute("reservations", reservations);

        req.getRequestDispatcher("/admin/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "createCompany" -> {
                String name = req.getParameter("companyName");
                String loginId = req.getParameter("loginId");
                String password = req.getParameter("password");
                companyDAO.create(name, loginId, password);
            }
            case "deleteCompany" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                companyDAO.delete(id);
            }
            case "deleteTrip" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                tripDAO.delete(id);
            }
            case "cancelReservation" -> {
                int id = Integer.parseInt(req.getParameter("id"));
                reservationDAO.delete(id);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}

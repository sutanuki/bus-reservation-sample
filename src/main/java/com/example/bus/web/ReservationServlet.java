package com.example.bus.web;

import java.io.IOException;

import com.example.bus.dao.BusReservationDAO;
import com.example.bus.dao.BusTripDAO;
import com.example.bus.model.BusReservation;
import com.example.bus.model.BusTrip;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    private BusTripDAO tripDAO;
    private BusReservationDAO reservationDAO;

    @Override
    public void init() {
        ServletContext app = getServletContext();
        synchronized (app) {
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
        int tripId = Integer.parseInt(req.getParameter("tripId"));
        BusTrip trip = tripDAO.findById(tripId).orElse(null);

        if (trip == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "指定された便が見つかりません");
            return;
        }

        req.setAttribute("trip", trip);
        req.getRequestDispatcher("/reservation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tripId = Integer.parseInt(req.getParameter("tripId"));
        BusTrip trip = tripDAO.findById(tripId).orElse(null);

        if (trip == null || trip.getAvailableSeats() <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "予約できません");
            return;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");

        trip.reserveSeat();

        BusReservation reservation = reservationDAO.add(tripId, name, phone);

        req.setAttribute("message", "予約が完了しました。予約番号: " + reservation.getPublicCode());
        req.getRequestDispatcher("/completed.jsp").forward(req, resp);
    }
}
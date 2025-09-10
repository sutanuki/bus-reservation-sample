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

@WebServlet("/cancel")
public class CancelServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/cancel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("reservationCode");
        String phone = req.getParameter("phone");

        BusReservation reservation = reservationDAO.findByPublicCode(code).orElse(null);

        if (reservation == null || !reservation.getPhone().equals(phone)) {
            req.setAttribute("error", "予約番号または電話番号が正しくありません");
            req.getRequestDispatcher("/cancel.jsp").forward(req, resp);
            return;
        }

        BusTrip trip = tripDAO.findById(reservation.getTripId()).orElse(null);
        if (trip != null) {
            trip.cancelSeat();
        }

        reservationDAO.delete(reservation.getId());

        req.setAttribute("message", "予約をキャンセルしました (予約番号: " + code + ")");
        req.getRequestDispatcher("/completed.jsp").forward(req, resp);
    }
}

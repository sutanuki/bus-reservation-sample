package com.example.bus.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.dao.BusTripDAO;
import com.example.bus.model.BusTripDto;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/top")
public class TopServlet extends HttpServlet {
    private BusTripDAO tripDAO;
    private BusCompanyDAO companyDAO;

    @Override
    public void init() {
        ServletContext app = getServletContext();
        synchronized (app) {
            tripDAO = (BusTripDAO) app.getAttribute("tripDAO");
            if (tripDAO == null) {
                tripDAO = new BusTripDAO();
                app.setAttribute("tripDAO", tripDAO);
            }
            companyDAO = (BusCompanyDAO) app.getAttribute("companyDAO");
            if (companyDAO == null) {
                companyDAO = new BusCompanyDAO();
                app.setAttribute("companyDAO", companyDAO);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<BusTripDto> fewSeatsTrips = tripDAO.findAll().stream()
                .filter(t -> t.getAvailableSeats() > 0 && t.getAvailableSeats() <= 10)
                .filter(t -> t.getLimitTime().isAfter(LocalDateTime.now()))
                .map(trip -> {
                    String companyName = companyDAO.findById(trip.getCompanyId())
                            .map(c -> c.getCompanyName())
                            .orElse("不明");
                    return new BusTripDto(trip, companyName);
                })
                .collect(Collectors.toList());

        req.setAttribute("fewSeatsTrips", fewSeatsTrips);
        req.getRequestDispatcher("/top.jsp").forward(req, resp);
    }
}

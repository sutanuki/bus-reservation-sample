package com.example.bus.web;

import java.io.IOException;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.dao.BusTripDAO;
import com.example.bus.model.BusTrip;
import com.example.bus.model.BusTripDto;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("tripId");
        if (idStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "便IDが指定されていません");
            return;
        }

        int tripId = Integer.parseInt(idStr);
        BusTrip trip = tripDAO.findById(tripId).orElse(null);

        if (trip != null) {
            String companyName = companyDAO.findById(trip.getCompanyId())
                                        .map(c -> c.getCompanyName())
                                        .orElse("不明");
            req.setAttribute("trip", new BusTripDto(trip, companyName));
        } else {
            req.setAttribute("trip", null);
        }

        req.getRequestDispatcher("/detail.jsp").forward(req, resp);
    }
}

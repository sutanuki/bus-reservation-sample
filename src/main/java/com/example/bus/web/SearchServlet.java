package com.example.bus.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
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
        String fromArea = req.getParameter("fromArea");
        String toArea = req.getParameter("toArea");
        String dateStr = req.getParameter("date");
        String type = req.getParameter("type");

        LocalDate date = null;
        if (dateStr != null && !dateStr.isEmpty()) {
            date = LocalDate.parse(dateStr);
        }

        LocalDateTime from = null;
        LocalDateTime to = null;
        if (date != null) {
            from = date.atTime(3, 0);
            to = date.plusDays(1).atTime(2, 59, 59);
        }

        List<BusTrip> results = tripDAO.search(fromArea, toArea, from, to, type);

        List<BusTripDto> dtos = results.stream()
            .filter(t -> t.getLimitTime().isAfter(LocalDateTime.now()))
            .map(t -> {
                String companyName = companyDAO.findById(t.getCompanyId())
                                            .map(c -> c.getCompanyName())
                                            .orElse("不明");
                return new BusTripDto(t, companyName);
            })
            .collect(Collectors.toList());

        req.setAttribute("trips", dtos);
        req.getRequestDispatcher("/search.jsp").forward(req, resp);
    }

}
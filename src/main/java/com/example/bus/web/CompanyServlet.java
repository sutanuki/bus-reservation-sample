package com.example.bus.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bus.dao.BusCompanyDAO;
import com.example.bus.dao.BusTripDAO;
import com.example.bus.model.BusCompany;
import com.example.bus.model.BusTripDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {
    private final BusTripDAO tripDAO = new BusTripDAO();
    private final BusCompanyDAO companyDAO = new BusCompanyDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer companyId = (session != null) ? (Integer) session.getAttribute("companyId") : null;

        if (companyId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        BusCompany company = companyDAO.findById(companyId).orElse(null);
        if (company == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        List<BusTripDto> companyTrips = tripDAO.findByCompanyId(companyId).stream()
                .filter(t -> t.getDepartureTime().isAfter(LocalDateTime.now()))
                .map(t -> new BusTripDto(t, company.getCompanyName()))
                .collect(Collectors.toList());

        req.setAttribute("companyTrips", companyTrips);
        req.setAttribute("companyName", company.getCompanyName());

        req.getRequestDispatcher("/company/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer companyId = (session != null) ? (Integer) session.getAttribute("companyId") : null;

        if (companyId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String fromArea = req.getParameter("fromArea");
        String toArea = req.getParameter("toArea");
        LocalDateTime departureTime = LocalDateTime.parse(req.getParameter("departureTime"));
        LocalDateTime arrivalTime = LocalDateTime.parse(req.getParameter("arrivalTime"));
        LocalDateTime limitTime = LocalDateTime.parse(req.getParameter("limitTime"));
        int capacity = Integer.parseInt(req.getParameter("capacity"));

        tripDAO.add(companyId, fromArea, toArea,
                departureTime, arrivalTime, limitTime, capacity);

        resp.sendRedirect(req.getContextPath() + "/company");
    }
}

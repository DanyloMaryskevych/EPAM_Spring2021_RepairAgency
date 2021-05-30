package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.Role;
import com.example.Broken_Hammer.filter.LanguageCookieFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie langCookie = LanguageCookieFilter.getLanguageCookie(request);

        int startPage = Integer.parseInt(request.getParameter("page"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        Map<String, String> filtersMap = new HashMap<>();
        filtersMap.put("performance_status", request.getParameter("performance"));
        filtersMap.put("payment_status", request.getParameter("payment"));
        filtersMap.put("login", request.getParameter("worker"));

        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPages(Role.ADMIN, 0);

        request.setAttribute("sort_param", sort);
        request.setAttribute("order_param", order);
        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getAllOrders(sort, order, start, filtersMap, langCookie.getValue()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/orders.jsp");
        dispatcher.forward(request, response);
    }
}

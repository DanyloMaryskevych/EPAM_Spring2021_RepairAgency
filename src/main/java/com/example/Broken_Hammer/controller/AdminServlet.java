package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        int startPage = Integer.parseInt(request.getParameter("page"));
        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPages("Admin", 0);

        request.setAttribute("sort_param", sort);
        request.setAttribute("order_param", order);
        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getAllOrders(sort, order, start));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/orders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

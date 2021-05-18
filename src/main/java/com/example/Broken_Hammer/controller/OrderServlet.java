package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("orderID"));

        Order order = orderDAO.getOrderForCustomerById(orderID);
        order.setId(orderID);
        String worker = orderDAO.getWorker(orderID);

        request.setAttribute("temp_order", order);
        request.setAttribute("temp_worker", worker);
        request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        orderDAO.addOrder((Integer) session.getAttribute("userID"), request.getParameterMap());
        response.sendRedirect("customer");
    }
}

package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.OrderDAO;

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post");
        Map<String, String[]> map = request.getParameterMap();
        for (String s : map.keySet()) {
            System.out.println(s + ": " + map.get(s)[0]);
        }

        HttpSession session = request.getSession();
        System.out.println();

        orderDAO.addOrder((Integer) session.getAttribute("userID"), request.getParameterMap());
        response.sendRedirect("customer");
    }
}

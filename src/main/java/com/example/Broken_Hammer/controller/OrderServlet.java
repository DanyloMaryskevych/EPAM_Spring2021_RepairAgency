package com.example.Broken_Hammer.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
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

        response.sendRedirect("customer");
    }
}

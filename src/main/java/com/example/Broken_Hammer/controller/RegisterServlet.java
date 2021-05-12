package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            userDAO.addUser(request.getParameterMap());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        response.sendRedirect("index.jsp");
    }
}

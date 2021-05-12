package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        userDAO.addUser("Danylo", "1111", "CUSTOMER");
//        System.out.println("added!");
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> stringEnumeration = request.getParameterNames();

        while (stringEnumeration.hasMoreElements()) {
            String paramName = stringEnumeration.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        response.sendRedirect("index.jsp");
    }
}

package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.User;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        String login = request.getParameter("login");
        user.setLogin(login);
        user.setPassword(request.getParameter("password"));

        String role =  userDAO.checkUser(user);

        HttpSession session = request.getSession();
        session.setAttribute("username", login);
        session.setAttribute("role", role);

        switch (role) {
            case "Customer": {
                response.sendRedirect("welcome_customer.jsp");
                break;
            }
            case "Workman": {
                response.sendRedirect("welcome_workman.jsp");
                break;
            }
            default: {
                session.removeAttribute("username");
                session.removeAttribute("role");
                session.invalidate();
                response.sendRedirect("login.jsp");
            }
        }
    }
}

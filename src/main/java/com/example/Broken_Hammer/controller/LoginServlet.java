package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

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
        String login = request.getParameter(LOGIN);
        user.setLogin(login);
        user.setPassword(request.getParameter(PASSWORD));

        String role = (String) request.getAttribute(ROLE);

        HttpSession session = request.getSession();
        session.setAttribute("id", session.getId());
        session.setAttribute("username", login);
        session.setAttribute("role", role);

        if (role.equals("Customer")) response.sendRedirect("welcome_customer.jsp");
        else if (role.equals("Worker")) response.sendRedirect("welcome_worker.jsp");


    }
}

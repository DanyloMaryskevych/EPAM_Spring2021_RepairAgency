package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = DAOFactory.getUserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(LOGIN);
        int id = userDAO.getId(login);

        String role = (String) request.getAttribute(ROLE);

        HttpSession session = request.getSession();
        session.setAttribute("id", session.getId());
        session.setAttribute("username", login);
        session.setAttribute("userID", id);
        session.setAttribute("role", role);

        switch (role) {
            case CUSTOMER:
            case WORKER:
                response.sendRedirect("profile?page=1");
                break;
            case ADMIN:
                response.sendRedirect("admin?page=1&sort=data&order=desc");
                break;
        }

    }
}

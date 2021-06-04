package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter(LOGIN);
        int userId = userDAO.getId(login);

        int roleId =  Integer.parseInt(String.valueOf(request.getAttribute(ROLE_ID)));

        HttpSession session = request.getSession();
        session.setAttribute("session_id", session.getId());

        logger.info("Session ID: " + session.getId());

        session.setAttribute(LOGIN, login);
        session.setAttribute(USER_ID, userId);
        session.setAttribute(ROLE_ID, roleId);

        switch (Role.getRoleById(roleId)) {
            case CUSTOMER:
            case WORKER:
                response.sendRedirect("profile?page=1");
                break;
            case ADMIN:
                response.sendRedirect("admin?page=1&sort=date&order=desc");
                break;
        }
    }
}

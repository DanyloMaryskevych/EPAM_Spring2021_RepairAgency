package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.Role;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login"})
public class LoginFilter implements Filter {
    public static final String USER_VALIDATION_ERROR = "invalidUserError";
    public static final String USER_VALIDATION_MESSAGE = "Wrong Password or Login! Please, try again";
    public static final String JSP_PAGE = "/login.jsp";

    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if(httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);

            String userRole = userDAO.checkIfUserExist(login, password);

            if (userRole != null) {
                logger.trace("Login for " + login + " (" + Role.getRoleById(Integer.parseInt(userRole)) + ") " + "successful!");
                request.setAttribute(ROLE_ID, userRole);
                chain.doFilter(request, response);
            }
            else {
                request.setAttribute(USER_VALIDATION_ERROR, USER_VALIDATION_MESSAGE);
                logger.error("Set the request attribute: invalidUserError --> " + USER_VALIDATION_MESSAGE);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        RequestDispatcher rd = request.getRequestDispatcher(JSP_PAGE);
        rd.include(request, response);
    }
}

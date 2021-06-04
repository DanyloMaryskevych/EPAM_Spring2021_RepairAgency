package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.UserDAO;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "RegisterFilter", urlPatterns = {"/register"})
public class RegisterFilter implements Filter {
    public static final String LOGIN_VALIDATION_ERROR = "loginError";
    public static final String LOGIN_VALIDATION_MESSAGE = "This login is already taken! Please choose another one";
    public static final String PASSWORD_EQUALITY_ERROR = "passwordEqualityError";
    public static final String PASSWORD_EQUALITY_MESSAGE = "Passwords should be equals!";
    public static final String JSP_PAGE = "/registration.jsp";

    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if(httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);
            String password1 = request.getParameter(CONFIRMED_PASSWORD);

            boolean passwordsEquality = userDAO.passwordsEquality(password, password1);
            boolean loginValidation = userDAO.checkLogin(login);

            if (passwordsEquality && loginValidation) {
                request.setAttribute(ROLE_ID, request.getParameter(ROLE_ID));
                chain.doFilter(request, response);
            }

            if (!loginValidation) {
                request.setAttribute(LOGIN_VALIDATION_ERROR, LOGIN_VALIDATION_MESSAGE);
                logger.error("Login Error: " + LOGIN_VALIDATION_MESSAGE);
            }
            else if (!passwordsEquality) {
                request.setAttribute(PASSWORD_EQUALITY_ERROR, PASSWORD_EQUALITY_MESSAGE);
                logger.error("Password Equality Error: " + PASSWORD_EQUALITY_MESSAGE);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        RequestDispatcher rd = request.getRequestDispatcher(JSP_PAGE);
        rd.include(request, response);

    }
}

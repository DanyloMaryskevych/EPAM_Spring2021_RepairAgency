package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "RegisterFilter", urlPatterns = {"/register"})
public class RegisterFilter implements Filter {
    public static final String LOGIN_VALIDATION_ERROR = "loginError";
    public static final String LOGIN_VALIDATION_MESSAGE = "This login is already taken! Please choose another one.";
    public static final String PASSWORD_EQUALITY_ERROR = "passwordEqualityError";
    public static final String PASSWORD_EQUALITY_MESSAGE = "Passwords should be equals!";
    public static final String PASSWORD_VALIDATION_ERROR = "passwordValidationError";
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password should be minimum 8 characters, at least one letter and one number!";
    public static final String JSP_PAGE = "registration.jsp";

    private UserDAO userDAO;

    public void init(FilterConfig config) {
        userDAO = new UserDAO();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String password1 = request.getParameter(CONFIRMED_PASSWORD);

        boolean passwordsEquality = userDAO.passwordsEquality(password, password1);
        boolean passwordValidation = userDAO.validatePassword(password);
        boolean loginValidation = userDAO.checkLogin(login);

        if (passwordsEquality && passwordValidation && loginValidation) {
            request.setAttribute(ROLE, request.getParameter(ROLE));
            chain.doFilter(request, response);
        }

        if (!loginValidation) request.setAttribute(LOGIN_VALIDATION_ERROR, LOGIN_VALIDATION_MESSAGE);
        else if (!passwordsEquality) request.setAttribute(PASSWORD_EQUALITY_ERROR, PASSWORD_EQUALITY_MESSAGE);
        else if (!passwordValidation) request.setAttribute(PASSWORD_VALIDATION_ERROR, PASSWORD_VALIDATION_MESSAGE);

        RequestDispatcher rd = request.getRequestDispatcher(JSP_PAGE);
        rd.include(request, response);

    }
}

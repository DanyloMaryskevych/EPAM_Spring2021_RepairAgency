package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/login"})
public class LoginFilter implements Filter {
    public static final String USER_VALIDATION_ERROR = "invalidUserError";
    public static final String USER_VALIDATION_MESSAGE = "Wrong Password or Login! Please, try again.";
    public static final String JSP_PAGE = "login.jsp";

    private UserDAO userDAO;

    public void init(FilterConfig config) {
        userDAO = new UserDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        String userRole = userDAO.checkIfUserExist(login, password);

        if (userRole != null) {
            request.setAttribute(ROLE, userRole);
            chain.doFilter(request, response);
        }
        else {
            request.setAttribute(USER_VALIDATION_ERROR, USER_VALIDATION_MESSAGE);
            RequestDispatcher rd = request.getRequestDispatcher(JSP_PAGE);
            rd.include(request, response);
        }
    }
}
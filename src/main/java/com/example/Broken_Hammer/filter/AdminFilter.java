package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    private final Logger logger = Logger.getLogger(AdminFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession session = httpServletRequest.getSession();
        Role role = Role.getRoleById((Integer) session.getAttribute(ROLE_ID));

        if (role != Role.ADMIN) {
            logger.error("User# " + session.getAttribute(USER_ID) + " (" + role + ") trying to access Admin page!");
            httpServletResponse.sendRedirect(ERROR_SERVLET);
        }
        else chain.doFilter(request, response);

    }
}

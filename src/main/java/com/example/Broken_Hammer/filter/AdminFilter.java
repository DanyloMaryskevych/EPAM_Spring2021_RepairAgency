package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.ROLE_ID;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        Role role = Role.getRoleById((Integer) session.getAttribute(ROLE_ID));
        if (role != Role.ADMIN) System.out.println("Sorry, wrong role!");
        else System.out.println("Hello Admin");

        chain.doFilter(request, response);
    }
}

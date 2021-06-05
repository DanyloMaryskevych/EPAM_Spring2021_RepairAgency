package com.example.Broken_Hammer.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.ROLE_ID;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Auth");

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession session = httpServletRequest.getSession();

        try {
            session.getAttribute(ROLE_ID);
            chain.doFilter(request, response);
        } catch (NullPointerException e) {
            httpServletResponse.sendRedirect("login.jsp");
        }
    }
}

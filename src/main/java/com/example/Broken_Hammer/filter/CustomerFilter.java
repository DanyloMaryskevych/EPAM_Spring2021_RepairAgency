package com.example.Broken_Hammer.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CustomerFilter", urlPatterns = {"/customer", "/order"})
public class CustomerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();

        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) servletResponse.sendRedirect("login.jsp");
        else chain.doFilter(request, response);
    }
}

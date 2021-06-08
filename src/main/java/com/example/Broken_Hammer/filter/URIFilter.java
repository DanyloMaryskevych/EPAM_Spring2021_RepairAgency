package com.example.Broken_Hammer.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.ERROR_SERVLET;
import static com.example.Broken_Hammer.helper.URIValidator.URIValidation;

@WebFilter(filterName = "URIFilter")
public class URIFilter implements Filter {
    private final Logger logger = Logger.getLogger(URIFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        boolean validURI = URIValidation(requestURI);

        if (validURI) chain.doFilter(request, response);
        else {
            logger.error("Invalid URI: " + requestURI);
            httpResponse.sendRedirect(ERROR_SERVLET);
        }

    }
}

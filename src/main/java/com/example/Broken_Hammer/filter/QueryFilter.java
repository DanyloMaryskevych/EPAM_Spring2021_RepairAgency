package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.controller.PDFGeneratorServlet;
import com.example.Broken_Hammer.helper.PDFGenerator;
import com.example.Broken_Hammer.helper.QueryValidator;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.example.Broken_Hammer.Constants.ERROR_PAGE;

@WebFilter(filterName = "QueryFilter")
public class QueryFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        System.out.println("QueryFilter " + httpServletRequest.getQueryString());

        boolean validQueryString = false;
        Map<String, String> queryString = PDFGenerator.queryStringParser(httpServletRequest.getQueryString());

        if (queryString != null) validQueryString = QueryValidator.queryStringValidator(queryString);

        if (validQueryString) chain.doFilter(request, response);
        else httpServletResponse.sendRedirect(ERROR_PAGE);
    }
}

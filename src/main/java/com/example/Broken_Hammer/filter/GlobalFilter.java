package com.example.Broken_Hammer.filter;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "GlobalFilter")
public class GlobalFilter implements Filter {
    private final Logger logger = Logger.getLogger(GlobalFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Global");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Cookie langCookie = getLanguageCookie(request);

        if (langCookie == null) {
            langCookie = new Cookie(LANGUAGE_COOKIE, ENGLISH_LANGUAGE);
            httpServletResponse.addCookie(langCookie);
        }

        MDC.put("method", httpServletRequest.getMethod());
        MDC.put("uri", httpServletRequest.getRequestURI());
        long start = System.nanoTime();
        try {
            MDC.put("CorrelationId", getCorrelationId());
            chain.doFilter(request, response);
        } finally {
            long end = System.nanoTime();
            logger.info("status code: " + httpServletResponse.getStatus() + ", duration: " + (end - start) / 1_000_000 + " ms");
            MDC.remove("CorrelationId");
        }
    }

    public static Cookie getLanguageCookie(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie langCookie = null;

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LANGUAGE_COOKIE)) {
                    langCookie = cookie;
                    break;
                }
            }
        }

        return langCookie;
    }

    private String getCorrelationId() {
        return UUID.randomUUID().toString();
    }

}

package com.example.Broken_Hammer.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "CookieFilter", urlPatterns = {"/*"})
public class LanguageCookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie langCookie = getLanguageCookie(request);

        if (langCookie == null) {
            langCookie = new Cookie(LANGUAGE_COOKIE, ENGLISH_LANGUAGE);
            httpServletResponse.addCookie(langCookie);
        }

        chain.doFilter(request, response);
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
}

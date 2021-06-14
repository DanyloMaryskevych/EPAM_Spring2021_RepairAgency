package com.example.Broken_Hammer.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIValidator {
    private static final Set<String> URISet = new HashSet<>();
    public static final String JS_CSS_PATTERN = "\\b(JS|CSS)\\b";

    static {
        URISet.add("/bh/");
        URISet.add("/bh/home");
        URISet.add("/bh/error");
        URISet.add("/bh/error.jsp");
        URISet.add("/bh/workers");
        URISet.add("/bh/login");
        URISet.add("/bh/register");
        URISet.add("/bh/logout");
        URISet.add("/bh/admin");
        URISet.add("/bh/profile");
        URISet.add("/bh/order");
        URISet.add("/bh/PDFGenerator");
    }

    public static boolean URIValidation(String requestURI) {
        Pattern pattern = Pattern.compile(JS_CSS_PATTERN);
        Matcher matcher = pattern.matcher(requestURI);

        if (matcher.find()) return true;

        for (String s : URISet) {
            if (requestURI.equals(s)) {
                return true;
            }
        }
        
        return false;
    }
}

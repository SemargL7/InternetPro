package com.finalproject.internetpro.web;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class CookiesController {
    private static final Logger logger = Logger.getLogger(CookiesController.class);
    private CookiesController cookiesController;

    private static CookiesController instance;

    public static CookiesController getInstance(){
        if(instance == null)
            instance = new CookiesController();
        return instance;
    }

    private CookiesController() {
    }
    /**
     * Function is reading a cookies and return value
     * @param key cookie`s name
     * @return cookie
     */
    public Optional<Cookie> readCookie(String key, HttpServletRequest request) {
        logger.info("Cookies are reading");
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .findAny();
    }

    /**
     * Function is deleting cookies
     * @param key cookie`s name
     */
    public void removeCookie(String key, String path, HttpServletResponse response) {
        response.setContentType("text/html");
        Cookie cookie = new Cookie(key,"");
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
        logger.info("Cookies are deleted");
    }
}

package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.web.CookiesController;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/logOut")
public class LoginOutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginOutServlet.class);

    private static final String COOKIE_EMAIL_NAME = "u_mail";
    private static final String COOKIE_PASSWORD_NAME = "u_password";
    private static final String LOG_USER = "logUser";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(LOG_USER,null);

        CookiesController.getInstance().removeCookie(COOKIE_EMAIL_NAME,"/login",resp);
        CookiesController.getInstance().removeCookie(COOKIE_PASSWORD_NAME,"/login",resp);

        logger.info("logOut | User is login-outed");
        resp.sendRedirect("/login");
    }
}

package com.finalproject.internetpro.web.servlets;


import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import com.finalproject.internetpro.web.CookiesController;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Cookie> email = CookiesController.getInstance().readCookie("u_mail",req);
        Optional<Cookie> password = CookiesController.getInstance().readCookie("u_password",req);
        if(email.isPresent() && password.isPresent() && !email.get().getValue().isBlank() && !password.get().getValue().isBlank()){
            logger.info("viewLogin | Find cookies");
            doPost(req,resp);
        }
        else {
            req.getRequestDispatcher(Path.PAGE__LOGIN).forward(req, resp);
            req.getSession().setAttribute("logWarning", false);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url;
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        Cookie cookieUEmail = new Cookie("u_mail",email);
        Cookie cookieUPassword = new Cookie("u_password",password);

        Optional<Cookie> readCookieEmail = CookiesController.getInstance().readCookie("u_mail",req);
        Optional<Cookie> readCookiePassword = CookiesController.getInstance().readCookie("u_password",req);

        if(readCookieEmail.isPresent() && readCookiePassword.isPresent() && !readCookieEmail.get().getValue().isBlank() && !readCookiePassword.get().getValue().isBlank()){
            logger.info("login | Using cookies for logging");
            email = readCookieEmail.get().getValue();
            password = readCookiePassword.get().getValue();
        }

        Optional<User> user = ServiceUserImpl.getInstance().loggingUser(email,password);

        if(!email.isBlank() && !password.isBlank() && user.isPresent()){
            req.getSession().setAttribute("logUser", user.get());

            cookieUEmail.setMaxAge(60 * 60 * 24 * 10);
            cookieUPassword.setMaxAge(60 * 60 * 24 * 10);

            cookieUEmail.setPath("/login");
            cookieUPassword.setPath("/login");

            resp.addCookie(cookieUEmail);
            resp.addCookie(cookieUPassword);

            url = "/";
        } else {
            logger.warn("login | Incorrect email or password");
            req.getSession().setAttribute("logWarning",true);

            CookiesController.getInstance().removeCookie("u_mail","/login",resp);
            CookiesController.getInstance().removeCookie("u_password","/login",resp);

            url = "/login";
        }
        resp.sendRedirect(url);
    }


}

package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HomeServlet.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PAGE__HOME).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("user_name");
        String surname = req.getParameter("user_surname");
        String email = req.getParameter("user_email");
        String password = req.getParameter("user_password");

        User logUser = (User) req.getSession().getAttribute("logUser");

        System.out.println(logUser);
        System.out.println(name + " " + surname + " " + email + " " + password);
        if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            logUser.setName(name);
            logUser.setSurname(surname);
            logUser.setEmail(email);
            logUser.setPassword(password);
            //if updating is successful -> apply changes
            if(ServiceUserImpl.getInstance().update(logUser)) {

                Cookie cookieUEmail = new Cookie("u_mail", email);
                Cookie cookieUPassword = new Cookie("u_password", password);

                cookieUEmail.setMaxAge(60 * 60 * 24 * 10);
                cookieUPassword.setMaxAge(60 * 60 * 24 * 10);

                cookieUEmail.setPath("/login");
                cookieUPassword.setPath("/login");

                resp.addCookie(cookieUEmail);
                resp.addCookie(cookieUPassword);
                logger.info("saveProfile | User`s profile is Successfully saved");
                req.getSession().setAttribute("logUser", logUser);
            }
            else
                logger.warn("saveProfile | User`s profile is UNSuccessfully saved");
        }
        else
            logger.warn("saveProfile | Incorrect date inputted");
        resp.sendRedirect("/home");
    }
}

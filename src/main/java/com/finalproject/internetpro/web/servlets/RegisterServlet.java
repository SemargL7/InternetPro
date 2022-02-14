package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.entity.UserAccess;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/home/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PAGE__REGISTER).forward(req, resp);
        req.getSession().setAttribute("regWarning",false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "";
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String dateOfBirth = req.getParameter("dataOfBirth");
        String blockStatus = req.getParameter("blocked");
        String accessStatus = req.getParameter("access");

        String password = req.getParameter("password");


        User user = new User();

        if(!name.isBlank() && !surname.isBlank() && !email.isBlank() && !password.isBlank() && !dateOfBirth.isBlank() && !blockStatus.isBlank() && !accessStatus.isBlank()){
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            user.setDateOfBirth(Date.valueOf(dateOfBirth));
            user.setBalance(0);
            user.setBlocked(blockStatus.equalsIgnoreCase("blocked"));
            user.setUserAccess(accessStatus.equalsIgnoreCase("User")? UserAccess.USER:UserAccess.MANAGER);
        }
        if(ServiceUserImpl.getInstance().register(user)){
            logger.info("register | successfully registered");
            url = "/home/usersList";
        }else{
            logger.warn("register | incorrect data");
            req.getSession().setAttribute("regWarning",true);
            url = "/home/register";
            doGet(req,resp);
        }
        resp.sendRedirect(url);
    }
}

package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/usersList")
public class UsersListServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UsersListServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        req.setAttribute("page", page);


        List<User> listUser = ServiceUserImpl.getInstance().getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listUser.size(), start);
        end = Math.min(listUser.size(), end);
        listUser = listUser.subList(start,end);
        req.getSession().setAttribute("listUser", listUser);

        req.getSession().setAttribute("paginationMax", Math.ceil((double)listUser.size()/5));

        RequestDispatcher dispatcher = req.getRequestDispatcher(Path.PAGE__USERS_LIST);
        dispatcher.forward(req, resp);
    }

}

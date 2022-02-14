package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.web.Path;
import com.finalproject.internetpro.web.Sorting;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/userTariffsList")
public class UserTariffListServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserTariffListServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        req.setAttribute("page", page);

        User logUser = (User) req.getSession().getAttribute("logUser");

        List<Tariff> userTariff = logUser.getTariffs();

        Sorting.listSort(userTariff,
                (Boolean) req.getSession().getAttribute("AZ"),
                (Boolean) req.getSession().getAttribute("cost"));

        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(userTariff.size(), start);
        end = Math.min(userTariff.size(), end);
        userTariff = userTariff.subList(start,end);



        req.getSession().setAttribute("userTariff", userTariff);

        req.getSession().setAttribute("paginationMax", Math.ceil((double)userTariff.size()/5));

        RequestDispatcher dispatcher = req.getRequestDispatcher(Path.PAGE__USER_TARIFF_LIST);
        dispatcher.forward(req, resp);
    }
}

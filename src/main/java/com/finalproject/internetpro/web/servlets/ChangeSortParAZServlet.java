package com.finalproject.internetpro.web.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/home/changeSortParAZ")
public class ChangeSortParAZServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangeSortParAZServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean AZ = (Boolean) req.getSession().getAttribute("AZ");
        req.getSession().setAttribute("AZ",!AZ);

        //Returning to prev page
        String page = "";
        List<String> link = Arrays
                .stream(req.getHeader("referer").split("/"))
                .toList();
        for (int i = link.size() - 1; i >= 0; i--) {
            if(link.get(i).contains(req.getServerName()))
                break;
            page = "/" + link.get(i) + page;
        }
        resp.sendRedirect(page);
    }
}

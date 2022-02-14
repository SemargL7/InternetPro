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

@WebServlet("/changeLanguage")
public class ChangeLanguageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChangeLanguageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer language = (Integer) req.getSession().getAttribute("language");
        if(language == 2)language = 1;
        else language = 2;
        req.getSession().setAttribute("language",language);
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
        page = page.isEmpty()?"/":page;
        logger.info("changeLanguage | Return to prev page");
        resp.sendRedirect(page);
    }
}

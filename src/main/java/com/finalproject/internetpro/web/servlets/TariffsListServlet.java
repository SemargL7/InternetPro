package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
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
import java.util.Arrays;
import java.util.List;

@WebServlet("/home/tariffsList")
public class TariffsListServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TariffsListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        req.setAttribute("page", page);


        List<Tariff> listTariff = ServiceTariffImpl.getInstance().getAll();

        String[] filters = req.getParameterValues("filter");


        if(filters!=null){
            boolean AZ = !Arrays.asList(filters).contains("az");
            boolean cost = !Arrays.asList(filters).contains("cost");
            Sorting.listSort(listTariff,
                    AZ,
                    cost);
        }

        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);



        req.getSession().setAttribute("listTariff", listTariff);

        req.getSession().setAttribute("paginationMax", Math.ceil((double)listTariff.size()/5));

        RequestDispatcher dispatcher = req.getRequestDispatcher(Path.PAGE__TARIFF_LIST);
        dispatcher.forward(req, resp);
    }

}

package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/deleteTariff")
public class DeleteTariffServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteTariffServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idTariff = Integer.parseInt(req.getParameter("id"));
        if(ServiceTariffImpl.getInstance().delete(idTariff))
            logger.info("deleteTariff | Tariff is Successfully deleted");
        else
            logger.warn("deleteTariff | Tariff is UNSuccessfully deleted");
        resp.sendRedirect("/home/managerTariffsList");
    }
}

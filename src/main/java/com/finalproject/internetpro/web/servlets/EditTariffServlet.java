package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;

import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/home/editTariff")
public class EditTariffServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EditTariffServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Optional<Tariff> selectedTariff = ServiceTariffImpl.getInstance().get(id);
        if(selectedTariff.isPresent()){
            Tariff existingTariff = selectedTariff.get();
            req.setAttribute("tariff", existingTariff);
            req.getRequestDispatcher(Path.PAGE__EDIT_TARIFF).forward(req, resp);
        }else
            resp.sendRedirect("/home/managerTariffsList");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Optional<Tariff> selectedTariff = ServiceTariffImpl.getInstance().get(id);

        if(selectedTariff.isPresent()){
            Tariff tariff = selectedTariff.get();
            tariff.setCost(Double.parseDouble(req.getParameter("cost")));
            tariff.setDaysOfTariff(Integer.parseInt(req.getParameter("daysOfTariff")));
            tariff.putDescription(1,req.getParameter("descriptionENG"));
            tariff.putDescription(2,req.getParameter("descriptionUA"));
            if(ServiceTariffImpl.getInstance().update(tariff))
                logger.info("updateTariff | Tariff is Successfully updated");
            else
                logger.warn("updateTariff | Tariff is UNSuccessfully updated");
        }else
            logger.warn("updateTariff | Tariff isn`t found");

        resp.sendRedirect("/home/managerTariffsList");
    }
}

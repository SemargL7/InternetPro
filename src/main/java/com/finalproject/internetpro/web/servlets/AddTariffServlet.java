package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Service;
import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.services.impl.ServiceServiceImpl;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/home/addTariff")
public class AddTariffServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddTariffServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Service> listService = ServiceServiceImpl.getInstance().getAll();

        req.setAttribute("listService", listService);
        req.getRequestDispatcher(Path.PAGE__ADD_TARIFF).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<Service> service = ServiceServiceImpl.getInstance().get(Long.parseLong(req.getParameter("serviceId")));

        if(service.isPresent()) {
            Tariff tariff = new Tariff();
            tariff.setService(service.get());
            tariff.setCost(Double.parseDouble(req.getParameter("cost")));
            tariff.setDaysOfTariff(Integer.parseInt(req.getParameter("daysOfTariff")));
            tariff.putDescription(1, req.getParameter("descriptionENG"));
            tariff.putDescription(2, req.getParameter("descriptionUA"));
            if(ServiceTariffImpl.getInstance().save(tariff))
                logger.info("addTariff | Tariff is Successfully added");
            else
                logger.warn("addTariff | Tariff is UNSuccessfully added");
        }
        else
            logger.warn("addTariff | Service isn`t found");

        resp.sendRedirect("/home/managerTariffsList");
    }
}

package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/home/connectTariff")
public class ConnectTariffServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ConnectTariffServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idTariff = Integer.parseInt(req.getParameter("id"));
        User logUser = (User) req.getSession().getAttribute("logUser");

        if(!logUser.isBlocked()) {
            Optional<Tariff> selectedTariff = ServiceTariffImpl.getInstance().get(idTariff);
            if (selectedTariff.isPresent()) {
                logUser.addTariff(selectedTariff.get());

                if(ServiceUserImpl.getInstance().update(logUser)){
                    logUser = ServiceUserImpl.getInstance().get(logUser.getId()).orElse(logUser);
                    req.getSession().setAttribute("logUser", logUser);
                    logger.info("connectTariff | tariff is successfully connected to user: id" + logUser.getId());
                }
            }
            else
                logger.warn("connectTariff | tariff isn`t found");
        }
        else
            logger.warn("connectTariff | User is blocked");

        ServiceUserImpl.getInstance().updateStatus(logUser.getId());

        resp.sendRedirect("/home/tariffsList");
    }
}

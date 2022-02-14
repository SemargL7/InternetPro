package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import com.finalproject.internetpro.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home/balance")
public class BalanceServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BalanceServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(Path.PAGE__BALANCE).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String res = req.getParameter("balance");
        User logUser = (User) req.getSession().getAttribute("logUser");
        try{
            double depMoney = Double.parseDouble(res);
            if (depMoney >= 0) {
                logUser.setBalance(logUser.getBalance() + depMoney);
                if(ServiceUserImpl.getInstance().update(logUser)) {
                    logger.info("depositBalance | Balance is Successfully deposited");
                    req.getSession().setAttribute("logUser", logUser);
                }
                else
                    logger.warn("depositBalance | Balance is UNSuccessfully deposited");
            }
            else
                logger.warn("depositBalance | Number is lowest then 0");
        }catch (Exception e){
            logger.error("depositBalance | ERROR: " + e.getMessage());
        }
        ServiceUserImpl.getInstance().updateStatus(logUser.getId());
        resp.sendRedirect("/home");
    }
}

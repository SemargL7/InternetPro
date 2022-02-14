package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/home/blockSwitcher")
public class BlockSwitcherServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BlockSwitcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<User> selectedUser = ServiceUserImpl.getInstance().get(id);
        selectedUser.ifPresent(user -> {
            if(ServiceUserImpl.getInstance().blockStatusUser(user.getId(), !user.isBlocked()))
                logger.info("blockSwitcher | Successfully change block status");
            else
                logger.warn("blockSwitcher | UNSuccessfully change block status");
        });
        resp.sendRedirect("/home/usersList");
    }
}

package com.finalproject.internetpro.listener;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.atomic.AtomicReference;

public class ContextListener implements ServletContextListener {
    private User logUser;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("logUser",logUser);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logUser = null;
    }
}

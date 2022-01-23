package com.finalproject.internetpro.listener;

import com.finalproject.internetpro.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {
    private User logUser;
    private boolean logWarning;
    private boolean regWarning;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        logWarning = false;
        regWarning = false;
        servletContext.setAttribute("logUser",logUser);
        servletContext.setAttribute("logWarning",logWarning);
        servletContext.setAttribute("regWarning",regWarning);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logUser = null;
    }
}

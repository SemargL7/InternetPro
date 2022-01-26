package com.finalproject.internetpro.listener;

import com.finalproject.internetpro.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


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

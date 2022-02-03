package com.finalproject.internetpro.filter;

import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter class that mapped for all URLs.
 * Setting request and response to UTF8 format.
 * Setting to start state of parameters: language - ENG, AZ - A-Z, cost - ^(from highest to lowest) if they are nulls
 */
public class ServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Integer lang = (Integer) request.getSession().getAttribute("language");
        if(lang == null)
            request.getSession().setAttribute("language",1);

        Boolean AZ = (Boolean) request.getSession().getAttribute("AZ");
        if(AZ == null)
            AZ = true;
        request.getSession().setAttribute("AZ",AZ);
        Boolean cost  = (Boolean) request.getSession().getAttribute("cost");
        if(cost == null)
            cost = true;
        request.getSession().setAttribute("cost",cost);

        User logUser = (User) request.getSession().getAttribute("logUser");
        if(logUser != null) {
            logUser = new ServiceUserImpl().get(logUser.getId()).orElse(logUser);
            request.getSession().setAttribute("logUser", logUser);
        }

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
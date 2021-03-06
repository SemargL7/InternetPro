package com.finalproject.internetpro.web.filter;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Filter class that mapped for all URLs.
 * Setting request and response to UTF8 format.
 * Setting to start state of parameters: language - ENG, AZ - A-Z, cost - ^(from highest to lowest) if they are nulls
 */
public class ServletFilter implements Filter {
    static final Logger logger = Logger.getLogger(ServletFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        logger.info("ServletFilter | Filtering");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String lang = request.getParameter("lang");
        if(request.getSession().getAttribute("language") != null && lang != null)
            request.getSession().setAttribute("language", lang.equals("ua") ?2:1);//1-eng 2-ua
        else if(request.getSession().getAttribute("language") == null)
            request.getSession().setAttribute("language", 1);//1-eng


        User logUser = (User) request.getSession().getAttribute("logUser");
        if(logUser != null) {
            logUser = ServiceUserImpl.getInstance().get(logUser.getId()).orElse(logUser);
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
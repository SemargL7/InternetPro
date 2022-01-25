package com.finalproject.internetpro.filter;

import com.finalproject.internetpro.model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageAccessFilter implements Filter {
    static final Logger logger = Logger.getLogger(PageAccessFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) servletRequest.getServletContext().getAttribute("logUser");
        String uri = request.getRequestURI();
        System.out.println(uri);
        if(user != null && user.isSpecialAccess())
        {
            if(uri.equals("/home/balance") || uri.equals("/home/tariffsList") || uri.equals("/home/userTariffsList") ||
                    uri.contains("/home/connectTariff") || uri.contains("/home/disconnectTariff") ||
                    uri.contains("/home/depositBalance") || uri.contains("/home/depBalance")){
                logger.warn("Have not an access to this page");
                response.sendRedirect("/home");
                return;
            }
        }
        else if(user != null)
        {
            if(uri.equals("/home/addTariff") || uri.equals("/home/changeTariff") ||
                    uri.equals("/home/managerTariffsList") || uri.equals("/home/usersList") ||
                    uri.contains("/home/deleteTariff") ||  uri.contains("/home/blockSwitcher") ||
                    uri.contains("/home/updateTariff") ||  uri.contains("/home/addedTariff")){
                logger.warn("Have not an access to this page");
                response.sendRedirect("/home");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
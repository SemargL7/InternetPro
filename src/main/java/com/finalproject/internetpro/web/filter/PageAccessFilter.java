package com.finalproject.internetpro.web.filter;

import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.entity.UserAccess;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Filter class that mapped for all URLs where is containing "/home/*".
 * Giving accesses for some page from looking at User status(User/Manager)
 */
public class PageAccessFilter implements Filter {
    static final Logger logger = Logger.getLogger(PageAccessFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("PageAccessFilter | Filtering");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User)request.getSession().getAttribute("logUser");

        String uri = request.getRequestURI();

        if(user != null && user.getUserAccess() == UserAccess.MANAGER)
        {
            if(uri.equals("/home/balance") || uri.equals("/home/tariffsList") || uri.equals("/home/userTariffsList") ||
                    uri.contains("/home/connectTariff") || uri.contains("/home/disconnectTariff") ||
                    uri.contains("/home/depositBalance") || uri.contains("/home/depBalance")){
                logger.warn("Have not an access to this page");
                response.sendRedirect("/home");
                return;
            }
            else if(uri.equals("/login")) {
                logger.warn("You are already logged");
                response.sendRedirect("/home");
                return;
            }
        }
        else if(user != null)
        {
            if(uri.equals("/home/addTariff") || uri.equals("/home/changeTariff") ||
                    uri.equals("/home/managerTariffsList") || uri.equals("/home/usersList") ||
                    uri.contains("/home/deleteTariff") ||  uri.contains("/home/blockSwitcher") ||
                    uri.contains("/home/updateTariff") ||  uri.contains("/home/addedTariff") || uri.contains("/register")){
                logger.warn("Have not an access to this page");
                response.sendRedirect("/home");
                return;
            }
            else if(uri.equals("/login")) {
                logger.warn("You are already logged");
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

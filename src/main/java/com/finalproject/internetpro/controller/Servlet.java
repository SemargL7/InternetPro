package com.finalproject.internetpro.controller;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.model.Service;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet("/")
public class Servlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(Servlet.class);
    static final Integer ELEMENTS_PAGINATION_PAGE = 5;
    private DAOUser daoUser;
    private DAOTariff daoTariff;
    private DAOService daoService;
    private User logUser;
    private Integer language;//1-ENG. 2-UA

    public void init() {
        daoUser = new DAOUser();
        daoTariff = new DAOTariff();
        daoService = new DAOService();
        logger.info("Init Servlet");
        language = 1;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/login" -> {
                    logger.info("action->viewLogin");
                    viewLogin(request, response);
                }
                case "/register" -> {
                    logger.info("action->viewRegister");
                    viewRegister(request, response);
                }
                case "/home/addTariff" -> {
                    logger.info("action->viewTariffAdd");
                    viewTariffAdd(request, response);
                }
                case "/home/addedTariff" -> {
                    logger.info("action->addTariff");
                    addTariff(request, response);
                }
                case "/home/changeTariff" -> {
                    logger.info("action->viewTariffChange");
                    viewTariffChange(request, response);
                }
                case "/home/updateTariff" -> {
                    logger.info("action->updateTariff");
                    updateTariff(request, response);
                }
                case "/home/depBalance" -> {
                    logger.info("action->viewDepositBalance");
                    viewDepositBalance(request, response);
                }
                case "/home/balance" -> {
                    logger.info("action->viewUserBalance");
                    viewUserBalance(request, response);
                }
                case "/login/log" -> {
                    logger.info("action->login");
                    login(request, response);
                }
                case "/home/loginOut" -> {
                    logger.info("action->viewLoginOut");
                    viewLoginOut(request, response);
                }
                case "/register/reg" -> {
                    logger.info("action->register");
                    register(request, response);
                }
                case "/home" -> {
                    logger.info("action->viewHome");
                    viewHome(request, response);
                }
                case "/home/usersList" -> {
                    logger.info("action->viewUsersList");
                    viewUsersList(request, response);
                }
                case "/home/managerTariffsList" -> {
                    logger.info("action->viewManagerTariffsList");
                    viewManagerTariffsList(request, response);
                }
                case "/home/userTariffsList" -> {
                    logger.info("action->viewUserTariffsList");
                    viewUserTariffsList(request, response);
                }
                case "/home/tariffsList" -> {
                    logger.info("action->viewTariffsList");
                    viewTariffsList(request, response);
                }
                case "/home/deleteTariff" -> {
                    logger.info("deleteTariff");
                    deleteTariff(request, response);
                }
                case "/home/blockSwitcher" -> {
                    logger.info("action->blockSwitcher");
                    blockSwitcher(request, response);
                }
                case "/home/connectTariff" -> {
                    logger.info("action->connectTariff");
                    connectTariff(request, response);
                }
                case "/home/disconnectTariff" -> {
                    logger.info("action->disconnectTariff");
                    disconnectTariff(request, response);
                }
                case "/home/depositBalance" -> {
                    logger.info("action->depositBalance");
                    depositBalance(request, response);
                }
                case "/home/logOut" -> {
                    logger.info("action->logOut");
                    logOut(request, response);
                }
                case "/changeLanguage" -> {
                    logger.info("action->changeLanguage");
                    changeLanguage(request, response);
                }
                case "/home/downloadDocx" ->{
                    logger.info("action->downloadDocx");
                    downloadDocx(request, response);
                }
                default -> {
                    logger.info("action->default");
                    response.sendRedirect("/");
                }
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    public void destroy() {}

    private void viewHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = "";
        if (logUser.isSpecialAccess()) url = "home/managerHome.jsp";
        else url = "home/userHome.jsp";
        List<User> listUser = daoUser.getAll();
        request.getSession().setAttribute("listUser", listUser);
        List<Tariff> listTariff = daoTariff.getAll();
        request.getSession().setAttribute("listTariff", listTariff);
        List<Tariff> userTariff = logUser.getTariffs();
        request.getSession().setAttribute("userTariff", userTariff);
        double userBalance = logUser.getBalance();
        request.getSession().setAttribute("userBalance", userBalance);
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void viewUserBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/balance.jsp");
        dispatcher.forward(request, response);
    }

    private void viewTariffAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<Service> listService = daoService.getAll();
        request.getSession().setAttribute("listService", listService);
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/addTariff.jsp");
        dispatcher.forward(request, response);
    }

    private void viewTariffChange(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Tariff existingTariff = daoTariff.get(id).get();
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/changeTariff.jsp");
        request.getSession().setAttribute("tariff", existingTariff);
        dispatcher.forward(request, response);
    }

    private void viewLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login/log.jsp");
        dispatcher.forward(request, response);
    }

    private void viewLoginOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/logOut.jsp");
        dispatcher.forward(request, response);
    }

    private void viewRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("register/register.jsp");
        dispatcher.forward(request, response);
    }
    private void viewDepositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/balance.jsp");
        dispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String url = "";
        if (daoUser.loggingUser(email,password) != null) {
            logUser = daoUser.get(daoUser.loggingUser(email,password)).get();
            request.getServletContext().setAttribute("logUser", logUser);
            url = "/home";
        } else {
            url = "log";
        }
        response.sendRedirect(url);
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dateOfBirth = request.getParameter("dataOfBirth");

        User user = new User();

        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setDateOfBirth(Date.valueOf(dateOfBirth));
        user.setBalance(0);
        user.setBlocked(true);
        user.setSpecialAccess(false);

        daoUser.save(user);

        response.sendRedirect("/login");
    }

    private void blockSwitcher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        daoUser.blockStatusUser(id,!daoUser.get(id).get().isBlocked());
        response.sendRedirect("/home/usersList");
    }

    private void connectTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoUser.connectTariffConnection(logUser.getId(),idTariff);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).get();
        response.sendRedirect("/home/tariffsList");
    }

    private void disconnectTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoUser.deleteTariffConnection(logUser.getId(),idTariff);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).get();
        response.sendRedirect("/home/userTariffsList");
    }

    private void deleteTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoTariff.delete(idTariff);
        response.sendRedirect("/home/managerTariffsList");
    }

    private void updateTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Tariff tariff = daoTariff.get(id).orElse(new Tariff());
        tariff.setCost(Double.parseDouble(request.getParameter("cost")));
        tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
        tariff.putDescription(1,request.getParameter("descriptionENG"));
        tariff.putDescription(2,request.getParameter("descriptionUA"));
        daoTariff.update(tariff);
        response.sendRedirect("/home/managerTariffsList");
    }

    private void addTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        Tariff tariff = new Tariff();
        tariff.setService(daoService.get(Long.parseLong(request.getParameter("serviceId"))).get());
        tariff.setCost(Double.parseDouble(request.getParameter("cost")));
        tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
        tariff.putDescription(1,request.getParameter("descriptionENG"));
        tariff.putDescription(2,request.getParameter("descriptionUA"));
        daoTariff.save(tariff);
        response.sendRedirect("/home/managerTariffsList");
    }

    private void depositBalance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        String res = request.getParameter("balance");
        double depMoney = 0.0;
        if(res.length() > 0 && Double.parseDouble(res) >= 0)
            depMoney = Double.parseDouble(res);
        logUser.setBalance(logUser.getBalance()+depMoney);
        daoUser.update(logUser);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).get();
        response.sendRedirect("/home");
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        logUser = null;
        request.getServletContext().setAttribute("logUser", logUser);
        response.sendRedirect("/");
    }

    private void changeLanguage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        if(language == 2)language = 1;
        else language = 2;
        String page =Arrays
                .stream(request.getHeader("referer").split("/"))
                .reduce((first, second) -> second).orElse("");
        response.sendRedirect("/"+page);
    }


    private void viewUsersList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        Object o = request.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.getSession().setAttribute("page", page);


        List<User> listUser = daoUser.getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listUser.size(), start);
        end = Math.min(listUser.size(), end);
        listUser = listUser.subList(start,end);
        request.getSession().setAttribute("listUser", listUser);

        request.getSession().setAttribute("paginationMax", listUser.size()/5);
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/usersList.jsp");
        dispatcher.forward(request, response);
    }

    private void viewManagerTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        Object o = request.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.getSession().setAttribute("page", page);


        List<Tariff> listTariff = daoTariff.getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);
        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute("paginationMax", listTariff.size()/5);

        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/managerTariffsList.jsp");
        dispatcher.forward(request, response);
    }

    private void viewUserTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {

        Object o = request.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.getSession().setAttribute("page", page);


        List<Tariff> userTariff = logUser.getTariffs();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(userTariff.size(), start);
        end = Math.min(userTariff.size(), end);
        userTariff = userTariff.subList(start,end);
        request.getSession().setAttribute("userTariff", userTariff);

        request.getSession().setAttribute("paginationMax", userTariff.size()/5);

        double userBalance = logUser.getBalance();
        request.getSession().setAttribute("userBalance", userBalance);

        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/userTariffsList.jsp");
        dispatcher.forward(request, response);
    }

    private void viewTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {



        Object o = request.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.getSession().setAttribute("page", page);


        List<Tariff> listTariff = daoTariff.getAll();
        int start = (page-1)*ELEMENTS_PAGINATION_PAGE;
        int end = start+ELEMENTS_PAGINATION_PAGE;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);
        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute("paginationMax", listTariff.size()/ELEMENTS_PAGINATION_PAGE);
        double userBalance = logUser.getBalance();
        request.getSession().setAttribute("userBalance", userBalance);

        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/tariffsList.jsp");
        dispatcher.forward(request, response);
    }

    private void downloadDocx(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {

        List<Tariff> tariffList = daoTariff.getAll();
        String fileName = "tariffs.txt";

        String str ="";
        tariffList.sort((o1, o2) -> o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName()));
        for (int i = 0; i < tariffList.size(); i++) {
            str += ("\nID:"+String.valueOf(tariffList.get(i).getId()));
            str+=("\nService:"+String.valueOf(tariffList.get(i).getService().getServiceName()));
            str+=("\nCost:"+String.valueOf(tariffList.get(i).getCost()));
            str+=("\nDescription ENG:"+String.valueOf(tariffList.get(i).getDescription().get(1)));
            str+=("\nDescription UA:"+String.valueOf(tariffList.get(i).getDescription().get(2)));
            str+=("\n\n");
        }
        try {
            FileWriter myObj = new FileWriter("tariffs.txt");
            myObj.write(str);
            myObj.close();
        } catch (IOException e) {
            logger.error("An error occurred.");
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        String filename = "tariffs.txt";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        FileInputStream fileInputStream = new FileInputStream(filename);

        int i;
        while( (i = fileInputStream.read()) != -1 )
        {
            out.write(i);
        }
        fileInputStream.close();
        out.close();

        response.sendRedirect("/home/tariffsList");
    }

}
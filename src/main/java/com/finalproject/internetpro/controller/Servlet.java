package com.finalproject.internetpro.controller;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.model.Service;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet class for Internet provider
 * Servlet standardized API for creating dynamic content to a web server
 * @see DAOUser
 * @see DAOTariff
 * @see DAOService
 * @see User
 * @see Tariff
 * @see Service
 */
@WebServlet("/")
public class Servlet extends HttpServlet {
    static final Logger logger = Logger.getLogger(Servlet.class);
    static final Integer ELEMENTS_PAGINATION_PAGE = 5;
    private DAOUser daoUser;
    private DAOTariff daoTariff;
    private DAOService daoService;
    private User logUser;
    private Integer language;//1-ENG. 2-UA
    private boolean regWarning;
    private boolean logWarning;

    public void init() {
        daoUser = new DAOUser();
        daoTariff = new DAOTariff();
        daoService = new DAOService();
        logger.info("Init Servlet");
        language = 1;
        regWarning = false;
        logWarning = false;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        doGet(request, response);
    }

    /**
     * This method is the main method, which contains the very logic of the servlet
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
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
                case "/home/saveProfile"->{
                    logger.info("action->saveProfile");
                    saveProfile(request, response);
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

    /**
     * If the servlet object is not used for a long time - destroy
     */
    public void destroy() {
        super.destroy();
    }

    /**
     * Function is choosing witch jsp page will be open.
     * If user is manager -> managerHome
     * If user is user -> userHome
     */
    private void viewHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String url = "";
        if (logUser.isSpecialAccess()) url = "home/managerHome.jsp";
        else url = "home/userHome.jsp";

        request.getSession().setAttribute("user",request.getServletContext().getAttribute("logUser"));
        double userBalance = logUser.getBalance();
        request.getSession().setAttribute("userBalance", userBalance);
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Function is viewing a balance page
     */
    private void viewUserBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/balance.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a Tariff add page
     */
    private void viewTariffAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<Service> listService = daoService.getAll();
        request.getSession().setAttribute("listService", listService);
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/addTariff.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a Tariff change page
     */
    private void viewTariffChange(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Tariff existingTariff = daoTariff.get(id).orElseThrow();
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/changeTariff.jsp");
        request.getSession().setAttribute("tariff", existingTariff);
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a login page
     */
    private void viewLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        request.getSession().setAttribute("logWarning", logWarning);
        logWarning = false;
        RequestDispatcher dispatcher = request.getRequestDispatcher("login/log.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a login-out page
     */
    private void viewLoginOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/logOut.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a register page
     */
    private void viewRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        request.getSession().setAttribute("regWarning", regWarning);
        regWarning = false;
        RequestDispatcher dispatcher = request.getRequestDispatcher("register/register.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is viewing a deposit page
     */
    private void viewDepositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/balance.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is do logging.
     * Using requests for getting email and password for logging
     */
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String url = "";
        if (email!=null && password !=null && daoUser.loggingUser(email,password) != null) {
            logUser = daoUser.get(daoUser.loggingUser(email,password)).orElseThrow();
            request.getServletContext().setAttribute("logUser", logUser);
            url = "/home";
        } else {
            logWarning = true;
            url = "/login";
        }
        response.sendRedirect(url);
    }

    /**
     * Function is registering a new User.
     * if email which was entered is already into database -> redirected again to register page and give warning
     */
    private void register(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dateOfBirth = request.getParameter("dataOfBirth");

        if(daoUser.mailFree(email)) {
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
        }else{
            regWarning = true;
            response.sendRedirect("/register");
        }

    }

    /**
     * Function is switching block status of User which was got by id
     */
    private void blockSwitcher(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        daoUser.blockStatusUser(id,!daoUser.get(id).orElseThrow().isBlocked());
        response.sendRedirect("/home/usersList");
    }

    /**
     * Function is connecting a tariff to User`s tariff-list and write off funds from the balance sheet
     */
    private void connectTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoUser.connectTariffConnection(logUser.getId(),idTariff);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).orElseThrow();
        response.sendRedirect("/home/tariffsList");
    }
    /**
     * Function is disconnecting a tariff from User tariff-list
     */
    private void disconnectTariff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoUser.deleteTariffConnection(logUser.getId(),idTariff);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).orElseThrow();
        response.sendRedirect("/home/userTariffsList");
    }

    /**
     * Function is deleting the Tariff which was got by id
     */
    private void deleteTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        daoTariff.delete(idTariff);
        response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is updating the Tariff which was got by id
     */
    private void updateTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Tariff tariff = daoTariff.get(id).orElse(new Tariff());
        tariff.setCost(Double.parseDouble(request.getParameter("cost")));
        tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
        tariff.putDescription(1,request.getParameter("descriptionENG"));
        tariff.putDescription(2,request.getParameter("descriptionUA"));
        daoTariff.update(tariff);
        response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is inserting a new Tariff
     */
    private void addTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Tariff tariff = new Tariff();
        tariff.setService(daoService.get(Long.parseLong(request.getParameter("serviceId"))).orElseThrow());
        tariff.setCost(Double.parseDouble(request.getParameter("cost")));
        tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
        tariff.putDescription(1,request.getParameter("descriptionENG"));
        tariff.putDescription(2,request.getParameter("descriptionUA"));
        daoTariff.save(tariff);
        response.sendRedirect("/home/managerTariffsList");
    }

    /**
     * Function is depositing money to User`s account-balance
     */
    private void depositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String res = request.getParameter("balance");
        double depMoney = 0.0;
        if(res.length() > 0 && Double.parseDouble(res) >= 0)
            depMoney = Double.parseDouble(res);
        logUser.setBalance(logUser.getBalance()+depMoney);
        daoUser.update(logUser);
        daoUser.updateAllUsersBalances();
        logUser = daoUser.get(logUser.getId()).orElseThrow();
        response.sendRedirect("/home");
    }

    /**
     * Function is log-outing from account by setting logUser to NULL
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logUser = null;
        response.sendRedirect("/");
    }

    /**
     * Function is changing web language on all pages
     */
    private void changeLanguage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if(language == 2)language = 1;
        else language = 2;
        List<String> link = Arrays
                .stream(request.getHeader("referer").split("/"))
                .toList();
        String page = "";
        for (int i = link.size() - 1; i >= 0; i--) {
            if(link.get(i).contains(request.getServerName()))
                break;
            page = "/" + link.get(i) + page;
        }
        response.sendRedirect(page);
    }

    /**
     * Function is viewing page with all users
     */
    private void viewUsersList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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

    /**
     * Function is viewing page with all tariffs
     */
    private void viewManagerTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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

    /**
     * Function is viewing the page with all User`s connected tariffs
     */
    private void viewUserTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

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

    /**
     * Function is viewing all tariffs which User can to connect
     */
    private void viewTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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
        request.getSession().setAttribute("language", language);
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/tariffsList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is send txt file with all tariffs to the server and next to user to download
     */
    private void downloadDocx(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<Tariff> tariffList = daoTariff.getAll();
        String fileName = "tariffs.txt";

        String str ="";
        tariffList.sort(new Comparator<Tariff>() {
            @Override
            public int compare(Tariff o1, Tariff o2) {
                if(o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                    return (int) (o1.getCost() - o2.getCost());
                else
                    return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
            }
        });
        for (Tariff tariff : tariffList) {
            str += ("\nID:" + String.valueOf(tariff.getId()));
            str += ("\nService:" + String.valueOf(tariff.getService().getServiceName()));
            str += ("\nCost:" + String.valueOf(tariff.getCost()));
            str += ("\nDescription ENG:" + String.valueOf(tariff.getDescription().get(1)));
            str += ("\nDescription UA:" + String.valueOf(tariff.getDescription().get(2)));
            str += ("\n\n");
        }
        try {
            OutputStreamWriter myObj =
                    new OutputStreamWriter(new FileOutputStream("tariffs.txt"), StandardCharsets.UTF_8);

            myObj.write(str);
            myObj.close();
        } catch (IOException e) {
            logger.error("An error occurred.");
            e.printStackTrace();
        }


        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                response.getOutputStream(), StandardCharsets.UTF_8), true);

        String filename = "tariffs.txt";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);

        int i;
        while( (i = reader.read()) != -1 )
        {
            out.write(i);
        }
        fis.close();
        isr.close();
        reader.close();
        out.close();

        response.sendRedirect("/home/tariffsList");
    }

    /**
     * Function is changing and updating(database) info about User account
     */
    private void saveProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logUser.setName(request.getParameter("user_name"));
        logUser.setSurname(request.getParameter("user_surname"));
        logUser.setEmail(request.getParameter("user_email"));
        logUser.setPassword(request.getParameter("user_password"));
        daoUser.update(logUser);
        response.sendRedirect("/home");
    }

}
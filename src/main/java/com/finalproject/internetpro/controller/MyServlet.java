package com.finalproject.internetpro.controller;


import com.finalproject.internetpro.model.Service;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.model.UserAccess;
import com.finalproject.internetpro.services.impl.ServiceServiceImpl;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet class for Internet provider
 * Servlet standardized API for creating dynamic content to a web server
 * Using Front-Controller pattern
 * @see ServiceUserImpl
 * @see ServiceServiceImpl
 * @see ServiceTariffImpl
 * @see User
 * @see Tariff
 * @see Service
 * @see Logger
 */
@WebServlet("/")
public class MyServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MyServlet.class);
    private static final Integer ELEMENTS_PAGINATION_PAGE = 5;
    private static final String COOKIE_EMAIL_NAME = "u_mail";
    private static final String COOKIE_PASSWORD_NAME = "u_password";
    private static final String LOG_USER = "logUser";
    private static final String LOG_WARNING = "logWarning";
    private static final String REG_WARNING = "regWarning";
    private static final String LANGUAGE = "language";
    private static final String PAGINATION = "paginationMax";
    private static final String PAGINATION_PAGE = "page";
    private static final String SORTING_BY_COST = "cost";


    private static final ServiceUserImpl serviceUser = new ServiceUserImpl();
    private static final ServiceTariffImpl serviceTariff = new ServiceTariffImpl();
    private static final ServiceServiceImpl serviceService = new ServiceServiceImpl();


    public void init() {
        logger.info("Init Servlet");
        new Thread(() -> {
            while (true) {
                try {
                    logger.info("Updating all users`s status");
                    serviceUser.getAll().forEach(x->serviceUser.updateStatus(x.getId()));
                    logger.info("Updating all users`s status was finished");
                    Thread.sleep(1000 * 60 * 60 * 24);
                } catch (InterruptedException e) {
                    logger.error(e.getCause() + " " + e.getMessage());
                    break;
                }
            }
        }).start();
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
                    logOut(request,response);
                }
                case "/changeLanguage" -> {
                    logger.info("action->changeLanguage");
                    changeLanguage(request, response);
                }
                case "/home/downloadDocx" ->{
                    logger.info("action->downloadDocx");
                    downloadTariffList(request, response);
                }
                case "/home/saveProfile"->{
                    logger.info("action->saveProfile");
                    saveProfile(request, response);
                }
                case "/home/changeSortParAZ"->{
                    logger.info("action->changeSortParAZ");
                    changeSortParAZ(request, response);
                }
                case "/home/changeSortParCost"->{
                    logger.info("action->changeSortParCost");
                    changeSortParCost(request, response);
                }
                case "/" -> {
                    logger.info("action->/");
                    response.sendRedirect("/");
                }
                default -> {
                    logger.info("action->404");
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
        request.getRequestDispatcher("home/home.jsp").forward(request, response);
    }

    /**
     * Function is viewing a balance page
     */
    private void viewUserBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("../home/user/balance.jsp").forward(request, response);
    }
    /**
     * Function is viewing a Tariff add page
     */
    private void viewTariffAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<Service> listService = serviceService.getAll();
        request.setAttribute("listService", listService);
        request.getRequestDispatcher("../home/manager/addTariff.jsp").forward(request, response);
    }
    /**
     * Function is viewing a Tariff change page
     */
    private void viewTariffChange(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        Optional<Tariff> selectedTariff = serviceTariff.get(id);
        if(selectedTariff.isPresent()){
            Tariff existingTariff = selectedTariff.get();
            request.setAttribute("tariff", existingTariff);
            request.getRequestDispatcher("../home/manager/changeTariff.jsp").forward(request, response);
        }else
            response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is viewing a login page or if cookies is found - redirect to log form
     */
    private void viewLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Optional<Cookie> email = readCookie(COOKIE_EMAIL_NAME,request);
        Optional<Cookie> password = readCookie(COOKIE_PASSWORD_NAME,request);
        if(email.isPresent() && password.isPresent() && !email.get().getValue().isBlank() && !password.get().getValue().isBlank()){
            logger.info("viewLogin | Find cookies");
            response.sendRedirect("/login/log");
        }
        else {
            request.getRequestDispatcher("login/log.jsp").forward(request, response);
            request.getSession().setAttribute(LOG_WARNING, false);
        }
    }
    /**
     * Function is viewing a register page
     */
    private void viewRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("register/register.jsp").forward(request, response);
        request.getSession().setAttribute(REG_WARNING,false);
    }
    /**
     * Function is viewing a deposit page
     */
    private void viewDepositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/balance.jsp");
        dispatcher.forward(request, response);
    }
    /**
     * Function is do logging.
     * Using requests for getting email and password for logging
     */
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        String url;
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        Cookie cookieUEmail = new Cookie(COOKIE_EMAIL_NAME,email);
        Cookie cookieUPassword = new Cookie(COOKIE_PASSWORD_NAME,password);

        Optional<Cookie> readCookieEmail = readCookie(COOKIE_EMAIL_NAME,request);
        Optional<Cookie> readCookiePassword = readCookie(COOKIE_PASSWORD_NAME,request);

        if(readCookieEmail.isPresent() && readCookiePassword.isPresent() && !readCookieEmail.get().getValue().isBlank() && !readCookiePassword.get().getValue().isBlank()){
            logger.info("login | Using cookies for logging");
            email = readCookieEmail.get().getValue();
            password = readCookiePassword.get().getValue();
        }

        Optional<User> user = serviceUser.loggingUser(email,password);

        if(!email.isBlank() && !password.isBlank() && user.isPresent()){
            request.getSession().setAttribute(LOG_USER, user.get());

            cookieUEmail.setMaxAge(60 * 60 * 24 * 10);
            cookieUPassword.setMaxAge(60 * 60 * 24 * 10);

            cookieUEmail.setPath("/login");
            cookieUPassword.setPath("/login");

            response.addCookie(cookieUEmail);
            response.addCookie(cookieUPassword);

            url = "/";
        } else {
            logger.warn("login | Incorrect email or password");
            request.getSession().setAttribute(LOG_WARNING,true);

            removeCookie(COOKIE_EMAIL_NAME,"/login",response);
            removeCookie(COOKIE_PASSWORD_NAME,"/login",response);

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
        String url;
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dateOfBirth = request.getParameter("dataOfBirth");

        User user = new User();

        if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !dateOfBirth.isEmpty()){
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            user.setDateOfBirth(Date.valueOf(dateOfBirth));
            user.setBalance(0);
            user.setBlocked(false);
            user.setUserAccess(UserAccess.USER);
        }
        if(serviceUser.register(user)){
            logger.info("register | successfully registered");
            url = "/login";
        }else{
            logger.warn("register | incorrect data");
            request.getSession().setAttribute(REG_WARNING,true);
            url = "/register";
        }
        response.sendRedirect(url);
    }

    /**
     * Function is switching block status of User which was got by id
     */
    private void blockSwitcher(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<User> selectedUser = serviceUser.get(id);
        selectedUser.ifPresent(user -> {
            if(serviceUser.blockStatusUser(user.getId(), !user.isBlocked()))
                logger.info("blockSwitcher | Successfully change block status");
            else
                logger.warn("blockSwitcher | UNSuccessfully change block status");
        });
        response.sendRedirect("/home/usersList");
    }

    /**
     * Function is connecting a tariff to User`s tariff-list and write off funds from the balance sheet
     */
    private void connectTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        User logUser = (User) request.getSession().getAttribute(LOG_USER);

        if(!logUser.isBlocked()) {
            Optional<Tariff> selectedTariff = serviceTariff.get(idTariff);
            if (selectedTariff.isPresent()) {
                logUser.addTariff(selectedTariff.get());

                if(serviceUser.update(logUser)){
                    logUser = serviceUser.get(logUser.getId()).orElse(logUser);
                    request.getSession().setAttribute(LOG_USER, logUser);
                    logger.info("connectTariff | tariff is successfully connected to user: id" + logUser.getId());
                }
            }
            else
                logger.warn("connectTariff | tariff isn`t found");
        }
        else
            logger.warn("connectTariff | User is blocked");

        serviceUser.updateStatus(logUser.getId());

        response.sendRedirect("/home/tariffsList");
    }
    /**
     * Function is disconnecting a tariff from User tariff-list
     */
    private void disconnectTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        User logUser = (User) request.getSession().getAttribute(LOG_USER);
        Optional<Tariff> selectedTariff = serviceTariff.get(idTariff);
        if(selectedTariff.isPresent()) {
            logUser.removeTariff(selectedTariff.get());
            if (serviceUser.update(logUser)) {
                serviceUser.updateStatus(logUser.getId());
                request.getSession().setAttribute(LOG_USER, logUser);
                logger.info("disconnectTariff | Tariff is successfully disconnected");
            }
        }
        else
            logger.warn("disconnectTariff | Tariff isn`t found");
        response.sendRedirect("/home/tariffsList");
    }

    /**
     * Function is deleting the Tariff which was got by id
     */
    private void deleteTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        if(serviceTariff.delete(idTariff))
            logger.info("deleteTariff | Tariff is Successfully deleted");
        else
            logger.warn("deleteTariff | Tariff is UNSuccessfully deleted");
        response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is updating the Tariff which was got by id
     */
    private void updateTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Optional<Tariff> selectedTariff = serviceTariff.get(id);

        if(selectedTariff.isPresent()){
            Tariff tariff = selectedTariff.get();
            tariff.setCost(Double.parseDouble(request.getParameter("cost")));
            tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
            tariff.putDescription(1,request.getParameter("descriptionENG"));
            tariff.putDescription(2,request.getParameter("descriptionUA"));
            if(serviceTariff.update(tariff))
                logger.info("updateTariff | Tariff is Successfully updated");
            else
                logger.warn("updateTariff | Tariff is UNSuccessfully updated");
        }else
            logger.warn("updateTariff | Tariff isn`t found");

        response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is inserting a new Tariff
     */
    private void addTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Tariff tariff = new Tariff();

        Optional<Service> service = serviceService.get(Long.parseLong(request.getParameter("serviceId")));

        if(service.isPresent()) {
            tariff.setService(service.get());
            tariff.setCost(Double.parseDouble(request.getParameter("cost")));
            tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
            tariff.putDescription(1, request.getParameter("descriptionENG"));
            tariff.putDescription(2, request.getParameter("descriptionUA"));
            if(serviceTariff.save(tariff))
                logger.info("addTariff | Tariff is Successfully added");
            else
                logger.warn("addTariff | Tariff is UNSuccessfully added");
        }
        else
            logger.warn("addTariff | Service isn`t found");

        response.sendRedirect("/home/managerTariffsList");
    }

    /**
     * Function is depositing money to User`s account-balance
     */
    private void depositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String res = request.getParameter("balance");
        User logUser = (User) request.getSession().getAttribute(LOG_USER);
        try{
            double depMoney = Double.parseDouble(res);
            if (depMoney >= 0) {
                logUser.setBalance(logUser.getBalance() + depMoney);
                if(serviceUser.update(logUser)) {
                    logger.info("depositBalance | Balance is Successfully deposited");
                    request.getSession().setAttribute(LOG_USER, logUser);
                }
                else
                    logger.warn("depositBalance | Balance is UNSuccessfully deposited");
            }
            else
                logger.warn("depositBalance | Number is lowest then 0");
        }catch (Exception e){
            logger.error("depositBalance | ERROR: " + e.getMessage());
        }
        serviceUser.updateStatus(logUser.getId());
        response.sendRedirect("/home");
    }

    /**
     * Function is log-outing from account by setting logUser to NULL
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.getSession().setAttribute(LOG_USER,null);

        removeCookie(COOKIE_EMAIL_NAME,"/login",response);
        removeCookie(COOKIE_PASSWORD_NAME,"/login",response);

        logger.info("logOut | User is login-outed");
        response.sendRedirect("/login");
    }

    /**
     * Function is changing web language on all pages
     */
    private void changeLanguage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer language = (Integer) request.getSession().getAttribute(LANGUAGE);
        if(language == 2)language = 1;
        else language = 2;
        request.getSession().setAttribute(LANGUAGE,language);
        //Returning to prev page
        String page = "";
        List<String> link = Arrays
                .stream(request.getHeader("referer").split("/"))
                .toList();
        for (int i = link.size() - 1; i >= 0; i--) {
            if(link.get(i).contains(request.getServerName()))
                break;
            page = "/" + link.get(i) + page;
        }
        page = page.isEmpty()?"/":page;
        logger.info("changeLanguage | Return to prev page");
        response.sendRedirect(page);
    }

    /**
     * Function is viewing page with all users
     */
    private void viewUsersList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Object o = request.getParameter(PAGINATION_PAGE);
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.setAttribute(PAGINATION_PAGE, page);


        List<User> listUser = serviceUser.getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listUser.size(), start);
        end = Math.min(listUser.size(), end);
        listUser = listUser.subList(start,end);
        request.getSession().setAttribute("listUser", listUser);

        request.getSession().setAttribute(PAGINATION, Math.ceil((double)listUser.size()/ELEMENTS_PAGINATION_PAGE));

        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/usersList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is viewing page with all tariffs
     */
    private void viewManagerTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Object o = request.getParameter(PAGINATION_PAGE);
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.setAttribute(PAGINATION_PAGE, page);


        List<Tariff> listTariff = serviceTariff.getAll();

        listSort(listTariff,
                (Boolean) request.getSession().getAttribute("AZ"),
                (Boolean) request.getSession().getAttribute(SORTING_BY_COST));

        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);



        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute(PAGINATION, Math.ceil((double)listTariff.size()/ELEMENTS_PAGINATION_PAGE));


        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/manager/managerTariffsList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is viewing the page with all User`s connected tariffs
     */
    private void viewUserTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Object o = request.getParameter(PAGINATION_PAGE);
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.setAttribute(PAGINATION_PAGE, page);

        User logUser = (User) request.getSession().getAttribute(LOG_USER);

        List<Tariff> userTariff = logUser.getTariffs();

        listSort(userTariff,
                (Boolean) request.getSession().getAttribute("AZ"),
                (Boolean) request.getSession().getAttribute(SORTING_BY_COST));

        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(userTariff.size(), start);
        end = Math.min(userTariff.size(), end);
        userTariff = userTariff.subList(start,end);



        request.getSession().setAttribute("userTariff", userTariff);

        request.getSession().setAttribute(PAGINATION, Math.ceil((double)userTariff.size()/ELEMENTS_PAGINATION_PAGE));

        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/userTariffsList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is viewing all tariffs which User can to connect
     */
    private void viewTariffsList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Object o = request.getParameter(PAGINATION_PAGE);
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.setAttribute(PAGINATION_PAGE, page);


        List<Tariff> listTariff = serviceTariff.getAll();

        listSort(listTariff,
                (Boolean) request.getSession().getAttribute("AZ"),
                (Boolean) request.getSession().getAttribute(SORTING_BY_COST));

        int start = (page-1)*ELEMENTS_PAGINATION_PAGE;
        int end = start+ELEMENTS_PAGINATION_PAGE;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);



        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute(PAGINATION, Math.ceil((double)listTariff.size()/ELEMENTS_PAGINATION_PAGE));

        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/tariffsList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is send txt file with all tariffs to the server and next to user to download
     */
    private void downloadTariffList(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<Tariff> tariffList = serviceTariff.getAll();
        String fileName = "tariffs.txt";

        String str ="";
        listSort(tariffList,
                (Boolean) request.getSession().getAttribute("AZ"),
                (Boolean) request.getSession().getAttribute(SORTING_BY_COST));

        for (Tariff tariff : tariffList) {
            str += ("\nID:" + (tariff.getId()));
            str += ("\nService:" + (tariff.getService().getServiceName()));
            str += ("\nCost:" + (tariff.getCost()));
            str += ("\nDescription ENG:" + (tariff.getDescription().get(1)));
            str += ("\nDescription UA:" + (tariff.getDescription().get(2)));
            str += ("\n\n");
        }
        try {
            OutputStreamWriter myObj =
                    new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8);

            myObj.write(str);
            myObj.close();
            logger.info("download | file is created");
        } catch (IOException e) {
            logger.error("download | ERROR: " + e.getMessage());
        }

        sendFile(request,response,fileName);

        logger.info("download | file is sent");

        response.sendRedirect("/home/tariffsList");
    }
    /**
     * Function is changing and updating(database) info about User account
     */
    private void saveProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("user_name");
        String surname = request.getParameter("user_surname");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");

        User logUser = (User) request.getSession().getAttribute(LOG_USER);

        System.out.println(logUser);
        System.out.println(name + " " + surname + " " + email + " " + password);
        if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            logUser.setName(name);
            logUser.setSurname(surname);
            logUser.setEmail(email);
            logUser.setPassword(password);
            //if updating is successful -> apply changes
            if(serviceUser.update(logUser)) {

                Cookie cookieUEmail = new Cookie(COOKIE_EMAIL_NAME, email);
                Cookie cookieUPassword = new Cookie(COOKIE_PASSWORD_NAME, password);

                cookieUEmail.setMaxAge(60 * 60 * 24 * 10);
                cookieUPassword.setMaxAge(60 * 60 * 24 * 10);

                cookieUEmail.setPath("/login");
                cookieUPassword.setPath("/login");

                response.addCookie(cookieUEmail);
                response.addCookie(cookieUPassword);
                logger.info("saveProfile | User`s profile is Successfully saved");
                request.getSession().setAttribute(LOG_USER, logUser);
            }
            else
                logger.warn("saveProfile | User`s profile is UNSuccessfully saved");
        }
        else
            logger.warn("saveProfile | Incorrect date inputted");
        response.sendRedirect("/home");
    }

    /**
     * Function is changing sorting logic.
     * Sort alphabetically
     */
    private void changeSortParAZ(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Boolean AZ = (Boolean) request.getSession().getAttribute("AZ");
        request.getSession().setAttribute("AZ",!AZ);

        //Returning to prev page
        String page = "";
        List<String> link = Arrays
                .stream(request.getHeader("referer").split("/"))
                .toList();
        for (int i = link.size() - 1; i >= 0; i--) {
            if(link.get(i).contains(request.getServerName()))
                break;
            page = "/" + link.get(i) + page;
        }
        response.sendRedirect(page);
    }
    /**
     * Function is changing sorting logic.
     * Sort by price
     */
    private void changeSortParCost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Boolean cost = (Boolean) request.getSession().getAttribute(SORTING_BY_COST);
        request.getSession().setAttribute(SORTING_BY_COST,!cost);

        //Returning to prev page
        String page = "";
        List<String> link = Arrays
                .stream(request.getHeader("referer").split("/"))
                .toList();
        for (int i = link.size() - 1; i >= 0; i--) {
            if(link.get(i).contains(request.getServerName()))
                break;
            page = "/" + link.get(i) + page;
        }
        response.sendRedirect(page);
    }

    /**
     * Function is sorting list by Alphabets and cost
     * @param list list which will be sorted
     * @param AZ sorting from A to Z or Z-A
     * @param cost sorting by cost > or <
     */
    private void listSort(List<Tariff> list, boolean AZ, boolean cost){
        list.sort(new Comparator<Tariff>() {
            @Override
            public int compare(Tariff o1, Tariff o2) {
                if (AZ && cost) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o1.getCost() - o2.getCost());
                    return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
                } else if (AZ) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o2.getCost() - o1.getCost());
                    return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
                } else if (cost) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o1.getCost() - o2.getCost());
                    return o2.getService().getServiceName().compareToIgnoreCase(o1.getService().getServiceName());
                } else {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o2.getCost() - o1.getCost());
                    return o2.getService().getServiceName().compareToIgnoreCase(o1.getService().getServiceName());
                }
            }
        });
    }
    /**
     * Function is reading a cookies and return value
     * @param key cookie`s name
     * @return cookie
     */
    public Optional<Cookie> readCookie(String key, HttpServletRequest request) {
        logger.info("Cookies are reading");
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .findAny();
    }

    /**
     * Function is deleting cookies
     * @param key cookie`s name
     */
    public void removeCookie(String key,String path,HttpServletResponse response) {
        response.setContentType("text/html");
        Cookie cookie = new Cookie(key,"");
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
        logger.info("Cookies are deleted");
    }

    /**
     * Function is sending file to client
     * @param fileName file`s name
     */
    private void sendFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                response.getOutputStream(), StandardCharsets.UTF_8), true);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        FileInputStream fis = new FileInputStream(fileName);
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
    }
}
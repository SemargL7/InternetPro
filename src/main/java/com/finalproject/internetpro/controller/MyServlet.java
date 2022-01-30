package com.finalproject.internetpro.controller;


import com.finalproject.internetpro.model.Service;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.services.impl.ServiceServiceImpl;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Arrays;
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

    private static final ServiceUserImpl serviceUser = new ServiceUserImpl();
    private static final ServiceTariffImpl serviceTariff = new ServiceTariffImpl();
    private static final ServiceServiceImpl serviceService = new ServiceServiceImpl();

    public void init() {
        logger.info("Init Servlet");
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
                    logOut(request,response);
                }
                case "/changeLanguage" -> {
                    logger.info("action->changeLanguage");
                    changeLanguage(request, response);
                }
                case "/home/downloadDocx" ->{
                    logger.info("action->downloadDocx");
                    downloadDocx(response);
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

        User logUser = (User) request.getSession().getAttribute("logUser");

        if (logUser.isSpecialAccess()) url = "home/managerHome.jsp";
        else url = "home/userHome.jsp";

        request.getRequestDispatcher(url).forward(request, response);
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
        request.getSession().setAttribute("listService", listService);
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
            request.getSession().setAttribute("tariff", existingTariff);
            request.getRequestDispatcher("../home/manager/changeTariff.jsp").forward(request, response);
        }else
            response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is viewing a login page
     */
    private void viewLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("login/log.jsp").forward(request, response);
        request.getSession().setAttribute("logWarning",false);
    }
    /**
     * Function is viewing a login-out page
     */
    private void viewLoginOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("../home/logOut.jsp").forward(request, response);
    }
    /**
     * Function is viewing a register page
     */
    private void viewRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("register/register.jsp").forward(request, response);
        request.getSession().setAttribute("regWarning",false);
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
        String url = "";
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = serviceUser.loggingUser(email,password);

        if(!email.isEmpty() && !password.isEmpty() && user.isPresent()){
            request.getSession().setAttribute("logUser", user.get());
            url = "/home";
        } else {
            request.getSession().setAttribute("logWarning",true);
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
        String url ="";
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
            user.setBlocked(true);
            user.setSpecialAccess(false);
        }
        if(serviceUser.register(user))
            url = "/login";
        else{
            request.getSession().setAttribute("regWarning",true);
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
        selectedUser.ifPresent(user -> serviceUser.blockStatusUser(user.getId(), !user.isBlocked()));
        response.sendRedirect("/home/usersList");
    }

    /**
     * Function is connecting a tariff to User`s tariff-list and write off funds from the balance sheet
     */
    private void connectTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        User logUser = (User) request.getSession().getAttribute("logUser");

        Optional<Tariff> selectedTariff = serviceTariff.get(idTariff);
        if(selectedTariff.isPresent()){
            logUser.addTariff(selectedTariff.get());
            serviceUser.update(logUser);
            serviceUser.updateAllUsersBalances();
            request.getSession().setAttribute("logUser",logUser);
        }

        response.sendRedirect("/home/tariffsList");
    }
    /**
     * Function is disconnecting a tariff from User tariff-list
     */
    private void disconnectTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        User logUser = (User) request.getSession().getAttribute("logUser");

        Optional<Tariff> selectedTariff = serviceTariff.get(idTariff);
        if(selectedTariff.isPresent()){
            logUser.removeTariff(selectedTariff.get());
            serviceUser.update(logUser);
            serviceUser.updateAllUsersBalances();
            request.getSession().setAttribute("logUser",logUser);
        }

        response.sendRedirect("/home/tariffsList");
    }

    /**
     * Function is deleting the Tariff which was got by id
     */
    private void deleteTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idTariff = Integer.parseInt(request.getParameter("id"));
        serviceTariff.delete(idTariff);
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
            serviceTariff.update(tariff);
        }

        response.sendRedirect("/home/managerTariffsList");
    }
    /**
     * Function is inserting a new Tariff
     */
    private void addTariff(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Tariff tariff = new Tariff();
        tariff.setService(serviceService.get(Long.parseLong(request.getParameter("serviceId"))).get());
        tariff.setCost(Double.parseDouble(request.getParameter("cost")));
        tariff.setDaysOfTariff(Integer.parseInt(request.getParameter("daysOfTariff")));
        tariff.putDescription(1,request.getParameter("descriptionENG"));
        tariff.putDescription(2,request.getParameter("descriptionUA"));
        serviceTariff.save(tariff);

        response.sendRedirect("/home/managerTariffsList");
    }

    /**
     * Function is depositing money to User`s account-balance
     */
    private void depositBalance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String res = request.getParameter("balance");
        User logUser = (User) request.getSession().getAttribute("logUser");
        try{
            double depMoney = Double.parseDouble(res);
            if (depMoney >= 0) {
                logUser.setBalance(logUser.getBalance() + depMoney);
                serviceUser.update(logUser);
                request.getSession().setAttribute("logUser",logUser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        serviceUser.updateAllUsersBalances();
        response.sendRedirect("/home");
    }

    /**
     * Function is log-outing from account by setting logUser to NULL
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().setAttribute("logUser",null);
        response.sendRedirect("/");
    }

    /**
     * Function is changing web language on all pages
     */
    private void changeLanguage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer language = (Integer) request.getSession().getAttribute("language");
        if(language == 2)language = 1;
        else language = 2;
        request.getSession().setAttribute("language",language);
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
     * Function is viewing page with all users
     */
    private void viewUsersList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Object o = request.getParameter("page");
        int page;
        if(o == null)page = 1;
        else page = Integer.parseInt(String.valueOf(o));
        request.getSession().setAttribute("page", page);


        List<User> listUser = serviceUser.getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listUser.size(), start);
        end = Math.min(listUser.size(), end);
        listUser = listUser.subList(start,end);
        request.getSession().setAttribute("listUser", listUser);

        request.getSession().setAttribute("paginationMax", listUser.size()/5);

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


        List<Tariff> listTariff = serviceTariff.getAll();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);
        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute("paginationMax", listTariff.size()/5);


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

        User logUser = (User) request.getSession().getAttribute("logUser");

        List<Tariff> userTariff = logUser.getTariffs();
        int start = (page-1)*5;
        int end = start+5;
        start = Math.min(userTariff.size(), start);
        end = Math.min(userTariff.size(), end);
        userTariff = userTariff.subList(start,end);
        request.getSession().setAttribute("userTariff", userTariff);

        request.getSession().setAttribute("paginationMax", userTariff.size()/5);

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


        List<Tariff> listTariff = serviceTariff.getAll();
        int start = (page-1)*ELEMENTS_PAGINATION_PAGE;
        int end = start+ELEMENTS_PAGINATION_PAGE;
        start = Math.min(listTariff.size(), start);
        end = Math.min(listTariff.size(), end);
        listTariff = listTariff.subList(start,end);
        request.getSession().setAttribute("listTariff", listTariff);

        request.getSession().setAttribute("paginationMax", listTariff.size()/ELEMENTS_PAGINATION_PAGE);

        RequestDispatcher dispatcher = request.getRequestDispatcher("../home/user/tariffsList.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Function is send txt file with all tariffs to the server and next to user to download
     */
    private void downloadDocx(HttpServletResponse response)
            throws IOException {

        List<Tariff> tariffList = serviceTariff.getAll();
        String fileName = "tariffs.txt";

        String str ="";
        tariffList.sort((o1, o2) -> {
            if(o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                return (int) (o1.getCost() - o2.getCost());
            else
                return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
        });
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
        } catch (IOException e) {
            logger.error("An error occurred.");
            e.printStackTrace();
        }


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

        User logUser = (User) request.getSession().getAttribute("logUser");

        System.out.println(logUser);
        System.out.println(name + " " + surname + " " + email + " " + password);
        if(!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            logUser.setName(name);
            logUser.setSurname(surname);
            logUser.setEmail(email);
            logUser.setPassword(password);
            serviceUser.update(logUser);
            request.getSession().setAttribute("logUser",logUser);
        }
        response.sendRedirect("/home");
    }

}
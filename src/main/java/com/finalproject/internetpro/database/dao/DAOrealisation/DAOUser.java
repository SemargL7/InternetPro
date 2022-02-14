package com.finalproject.internetpro.database.dao.DAOrealisation;

import com.finalproject.internetpro.database.dao.DAO;
import com.finalproject.internetpro.database.DBManager;
import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.entity.UserAccess;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.*;

/**
 * DAO class for User
 * The Data Access Object (DAO) pattern is a structural pattern that allows us
 * to isolate the application/business layer from the persistence layer
 * (usually a relational database but could be any other persistence mechanism) using an abstract API.
 * Using Singleton pattern
 * @see User
 */
public class DAOUser implements DAO<User> {
    static final Logger logger = Logger.getLogger(DAOUser.class);

    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SQL_GET_USER_CONNECTIONS = "SELECT tariff.id FROM tariffconnected INNER JOIN tariff ON tariff.id = tariffconnected.idTariff WHERE tariffconnected.idUser = ? and tariffconnected.connection_status = true";
    private static final String SQL_GET_ALL_USER_ID = "SELECT id from Users";
    private static final String SQL_INSERT_USER = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER ="update users set name = ?, surname = ?, dateBirth = ?, email = ?, password = ?, balance = ?, blocked = ?, access = ? where users.id = ?";
    private static final String SQL_DELETE_USER = "DELETE from users WHERE id = ?";
    private static final String SQL_GET_USER_ID_BY_EMAIL_AND_PASSWORD = "SELECT id from Users where email = ? and password = ?";
    private static final String SQL_DISCONNECT_USER_CONNECTION = "UPDATE TariffConnected SET date_end_connection = ?, connection_status = false where idUser = ? and idTariff = ?";
    private static final String SQL_CONNECT_USER_CONNECTION = "INSERT INTO TariffConnected value(?,?,?,NULL,true)";
    private static final String SQL_SET_USER_BLOCK_STATUS ="update users set blocked = ? where users.id = ?";
    private static final String SQL_SELECT_USER_ID_BY_EMAIL = "SELECT id from Users where email = ?";
    private static final String SQL_CONTINUE_CONNECTION = "SELECT tariff.id FROM tariffconnected INNER JOIN tariff ON tariff.id = tariffconnected.idTariff WHERE tariffconnected.idUser = ? and tariffconnected.connection_status = true and date_end_connection IS NOT null";


    private static DAOUser instance;

    public static DAOUser getInstance(){
        if(instance == null)
            instance = new DAOUser();
        return instance;
    }

    private DAOUser() {
    }
    /**
     * Function is returning User by id
     * @param id User`s id
     * @return Optional of User which we find
     */
    @Override
    public Optional<User> get(long id){

        User user = new User();

        try(Connection con = DBManager.getInstance().getConnection()) {

            PreparedStatement statement = con.prepareStatement(SQL_GET_USER_BY_ID);
            PreparedStatement statement2 = con.prepareStatement(SQL_GET_USER_CONNECTIONS);

            statement.setLong(1, id);
            statement2.setLong(1, id);

            ResultSet result = statement.executeQuery();
            ResultSet result2 = statement2.executeQuery();



            List<Tariff> tariffs = new ArrayList<>();
            while (result.next()) {
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setSurname(result.getString(3));
                user.setDateOfBirth(result.getDate(4));
                user.setEmail(result.getString(5));
                user.setPassword(result.getString(6));
                user.setBalance(result.getDouble(7));
                user.setBlocked(result.getBoolean(8));
                user.setUserAccess(result.getString(9).equals("MANAGER")? UserAccess.MANAGER:UserAccess.USER);
            }

            while (result2.next()) {
                Optional<Tariff> tariff = DAOTariff.getInstance().get(result2.getInt(1));
                if(tariff.isPresent()){
                    Tariff addTariff = tariff.get();
                    addTariff.setStatus(checkConnectionStatus(user.getId(),addTariff.getId()));
                    tariffs.add(addTariff);
                }
            }


            user.setTariffs(tariffs);

            if(user.getId()==0)
                user = null;



            result.close();
            result2.close();
            statement.close();
            statement2.close();
            con.close();

            logger.info("get|"+id);
        }catch (Exception e){
            logger.error("get|ERROR:"+e);
            user = null;
        }
        return Optional.ofNullable(user);
    }

    /**
     * Function is returning list of all Users
     * @return list of all Users
     */
    @Override
    public List<User> getAll(){
        List<User> users = null;

        try(Connection con = DBManager.getInstance().getConnection()) {
            logger.info("Start getALL");
            PreparedStatement statement = con.prepareStatement(SQL_GET_ALL_USER_ID);
            ResultSet result = statement.executeQuery();
            DAOUser daoUser = new DAOUser();
            users = new LinkedList<>();
            while (result.next())
                users.add(daoUser.get(result.getInt(1)).get());

            result.close();
            statement.close();
            con.close();

            logger.info("End getALL");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return users;
    }

    /**
     * Function is inserting a new User into database
     * @param user User which we want to insert
     * @return boolean, if insert is successful - true, and else - false
     */
    @Override
    public boolean save(User user){
        try(Connection con = DBManager.getInstance().getConnection()){

            PreparedStatement posted = con.prepareStatement(SQL_INSERT_USER);

            posted.setLong(1, user.getId());
            posted.setString(2, user.getName());
            posted.setString(3, user.getSurname());
            posted.setDate(4, user.getDateOfBirth());
            posted.setString(5, user.getEmail());
            posted.setString(6, user.getPassword());
            posted.setDouble(7, user.getBalance());
            posted.setBoolean(8, user.isBlocked());
            posted.setString(9, user.getUserAccess().toString());

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("save|"+user);
            return true;
        }catch (Exception e){
            logger.error("save|"+e);
            return false;
        }
    }

    /**
     * Function is setting new data into the User
     * @param user User with new data
     * @return boolean, if user is successful saved - true, and else - false
     */
    @Override
    public boolean update(User user){
        try(Connection con = DBManager.getInstance().getConnection()) {

            PreparedStatement posted = con.prepareStatement(SQL_UPDATE_USER);
            posted.setString(1,user.getName());
            posted.setString(2,user.getSurname());
            posted.setDate(3,user.getDateOfBirth());
            posted.setString(4,user.getEmail());
            posted.setString(5,user.getPassword());
            posted.setDouble(6,user.getBalance());
            posted.setBoolean(7,user.isBlocked());
            posted.setString(8, user.getUserAccess().toString());
            posted.setLong(9,user.getId());

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("update|"+user);
            return true;
        }catch (Exception e) {
            logger.error("update|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is deleting the User
     * @param id User`s id
     * @return boolean, if delete is successful - true, and else - false
     */
    @Override
    public boolean delete(int id){
        try (Connection con = DBManager.getInstance().getConnection()){

            PreparedStatement posted = con.prepareStatement(SQL_DELETE_USER);

            posted.setLong(1, id);

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("delete|"+id);
            return true;
        } catch(Exception e ) {
            logger.error("delete|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is finding User`s id which email and password is same as inputted
     * @param email User`s email
     * @param password User`s password
     * @return res/null if your is in database function returning id of User
     */
    public Integer loggingUser(String email,String password) {
        try (Connection con = DBManager.getInstance().getConnection()) {

            PreparedStatement statement = con.prepareStatement(SQL_GET_USER_ID_BY_EMAIL_AND_PASSWORD);

            statement.setString(1,email);
            statement.setString(2,password);

            ResultSet result = statement.executeQuery();
            Integer res = null;

            while (result.next())
                res = result.getInt(1);

            result.close();
            statement.close();
            con.close();

            logger.info("loggingUser|"+email+"|"+password);
            return res;
        }catch (Exception e){
            logger.error("loggingUser|ERROR:"+e);
            return null;
        }
    }

    /**
     * Function is disconnecting tariff
     * @param idUser User`s id which will disable the tariff
     * @param idTariff Tariff`s id which will be disabled
     */
    public void deleteTariffConnection(int idUser,int idTariff)
    {
        try (Connection con = DBManager.getInstance().getConnection()){
            PreparedStatement posted = con.prepareStatement(SQL_DISCONNECT_USER_CONNECTION);

            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            posted.setString(1,formatter.format(date));
            posted.setLong(2, idUser);
            posted.setLong(3, idTariff);


            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("deleteTariffConnection|"+idUser+"|"+idTariff);
        } catch(Exception e ) {
            logger.error("deleteTariffConnection|ERROR:"+e);
        }
    }

    /**
     * Function is connecting tariff
     * @param idUser User`s id which will be able the tariff
     * @param idTariff Tariff`s id which will be able for user
     */
    public void connectTariffConnection(int idUser,int idTariff)
    {
        try(Connection con = DBManager.getInstance().getConnection()){

            PreparedStatement posted = con.prepareStatement(SQL_CONNECT_USER_CONNECTION);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            posted.setLong(1, idUser);
            posted.setLong(2, idTariff);
            posted.setString(3,formatter.format(date));

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("connectTariffConnection|"+idUser+"|"+idTariff);
        } catch(Exception e ) {
            logger.error("connectTariffConnection|ERROR:"+e);
        }
    }

    /**
     * Function is setting User`s status(block/unblock)
     * @param id User`s id
     * @param block status(block - true, unblock - false)
     */
    public boolean blockStatusUser(int id, boolean block){
        try (Connection con = DBManager.getInstance().getConnection()){

            PreparedStatement posted = con.prepareStatement(SQL_SET_USER_BLOCK_STATUS);
            posted.setBoolean(1,block);
            posted.setLong(2,id);

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("blockStatusUser|"+id+"|"+block);
            return true;
        }catch (Exception e) {
            logger.error("blockStatusUser|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is updating user`s balances and block status, if user`s tariffs ended, User will be paid auto.
     * if user`s balance is lower cost of tariff - user will be blocked
     * @param id User`s id
     */
    public boolean updateStatus(int id){
        try(Connection con = DBManager.getInstance().getConnection()) {

            PreparedStatement posted = con.prepareStatement("UPDATE TariffConnected inner join Tariff on Tariff.id = TariffConnected.idTariff " +
                    "inner join TariffConnected on TariffConnected.idUser = Users.id set TariffConnected.dateOfLastConnection = case when (now() - dateOfLastConnection > day(Tariff.daysOfTariff)) then null when (now() - dateOfLastConnection > day(Tariff.daysOfTariff)) then now() end; where Users.id = ?");

            posted.setInt(1,id);
            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("updateStatus " + id);
            return true;
        }catch (Exception e) {
            logger.error("updateStatus|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is checking does email is free
     * @param email Email
     * @return TRUE/False, if mail is free - true, else - false
     */
    public boolean mailFree(String email){
        try(Connection con = DBManager.getInstance().getConnection()) {

            PreparedStatement statement = con.prepareStatement(SQL_SELECT_USER_ID_BY_EMAIL);
            statement.setString(1,email);

            ResultSet result = statement.executeQuery();
            Integer res = null;

            while (result.next())
                res = result.getInt(1);

            result.close();
            statement.close();
            con.close();

            logger.error("mailFree|"+email);
            return (res == null);
        }catch (Exception e){
            logger.error("mailFree|ERROR:"+e);
            return false;
        }
    }


    public boolean continueConnection(int id){
        try(Connection con = DBManager.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_CONTINUE_CONNECTION);
            List<Tariff> tariffs = new ArrayList<>();
            Optional<User> user = get(id);
            statement.setLong(1,id);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Optional<Tariff> tariff = DAOTariff.getInstance().get(result.getInt("idTariff"));
                tariff.ifPresent(tariffs::add);
            }

            if(user.isPresent() && tariffs.stream().mapToDouble(Tariff::getCost).sum() <= user.get().getBalance()){
                tariffs.forEach(x->deleteTariffConnection(id, x.getId()));
                tariffs.forEach(x->connectTariffConnection(id, x.getId()));
                blockStatusUser(id,false);
            }else if(user.isPresent()){
                blockStatusUser(id,true);
            }

            result.close();
            statement.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //0-connected and paid
    //1-connected and unpaid
    //2-not connected
    //3-error
    public int checkConnectionStatus(int idUser, int idTariff){
        try(Connection con = DBManager.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement("SELECT * FROM internetprovider.tariffconnected where idUser = ? and idTariff = ? and connection_status = 1");


            statement.setInt(1,idUser);
            statement.setInt(2,idTariff);

            Boolean paid = null;
            String str = "";
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                paid = result.getString("date_end_connection") == null;
                str = result.getString("date_end_connection");
            }
            logger.info("Connection status: " + paid + "|User " + idUser + "|Tariff " + idTariff);
            System.out.println(str);
            System.out.println(paid?0:1);
            if(paid != null)return paid?0:1;
            else return 2;
        }catch (Exception e){
            e.printStackTrace();
            return 3;
        }
    }
}

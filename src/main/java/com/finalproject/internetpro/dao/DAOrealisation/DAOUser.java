package com.finalproject.internetpro.dao.DAOrealisation;

import com.finalproject.internetpro.dao.DAO;
import com.finalproject.internetpro.database.Database;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.*;
/**
 * A class that implement DAO-pattern for interaction with database(MySQL)
 * @see com.finalproject.internetpro.model.Service
 * @see com.finalproject.internetpro.database.Database
 */
public class DAOUser implements DAO<User> {
    static final Logger logger = Logger.getLogger(DAOUser.class);

    /**
     * Selected User by id
     * @param id User`s id
     * @return returning User witch could be null
     */
    @Override
    public Optional<User> get(long id){

        User user = null;
        try {
            String sql = "SELECT * from Users where id = ?";
            String sql2 = "SELECT tariff.id " +
                    "FROM tariffconnected " +
                    "inner join tariff on tariff.id = tariffconnected.idTariff " +
                    "where tariffconnected.idUser = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            PreparedStatement statement2 = con.prepareStatement(sql2);

            statement.setLong(1, id);
            statement2.setLong(1, id);

            ResultSet result = statement.executeQuery();
            ResultSet result2 = statement2.executeQuery();
            user = new User();
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
                user.setSpecialAccess(result.getBoolean(9));
            }
            while (result2.next()) {
                Tariff tariff = new DAOTariff().get(result2.getInt(1)).get();
                if (tariff != null)
                    tariffs.add(tariff);
            }

            user.setTariffs(tariffs);

            if(user.getId()==0)
                user = null;

            logger.info("get|"+id);
        }catch (Exception e){
            logger.error("get|ERROR:"+e);
        }
        return Optional.ofNullable(user);
    }

    /**
     * Selecting All Users from database
     * @return returning List of Users from database
     */
    @Override
    public List<User> getAll(){
        List<User> users = null;
        try {
            String sql = "SELECT id from Users";
            logger.info("Start getALL");

            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            DAOUser daoUser = new DAOUser();
            users = new LinkedList<>();
            while (result.next())
                users.add(daoUser.get(result.getInt(1)).get());

            logger.info("End getALL");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return users;
    }

    /**
     * Inserting into database entered User
     * @param user User
     */
    @Override
    public void save(User user){
        try {
            String sql = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setLong(1, user.getId());
            posted.setString(2, user.getName());
            posted.setString(3, user.getSurname());
            posted.setDate(4, user.getDateOfBirth());
            posted.setString(5, user.getEmail());
            posted.setString(6, user.getPassword());
            posted.setDouble(7, user.getBalance());
            posted.setBoolean(8, user.isBlocked());
            posted.setBoolean(9, user.isSpecialAccess());

            posted.executeUpdate();
            logger.info("save|"+user);
        }catch (Exception e){
            logger.error("save|"+e);
        }
    }

    /**
     * Updating in database entered User
     * @param user User
     */
    @Override
    public void update(User user){
        try {
        String sql ="update users " +
                "set name = ?, " +
                "surname = ?, " +
                "dateBirth = ?, " +
                "email = ?, " +
                "password = ?, " +
                "balance = ?, " +
                "blocked = ?, " +
                "specialAccess = ? " +
                "where users.id = ?";

        Connection con = Database.getConnection();
        PreparedStatement posted = con.prepareStatement(sql);

        posted.setString(1,user.getName());
        posted.setString(2,user.getSurname());
        posted.setDate(3,user.getDateOfBirth());
        posted.setString(4,user.getEmail());
        posted.setString(5,user.getPassword());
        posted.setDouble(6,user.getBalance());
        posted.setBoolean(7,user.isBlocked());
        posted.setBoolean(8,user.isSpecialAccess());
        posted.setLong(9,user.getId());

        List<Tariff> tariffs = user.getTariffs();
        for(Tariff tariff : tariffs)
            if(!checkTariff(user.getId(),tariff.getId()))
                connectTariffConnection(user.getId(),tariff.getId());

        posted.executeUpdate();
        logger.info("update|"+user);
        }catch (Exception e) {
            logger.error("update|ERROR:"+e);
        }
    }

    /**
     * Deleting in database the User by id
     * @param id User`s id
     */
    @Override
    public void delete(int id){
        try {
            String sql = "DELETE from TariffConnected where idUser = ?";
            String sql2 = "DELETE from users WHERE id = ?";

            Connection con = Database.getConnection();

            PreparedStatement posted = con.prepareStatement(sql);
            PreparedStatement posted2 = con.prepareStatement(sql2);

            posted.setLong(1, id);
            posted2.setLong(1, id);

            posted.executeUpdate();
            posted2.executeUpdate();

            logger.info("delete|"+id);
        } catch(Exception e ) {
            logger.error("delete|ERROR:"+e);
        }
    }

    /**
     * If User exist and all entered data are correct - return id
     * @param email User`s email
     * @param password User`s password
     * @return returning User`s id if all enter data are correct
     */
    public Integer loggingUser(String email,String password) {
        try {
        String sql = "SELECT id from Users where email = ? and password = ?";
        Connection con = Database.getConnection();
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1,email);
        statement.setString(2,password);

        ResultSet result = statement.executeQuery();
        Integer res = null;
            
        while (result.next())
            res = result.getInt(1);

        logger.error("loggingUser|"+email+"|"+password);
        return res;
        }catch (Exception e){
            logger.error("loggingUser|ERROR:"+e);
        }
        return null;
    }



    /**
     * Checking does the tariff is connected to the User
     * @param idUser User`s id
     * @param idTariff Tariff`s id
     * @return returning boolean does the tariff is connected to the User
     */
    public boolean checkTariff(int idUser, int idTariff)
    {
        boolean chT = false;
        try {
            String sql = "SELECT * from TariffConnected where idUser = ? and idTariff = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1,idUser);
            statement.setInt(2,idTariff);

            ResultSet result = statement.executeQuery();
            chT = result.next();

            logger.info("checkTariff|"+idUser+"|"+idTariff);

            return chT;
        }catch (Exception e){
            logger.error("checkTariff|ERROR:"+e);
        }
        return chT;
    }

    /**
     * Connecting a tariff from User account
     * @param idUser User`s id
     * @param idTariff Tariff`s id
     */
    public void deleteTariffConnection(int idUser,int idTariff)
    {
        try {
            String sql = "DELETE from TariffConnected where idUser = ? and idTariff = ?";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setLong(1, idUser);
            posted.setLong(2, idTariff);

            posted.executeUpdate();

            logger.info("deleteTariffConnection|"+idUser+"|"+idTariff);
        } catch(Exception e ) {
            logger.error("deleteTariffConnection|ERROR:"+e);
        }
    }

    /**
     * Connecting a tariff to User account
     * @param idUser User`s id
     * @param idTariff Tariff`s id
     */
    public void connectTariffConnection(int idUser,int idTariff)
    {
        try {
            String sql = "INSERT INTO TariffConnected value(?,?,?)";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            posted.setLong(1, idUser);
            posted.setLong(2, idTariff);
            posted.setString(3,formatter.format(date));

            posted.executeUpdate();

            logger.info("connectTariffConnection|"+idUser+"|"+idTariff);
        } catch(Exception e ) {
            logger.error("connectTariffConnection|ERROR:"+e);
        }
    }

    /**
     * Set User`s block status
     * @param id User`s id
     * @param block User`s block status
     */
    public void blockStatusUser(int id, boolean block){
        try {
            String sql ="update users set blocked = ? where users.id = ?";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setBoolean(1,block);
            posted.setLong(2,id);

            posted.executeUpdate();

            logger.info("blockStatusUser|"+id+"|"+block);
        }catch (Exception e) {
            logger.error("blockStatusUser|ERROR:"+e);
        }
    }
}

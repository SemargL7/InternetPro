package com.finalproject.internetpro.dao.DAOrealisation;

import com.finalproject.internetpro.dao.DAO;
import com.finalproject.internetpro.database.Database;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.*;

public class DAOUser implements DAO<User> {
    static final Logger logger = Logger.getLogger(DAOUser.class);

    private DAOUser instance;

    public DAOUser getInstance(){
        if(instance == null)
            instance = new DAOUser();
        return instance;
    }

    public DAOUser() {
    }

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

    public void updateAllUsersBalances(){
        try {
            String sql ="update internetprovider.Users " +
                    "inner join  internetprovider.TariffConnected on TariffConnected.idUser = Users.id " +
                    "inner join  internetprovider.Tariff on Tariff.id = TariffConnected.idTariff " +
                    "set Users.blocked = case " +
                        "when (Users.balance < Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then true " +
                        "when (Users.balance >= Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then false " +
                        "else false end, " +
                    "Users.balance = case " +
                        "when (Users.balance < Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then balance " +
                        "when (Users.balance >= Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then balance - cost " +
                        "else balance end, " +
                    "TariffConnected.dateOfLastConnection = case " +
                        "when (Users.balance < Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then dateOfLastConnection " +
                        "when (Users.balance >= Tariff.cost and now() - dateOfLastConnection > Tariff.daysOfTariff) then now() " +
                        "else dateOfLastConnection end where Users.id = ?;";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            List<User> userList = getAll();
            userList.forEach(x->{
                try {
                    posted.setInt(1,x.getId());
                    posted.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            logger.info("updateAllBalances");
        }catch (Exception e) {
            logger.error("blockStatusUser|ERROR:"+e);
        }
    }
    public boolean mailFree(String email){
        try {
            String sql = "SELECT id from Users where email = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1,email);

            ResultSet result = statement.executeQuery();
            Integer res = null;

            while (result.next())
                res = result.getInt(1);

            logger.error("mailFree|"+email);
            return (res == null);
        }catch (Exception e){
            logger.error("mailFree|ERROR:"+e);
        }
        return false;
    }
}

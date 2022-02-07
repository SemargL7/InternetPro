package com.finalproject.internetpro.dao.DAOrealisation;

import com.finalproject.internetpro.dao.DAO;
import com.finalproject.internetpro.database.Database;
import com.finalproject.internetpro.model.Tariff;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * DAO class for Tariff
 * The Data Access Object (DAO) pattern is a structural pattern that allows us
 * to isolate the application/business layer from the persistence layer
 * (usually a relational database but could be any other persistence mechanism) using an abstract API.
 * Using Singleton pattern
 * @see Tariff
 */
public class DAOTariff implements DAO<Tariff> {
    private static final Logger logger = Logger.getLogger(DAOTariff.class);

    private static final String SQL_GET_TARIFF_BY_ID = "SELECT id, idService, cost, daysOfTariff FROM Tariff  WHERE id = ?";
    private static final String SQL_GET_TARIFF_DESCRIPTION = "SELECT idLanguage, description.description_text FROM description WHERE idTariff = ?";
    private static final String SQL_GET_ALL_TARIFF_ID = "SELECT id FROM Tariff";
    private static final String SQL_INSERT_TARIFF = "INSERT INTO tariff VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_LAST_TARIFF_ID = "SELECT * FROM tariff ORDER BY id DESC LIMIT 1";
    private static final String SQL_INSERT_TARIFF_DESCRIPTION = "INSERT INTO description VALUES (?,?,?)";
    private static final String SQL_UPDATE_TARIFF = "UPDATE tariff SET idService = ?, cost = ?, daysOfTariff = ? WHERE tariff.id = ?";
    private static final String SQL_UPDATE_TARIFF_DESCRIPTION = "UPDATE description SET description_text = ? WHERE idTariff = ? AND idLanguage = ?";
    private static final String SQL_DELETE_TARIFF_DESCRIPTION = "DELETE FROM description WHERE description.idTariff = ?";
    private static final String SQL_DELETE_TARIFF_CONNECTIONS = "DELETE FROM tariffconnected WHERE tariffconnected.idTariff = ?";
    private static final String SQL_DELETE_TARIFF = "DELETE FROM Tariff WHERE id = ?";



    private static DAOTariff instance;

    public static DAOTariff getInstance(){
        if(instance == null)
            instance = new DAOTariff();
        return instance;
    }

    private DAOTariff() {
    }
    /**
     * Function is returning Tariff by id
     * @param id Tariff`s id
     * @return Optional of Tariff which we find
     */
    @Override
    public Optional<Tariff> get(long id){
        Tariff tariff = null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_TARIFF_BY_ID);
            PreparedStatement statement2 = con.prepareStatement(SQL_GET_TARIFF_DESCRIPTION);

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            tariff = new Tariff();

            while (result.next()) {
                tariff.setId(result.getInt(1));
                tariff.setCost(result.getDouble(3));
                tariff.setDaysOfTariff(result.getInt(4));
                DAOService daoService = DAOService.getInstance();
                tariff.setService(daoService.get(result.getInt(2)).get());
            }

            //Selecting description for this tariff
            statement2.setLong(1, id);
            ResultSet result2 = statement2.executeQuery();

            Map<Integer, String> disc = new HashMap<>();

            while (result2.next()) {
                disc.put(result2.getInt(1), result2.getString(2));
            }
            if(!disc.isEmpty())
                tariff.setDescription(disc);
            if(tariff.getId()==0)
                tariff = null;

            result.close();
            result2.close();
            statement.close();
            statement2.close();
            con.close();

            logger.info("get|"+id);
        }catch (Exception e){
            logger.error("get|ERROR:"+e);
        }
        return Optional.ofNullable(tariff);
    }

    /**
     * Function is returning list of all Tariffs
     * @return list of all Tariffs
     */
    @Override
    public List<Tariff> getAll(){
        List<Tariff> tariffs = null;
        try {
            logger.info("Start getAll");
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_ALL_TARIFF_ID);
            ResultSet result = statement.executeQuery();
            DAOTariff daoTariff = new DAOTariff();
            tariffs = new LinkedList<>();
            while (result.next())
                tariffs.add(daoTariff.get(result.getInt(1)).get());

            result.close();
            statement.close();
            con.close();

            logger.info("End getAll");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return tariffs;
    }
    /**
     * Function is inserting a new Tariff into database
     * @param tariff Tariff which we want to insert
     * @return boolean, if insert is successful - true, and else - false
     */
    @Override
    public boolean save(Tariff tariff){
        try {
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(SQL_INSERT_TARIFF);
            PreparedStatement posted2 = con.prepareStatement(SQL_INSERT_TARIFF_DESCRIPTION);
            PreparedStatement statement = con.prepareStatement(SQL_GET_LAST_TARIFF_ID);

            posted.setLong(1, tariff.getId());
            posted.setInt(2, tariff.getService().getId());
            posted.setDouble(3, tariff.getCost());
            posted.setInt(4, tariff.getDaysOfTariff());

            posted.executeUpdate();

            //Getting tariff`s id
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                tariff.setId(result.getInt(1));
            }

            //Inserting description for tariff
            for (int i = 1; i <= tariff.getDescription().size(); i++) {

                posted2.setInt(1, i);
                posted2.setInt(2, tariff.getId());
                posted2.setString(3, tariff.getDescription().get(i));

                posted2.executeUpdate();
            }

            result.close();
            posted.close();
            posted2.close();
            statement.close();
            con.close();

            logger.info("save|"+tariff);
            return true;
        }catch (Exception e){
            logger.error("save|ERROR:"+e);
            return false;
        }
    }
    /**
     * Function is setting new data into the Tariff
     * @param tariff Tariff with new data
     * @return boolean, if update is successful - true, and else - false
     */
    @Override
    public boolean update(Tariff tariff){
        try {
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(SQL_UPDATE_TARIFF);
            PreparedStatement posted2 = con.prepareStatement(SQL_UPDATE_TARIFF_DESCRIPTION);

            posted.setInt(1, tariff.getService().getId());
            posted.setDouble(2, tariff.getCost());
            posted.setInt(3, tariff.getDaysOfTariff());
            posted.setLong(4, tariff.getId());

            posted.executeUpdate();

            //updating tariff`s description
            for (int i = 1; i <= tariff.getDescription().size(); i++) {
                posted2.setString(1, tariff.getDescription().get(i));
                posted2.setInt(2, tariff.getId());
                posted2.setInt(3, i);

                posted2.executeUpdate();
            }

            posted.close();
            posted2.close();
            con.close();

            logger.info("update|"+tariff);
            return true;
        }catch (Exception e){
            logger.error("update|ERROR:"+e);
            return false;
        }

    }
    /**
     * Function is deleting the Tariff
     * @param id Tariff`s id
     * @return boolean, if delete is successful - true, and else - false
     */
    @Override
    public boolean delete(int id){
        try {
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(SQL_DELETE_TARIFF_DESCRIPTION);
            PreparedStatement posted2 = con.prepareStatement(SQL_DELETE_TARIFF_CONNECTIONS);
            PreparedStatement posted3 = con.prepareStatement(SQL_DELETE_TARIFF);

            posted.setLong(1, id);
            posted.executeUpdate();

            posted2.setLong(1, id);
            posted2.executeUpdate();

            posted3.setLong(1, id);
            posted3.executeUpdate();

            posted.close();
            posted2.close();
            posted3.close();
            con.close();

            logger.info("delete|"+id);
            return true;
        }catch (Exception e){
            logger.error("delete|ERROR:"+e);
            return false;
        }
    }
}

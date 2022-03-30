package com.finalproject.internetpro.database.dao.DAOrealisation;

import com.finalproject.internetpro.database.dao.DAO;
import com.finalproject.internetpro.database.DBManager;
import com.finalproject.internetpro.entity.Service;
import com.finalproject.internetpro.entity.Tariff;
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

    private static final String SQL_GET_TARIFF_BY_ID = "SELECT id, cost, daysOfTariff FROM Tariff  WHERE id = ?";
    private static final String SQL_GET_TARIFF_SERVICES = "SELECT idService FROM Services WHERE idTariff = ?";

    private static final String SQL_GET_TARIFF_DESCRIPTION = "SELECT idLanguage, description.description_text FROM description WHERE idTariff = ?";
    private static final String SQL_GET_ALL_TARIFF_ID = "SELECT id FROM Tariff";
    private static final String SQL_INSERT_TARIFF = "INSERT INTO tariff VALUES (?, ?, ?)";
    private static final String SQL_GET_LAST_TARIFF_ID = "SELECT * FROM tariff ORDER BY id DESC LIMIT 1";
    private static final String SQL_INSERT_TARIFF_DESCRIPTION = "INSERT INTO description VALUES (?,?,?)";
    private static final String SQL_UPDATE_TARIFF = "UPDATE tariff SET cost = ?, daysOfTariff = ? WHERE tariff.id = ?";
    private static final String SQL_UPDATE_TARIFF_DESCRIPTION = "UPDATE description SET description_text = ? WHERE idTariff = ? AND idLanguage = ?";

    private static final String SQL_ADD_SERVICE = "INSERT INTO Services VALUE (?,?)";
    private static final String SQL_DELETE_SERVICE = "DELETE FROM Services WHERE idService = ? AND idTariff = ?";

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
        Connection con = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        ResultSet result = null;
        ResultSet result2 = null;
        ResultSet result3 = null;
        try{
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            statement = con.prepareStatement(SQL_GET_TARIFF_BY_ID);
            statement2 = con.prepareStatement(SQL_GET_TARIFF_DESCRIPTION);
            statement3 = con.prepareStatement(SQL_GET_TARIFF_SERVICES);

            statement.setLong(1, id);
            result = statement.executeQuery();

            tariff = new Tariff();

            while (result.next()) {
                tariff.setId(result.getInt(1));
                tariff.setCost(result.getDouble(2));
                tariff.setDaysOfTariff(result.getInt(3));
            }


            statement3.setLong(1, id);
            result3 = statement3.executeQuery();
            List<Service> services = new LinkedList<>();
            while (result3.next()) {
                Integer idService = result3.getInt(1);
                if(idService != null){
                    DAOService.getInstance().get(idService).ifPresent(services::add);
                }
            }
            tariff.setService(services);


            //Selecting description for this tariff
            statement2.setLong(1, id);
            result2 = statement2.executeQuery();

            Map<Integer, String> disc = new HashMap<>();

            while (result2.next()) {
                disc.put(result2.getInt(1), result2.getString(2));
            }
            if(!disc.isEmpty())
                tariff.setDescription(disc);
            if(tariff.getId()==0)
                tariff = null;

            DBManager.getInstance().commitAndClose(con);

            logger.info("get|"+id);
        }catch (Exception e){
            if(con!=null)
                DBManager.getInstance().rollbackAndClose(con);
            logger.error("get|ERROR:"+e);
            e.printStackTrace();
        }finally {
            DBManager.getInstance().tryClose(result);
            DBManager.getInstance().tryClose(result2);
            DBManager.getInstance().tryClose(result3);
            DBManager.getInstance().tryClose(statement);
            DBManager.getInstance().tryClose(statement2);
            DBManager.getInstance().tryClose(statement3);
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
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_ALL_TARIFF_ID)) {

            logger.info("Start getAll");
            ResultSet result = statement.executeQuery();
            tariffs = new LinkedList<>();
            while (result.next())
                tariffs.add(get(result.getInt(1)).get());

            result.close();

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
        Connection con = null;
        PreparedStatement posted = null;
        PreparedStatement posted2 = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            posted = con.prepareStatement(SQL_INSERT_TARIFF);
            posted2 = con.prepareStatement(SQL_INSERT_TARIFF_DESCRIPTION);
            statement = con.prepareStatement(SQL_GET_LAST_TARIFF_ID);

            posted.setLong(1, tariff.getId());
            posted.setDouble(2, tariff.getCost());
            posted.setInt(3, tariff.getDaysOfTariff());

            posted.executeUpdate();

            //Getting tariff`s id
            result = statement.executeQuery();
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


            DBManager.getInstance().commitAndClose(con);
            logger.info("save|"+tariff);
            return true;
        }catch (Exception e){
            if(con != null)
                DBManager.getInstance().rollbackAndClose(con);
            logger.error("save|ERROR:"+e);
            return false;
        }finally {
            DBManager.getInstance().tryClose(result);
            DBManager.getInstance().tryClose(statement);
            DBManager.getInstance().tryClose(posted);
            DBManager.getInstance().tryClose(posted2);
        }
    }
    /**
     * Function is setting new data into the Tariff
     * @param tariff Tariff with new data
     * @return boolean, if update is successful - true, and else - false
     */
    @Override
    public boolean update(Tariff tariff){
        Connection con = null;
        PreparedStatement posted = null;
        PreparedStatement posted2 = null;
        try{
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);

            posted = con.prepareStatement(SQL_UPDATE_TARIFF);
            posted2 = con.prepareStatement(SQL_UPDATE_TARIFF_DESCRIPTION);

            posted.setDouble(1, tariff.getCost());
            posted.setInt(2, tariff.getDaysOfTariff());
            posted.setLong(3, tariff.getId());

            posted.executeUpdate();

            //updating tariff`s description
            for (int i = 1; i <= tariff.getDescription().size(); i++) {
                posted2.setString(1, tariff.getDescription().get(i));
                posted2.setInt(2, tariff.getId());
                posted2.setInt(3, i);

                posted2.executeUpdate();
            }

            DBManager.getInstance().commitAndClose(con);

            logger.info("update|"+tariff);
            return true;
        }catch (Exception e){
            if(con != null)
                DBManager.getInstance().rollbackAndClose(con);
            logger.error("update|ERROR:"+e);
            return false;
        }finally {
            DBManager.getInstance().tryClose(posted);
            DBManager.getInstance().tryClose(posted2);
        }

    }

    public boolean addService(int idTariff, int idService){
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement post = con.prepareStatement(SQL_ADD_SERVICE)){
            post.setInt(1,idService);
            post.setInt(2,idTariff);
            post.executeUpdate();

            return true;
        }catch (Exception e){
            logger.error(e);
            return false;
        }
    }

    public boolean deleteService(int idTariff, int idService){
        try(Connection con = DBManager.getInstance().getConnection();
            PreparedStatement post = con.prepareStatement(SQL_DELETE_SERVICE)){

            post.setInt(1,idService);
            post.setInt(2,idTariff);
            post.executeUpdate();

            return true;
        }catch (Exception e){
            logger.error(e);
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
        try(Connection con = DBManager.getInstance().getConnection();
        PreparedStatement posted = con.prepareStatement(SQL_DELETE_TARIFF);) {
            posted.setLong(1, id);
            posted.executeUpdate();
            logger.info("delete|"+id);
            return true;
        }catch (Exception e){
            logger.error("delete|ERROR:"+e);
            return false;
        }
    }
}

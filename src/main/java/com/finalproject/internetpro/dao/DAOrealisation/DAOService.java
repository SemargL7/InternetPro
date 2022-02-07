package com.finalproject.internetpro.dao.DAOrealisation;

import com.finalproject.internetpro.dao.DAO;
import com.finalproject.internetpro.database.Database;
import com.finalproject.internetpro.model.Service;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * DAO class for Service
 * The Data Access Object (DAO) pattern is a structural pattern that allows us
 * to isolate the application/business layer from the persistence layer
 * (usually a relational database but could be any other persistence mechanism) using an abstract API.
 * Using Singleton pattern
 * @see Service
 */
public class DAOService implements DAO<Service> {
    private static final Logger logger = Logger.getLogger(DAOService.class);

    private static final String SQL_GET_SERVICE_BY_ID = "SELECT * from Services where id = ?";
    private static final String SQL_GET_ALL_SERVICES = "SELECT * from Services";
    private static final String SQL_INSERT_SERVICE = "INSERT INTO Services VALUES (?, ?)";
    private static final String SQL_UPDATE_SERVICE = "UPDATE Services SET name = ? WHERE Services.id = ?";
    private static final String SQL_GET_SERVICE_TARIFFS = "SELECT id FROM Tariff where idService = ?";
    private static final String SQL_DELETE_SERVICE = "DELETE FROM Services where Services.id = ?";


    private static DAOService instance;

    public static DAOService getInstance(){
        if(instance == null)
            instance = new DAOService();
        return instance;
    }

    private DAOService() {
    }

    /**
     * Function is returning Service by id
     * @param id Service`s id
     * @return Optional of Service which we find
     */
    @Override
    public Optional<Service> get(long id){
        Service service = null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_SERVICE_BY_ID);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            service = new Service();
            while (result.next()) {
                service.setId(result.getInt(1));
                service.setServiceName(result.getString(2));
            }

            if(service.getId()==0 && service.getServiceName()==null)
                service=null;

            result.close();
            statement.close();
            con.close();

            logger.info("get|"+id);
        }catch (Exception e)
        {
            logger.error("get|ERROR:"+e);
        }
        return Optional.ofNullable(service);
    }

    /**
     * Function is returning list of all Services
     * @return list of all Services
     */
    @Override
    public List<Service> getAll(){
        List<Service> services = null;
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_ALL_SERVICES);
            ResultSet result = statement.executeQuery();
            services = new ArrayList<>();
            while (result.next())
            {
                Service service = new Service();
                service.setId(result.getInt(1));
                service.setServiceName(result.getString(2));
                services.add(service);
            }

            result.close();
            statement.close();
            con.close();

            logger.error("getAll");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return services;
    }

    /**
     * Function is inserting a new Service into database
     * @param service Service which we want to insert
     * @return boolean, if service is successful saved - true, and else - false
     */
    @Override
    public boolean save(Service service){
        try {
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(SQL_INSERT_SERVICE);
            posted.setInt(1,service.getId());
            posted.setString(2, service.getServiceName());

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("save|"+service);
            return true;
        }catch (Exception e){
            logger.error("save|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is setting new data into the Service
     * @param service Service with new data
     * @return boolean, if service is successful updated - true, and else - false
     */
    @Override
    public boolean update(Service service){
        try {
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(SQL_UPDATE_SERVICE);
            posted.setString(1, service.getServiceName());
            posted.setLong(2,service.getId());

            posted.executeUpdate();

            posted.close();
            con.close();

            logger.info("update|"+service);
            return true;
        }catch (Exception e){
            logger.error("update|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is deleting the Service and all Tariffs with this Service
     * @param id Service`s id
     * @return boolean, if service is successfully deleted - true, and else - false
     */
    @Override
    public boolean delete(int id){
        try {
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_SERVICE_TARIFFS);
            PreparedStatement posted = con.prepareStatement(SQL_DELETE_SERVICE);

            statement.setLong(1, id);
            posted.setInt(1, id);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DAOTariff.getInstance().delete(result.getInt(1));
            }

            posted.executeUpdate();

            result.close();
            statement.close();
            posted.close();
            con.close();

            logger.info("delete|"+id);
            return true;
        }catch (Exception e){
            logger.error("delete|ERROR:"+e);
            return false;
        }
    }

}

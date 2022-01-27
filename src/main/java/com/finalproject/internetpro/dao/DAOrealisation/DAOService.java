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
    static final Logger logger = Logger.getLogger(DAOService.class);

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
            String sql = "SELECT * from Services where id = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            service = new Service();
            while (result.next()) {
                service.setId(result.getInt(1));
                service.setServiceName(result.getString(2));
            }

            if(service.getId()==0 && service.getServiceName()==null)
                service=null;

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
            String sql = "SELECT * from Services";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            services = new ArrayList<>();
            while (result.next())
            {
                Service service = new Service();
                service.setId(result.getInt(1));
                service.setServiceName(result.getString(2));
                services.add(service);
            }
            logger.error("getAll");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return services;
    }

    /**
     * Function is inserting a new Service into database
     * @param service Service which we want to insert
     * @return
     */
    @Override
    public boolean save(Service service){
        try {
            String sql = "INSERT INTO Services VALUES (?, ?)";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setInt(1,service.getId());
            posted.setString(2, service.getServiceName());

            posted.executeUpdate();

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
     */
    @Override
    public boolean update(Service service){
        try {
            String sql = "UPDATE Services SET name = ? WHERE Services.id = ?";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setString(1, service.getServiceName());
            posted.setLong(2,service.getId());

            posted.executeUpdate();

            logger.info("update|"+service);
            return true;
        }catch (Exception e){
            logger.error("update|ERROR:"+e);
            return false;
        }
    }

    /**
     * Function is deleting the Service
     * @param id Service`s id
     * @return
     */
    @Override
    public boolean delete(int id){
        try {
            String sql1 = "SELECT id FROM Tariff where idService = ?";
            String sql2 = "DELETE FROM Services where Services.id = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql1);
            PreparedStatement posted = con.prepareStatement(sql2);

            statement.setLong(1, id);
            posted.setInt(1, id);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DAOTariff.getInstance().delete(result.getInt(1));
            }

            posted.executeUpdate();

            logger.info("delete|"+id);
            return true;
        }catch (Exception e){
            logger.error("delete|ERROR:"+e);
            return false;
        }
    }

}

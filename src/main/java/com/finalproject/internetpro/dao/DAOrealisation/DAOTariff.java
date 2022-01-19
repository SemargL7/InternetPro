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
 * A class that implement DAO pattern for interaction with database(MySQL)
 * @see com.finalproject.internetpro.model.Service
 * @see com.finalproject.internetpro.database.Database
 */
public class DAOTariff implements DAO<Tariff> {
    static final Logger logger = Logger.getLogger(DAOService.class);

    /**
     * Selected Tariff by id
     * @param id Tariff`s id
     * @return returning Tariff witch could be null
     */
    @Override
    public Optional<Tariff> get(long id){
        Tariff tariff = null;
        try {
            String sql = "SELECT id, idService, cost, daysOfTariff from Tariff  where id = ?";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            tariff = new Tariff();

            while (result.next()) {
                tariff.setId(result.getInt(1));
                tariff.setCost(result.getDouble(3));
                tariff.setDaysOfTariff(result.getInt(4));
                DAOService daoService = new DAOService();
                tariff.setService(daoService.get(result.getInt(2)).get());
            }

            String sql2 = "select idLanguage, description.description_text " +
                    "from description " +
                    "where idTariff = ?";
            PreparedStatement statement2 = con.prepareStatement(sql2);
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
            logger.info("get|"+id);
        }catch (Exception e){
            logger.error("get|ERROR:"+e);
        }
        return Optional.ofNullable(tariff);
    }

    /**
     * Selecting All Tariffs from database
     * @return returning List of Tariffs from the database
     */
    @Override
    public List<Tariff> getAll(){
        List<Tariff> tariffs = null;
        try {
            logger.info("Start getAll");
            String sql = "SELECT id from Tariff";
            Connection con = Database.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            DAOTariff daoTariff = new DAOTariff();
            tariffs = new LinkedList<>();
            while (result.next())
                tariffs.add(daoTariff.get(result.getInt(1)).get());
            logger.info("End getAll");
        }catch (Exception e){
            logger.error("getAll|ERROR:"+e);
        }
        return tariffs;
    }

    /**
     * Inserting the tariff in the database
     * @param tariff Tariff
     */
    @Override
    public void save(Tariff tariff){
        try {
            Connection con = Database.getConnection();

            String sql = "INSERT INTO tariff VALUES (?, ?, ?, ?)";
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setLong(1, tariff.getId());
            posted.setInt(2, tariff.getService().getId());
            posted.setDouble(3, tariff.getCost());
            posted.setInt(4, tariff.getDaysOfTariff());


            posted.executeUpdate();

            String sql2 = "select *from tariff ORDER BY id DESC LIMIT 1";
            Connection con2 = Database.getConnection();
            PreparedStatement statement2 = con.prepareStatement(sql2);
            ResultSet result = statement2.executeQuery();

            while (result.next()) {
                tariff.setId(result.getInt(1));
            }


            for (int i = 1; i <= tariff.getDescription().size(); i++) {
                String sql3 = "INSERT INTO description VALUES (?,?,?)";
                PreparedStatement posted3 = con.prepareStatement(sql3);

                posted3.setInt(1, i);
                posted3.setInt(2, tariff.getId());
                posted3.setString(3, tariff.getDescription().get(i));

                posted3.executeUpdate();
            }
            logger.info("save|"+tariff);
        }catch (Exception e){
            logger.error("save|ERROR:"+e);
        }
    }

    /**
     * Updating the tariff in the database
     * @param tariff Tariff
     */
    @Override
    public void update(Tariff tariff){
        try {
            String sql = "update tariff set " +
                    "idService = ?, " +
                    "cost = ?, " +
                    "daysOfTariff = ? " +
                    "where tariff.id = ?";
            Connection con = Database.getConnection();
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setInt(1, tariff.getService().getId());
            posted.setDouble(2, tariff.getCost());
            posted.setInt(3, tariff.getDaysOfTariff());
            posted.setLong(4, tariff.getId());

            posted.executeUpdate();

            for (int i = 1; i <= tariff.getDescription().size(); i++) {
                String sql2 = "update description " +
                        "set description_text = ? " +
                        "where idTariff = ? and idLanguage = ?";
                PreparedStatement posted2 = con.prepareStatement(sql2);

                posted2.setString(1, tariff.getDescription().get(i));
                posted2.setInt(2, tariff.getId());
                posted2.setInt(3, i);

                posted2.executeUpdate();
            }
            logger.info("update|"+tariff);
        }catch (Exception e){
            logger.error("update|ERROR:"+e);
        }

    }

    /**
     * Deleting the tariff by id in the database
     * @param id Tariff`s id
     */
    @Override
    public void delete(int id){
        try {
            Connection con = Database.getConnection();
            String sql = "DELETE from description where description.idTariff = ?";
            PreparedStatement posted = con.prepareStatement(sql);

            posted.setLong(1, id);

            posted.executeUpdate();

            String sql2 = "DELETE from tariffconnected where tariffconnected.idTariff = ?";
            PreparedStatement posted2 = con.prepareStatement(sql2);

            posted2.setLong(1, id);

            posted2.executeUpdate();


            String sql3 = "DELETE from Tariff where id = ?";

            PreparedStatement posted3 = con.prepareStatement(sql3);

            posted3.setLong(1, id);

            posted3.executeUpdate();

            logger.info("delete|"+id);
        }catch (Exception e){
            logger.error("delete|ERROR:"+e);
        }
    }
}

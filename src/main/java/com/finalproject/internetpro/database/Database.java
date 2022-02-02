package com.finalproject.internetpro.database;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that creating Database Connection
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 */
public class Database {
    static final Logger logger = Logger.getLogger(Database.class);

    /**
     * @return returning database connection
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/internetprovider";
        String username = "root";
        String password = "Elias130720031567";
        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url,username,password);
        logger.info("Connect with database MySQL");
        return conn;
    }
}

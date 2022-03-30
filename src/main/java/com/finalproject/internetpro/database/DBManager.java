package com.finalproject.internetpro.database;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that creating Database Connection
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 */
public class DBManager {
    static final Logger logger = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");

            // MyDatabase - the name of data source
            DataSource ds = (DataSource)envContext.lookup("jdbc/MyDatabase");
            con = ds.getConnection();
        } catch (NamingException ex) {
            logger.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }


    private DBManager() {
    }

    /**
     * Commits and close the given connection.
     *
     * @param con Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.setAutoCommit(true);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.setAutoCommit(true);
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public <T> void tryClose(T closeable){
        if(closeable != null){
            try {
                if(closeable instanceof Closeable)
                    ((Closeable) closeable).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

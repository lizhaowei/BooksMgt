package org.lzw.booksmgt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.lzw.booksmgt.conf.ConfigurationHandler;
import org.lzw.booksmgt.utils.BookConst;

/**
 * created by zwli on Sep 11, 2013 Detailed comment
 */
public class ConnectionFactory {

    private static Logger log = Logger.getLogger(ConnectionFactory.class);
    private static ConnectionFactory factory = null;
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/books_mgt";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";

    private static void loadDatabaseResource() {
        DRIVER = ConfigurationHandler.getInstance().getValue(BookConst.DATABASE_DRIVER);
        URL = ConfigurationHandler.getInstance().getValue(BookConst.DATABASE_URL);
        USERNAME = ConfigurationHandler.getInstance().getValue(BookConst.DATABASE_USERNAME);
        PASSWORD = ConfigurationHandler.getInstance().getValue(BookConst.DATABASE_PASSWORD);
    }

    private ConnectionFactory(String driver) {
        loadDatabaseResource();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }

    public static ConnectionFactory getInstance() {
        if (factory == null) {
            factory = new ConnectionFactory(DRIVER);
        }
        return factory;
    }
}

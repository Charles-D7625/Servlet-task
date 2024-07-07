package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

public class JdbcConnection implements Serializable {
    
    private static final String APPLICATION_PROPERTIES_FILENAME = "application.properties"; // Название файла с данными о базе данных

    private transient Logger logger = Logger.getLogger(getClass().getName());

    public Connection connectionToPostgresDB() throws SQLException {

        Connection connection = null;

        Properties props = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILENAME)) {
            
            if (input == null) {
                logger.info("No such a file in resources: " + APPLICATION_PROPERTIES_FILENAME);
                return null;
            }

            props.load(input);

            String url = props.getProperty("application.url");
            String dbName = props.getProperty("application.dbname");
            String username = props.getProperty("application.username");
            String password = props.getProperty("application.password");

            
            if (connection == null || connection.isClosed()) {
                
                Class.forName(props.getProperty("application.driver"));

                
                connection = DriverManager.getConnection(url + "/" + dbName, username, password);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
        
        return connection;
    }

}

package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcConnection {
    
    private static final String APPLICATION_PROPERTIES_FILENAME = "application.properties"; // Название файла с данными о базе данных

    public static Connection connectionToPostgresDB() throws Exception {

        Connection connection = null;

        Properties props = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILENAME)) {
            
            if (input == null) {
                System.out.println("No such a file in resources: " + APPLICATION_PROPERTIES_FILENAME);
                return null;
            }

            props.load(input);

            String url = props.getProperty("application.url");
            String dbName = props.getProperty("application.dbname");
            String username = props.getProperty("application.username");
            String password = props.getProperty("application.password");

            
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName(props.getProperty("application.driver"));

                } catch (ClassNotFoundException e) {
                    throw new Exception(e);
                }
                connection = DriverManager.getConnection(url + "/" + dbName, username, password);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

}

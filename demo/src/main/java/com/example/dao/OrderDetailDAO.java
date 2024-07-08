package com.example.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public List<Integer> getAllOrderIds(Connection connection) throws SQLException {

        List<Integer> orderIds = new ArrayList<>();

        String sqlQuery = "SELECT id" + " FROM orderdetail";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            orderIds.add(id);
        }

        connection.close();

        return orderIds;
    }
}

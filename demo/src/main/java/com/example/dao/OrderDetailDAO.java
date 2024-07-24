package com.example.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.classes.OrderDetail;
import com.example.enums.OrderStatus;

public class OrderDetailDAO {

    public List<OrderDetail> listAllOrderDetail(Connection connection) throws SQLException {

        List<OrderDetail> listOrderDetail = new ArrayList<>();

        String sqlQuery = "SELECT * " + "FROM orderdetail";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {
            
            OrderDetail orderDetail = new OrderDetail();   

            orderDetail.setId(resultSet.getInt(1));
            orderDetail.setOrderStatus(OrderStatus.fromString(resultSet.getString(2)));
            orderDetail.setTotalAmount(resultSet.getDouble(3));

            listOrderDetail.add(orderDetail);
        }

        connection.close();

        return listOrderDetail;
    }

    public boolean insertOrderDetail(Connection connection) throws SQLException {

        
        String sqlQuery = "INSERT INTO orderdetail (total_amount, order_status) VALUES (0, ?)";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        statement.setString(1, OrderStatus.ORDER_ACCEPTED.getStatus());

        boolean rowInserted = statement.executeUpdate() > 0;

        connection.close();

        return rowInserted;
    }


    public boolean updateOrderDetail(OrderDetail orderDetail, Connection connection) throws SQLException {
        
        //Переделать потом под то, чтобы когда добавлял новый Product к OrderDetail вызывался Update для OrderDetail
        String sqlQuery = "UPDATE orderdetail SET total_amount = ?, order_status = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);


        statement.setDouble(1, orderDetail.getTotalAmount());
        statement.setString(2, orderDetail.getOrderStatus().getStatus());
        statement.setInt(3, orderDetail.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;

        connection.close();

        return rowUpdated;
    }

    public boolean deleteOrderDetail(int id, Connection connection) throws SQLException {

        String sqlQuery = "DELETE FROM orderdetail " + "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        boolean rowDelete = statement.executeUpdate() > 0;

        connection.close();

        return rowDelete;
    }

    public OrderDetail getOrderDetail(int id, Connection connection) throws SQLException {

        String sqlQuery = "SELECT * " + "FROM orderdetail WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.first()) return null;

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setId(resultSet.getInt(1));
        orderDetail.setOrderStatus(OrderStatus.fromString(resultSet.getString(2)));
        orderDetail.setTotalAmount(resultSet.getDouble(3));

        connection.close();

        return orderDetail;
    }

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

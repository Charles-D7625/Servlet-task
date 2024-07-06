package com.example.DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.Product;

public class ProductDAO {
    
    public List<Product> listAllProducts(Connection connection) throws Exception {

        List<Product> listProduct = new ArrayList<Product>();

        String sqlQuery = "SELECT * FROM product";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {

            Product product = new Product();

            product.id = resultSet.getInt(1);
            product.name = resultSet.getString(2);
            product.price = resultSet.getDouble(3);
            product.quantity = resultSet.getInt(4);
            product.available = resultSet.getBoolean(5);

            listProduct.add(product);
        }

        return listProduct;
    }
}

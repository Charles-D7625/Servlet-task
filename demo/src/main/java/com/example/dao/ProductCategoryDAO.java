package com.example.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.classes.ProductCategory;
import com.example.enums.CategoryType;

public class ProductCategoryDAO {

    public List<ProductCategory> listAllProductCategory(Connection connection) throws SQLException {

        List<ProductCategory> listProductCategories = new ArrayList<>();

        String sqlQuery = "SELECT * " + "FROM productcategory";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {

            ProductCategory productCategory = new ProductCategory();

            productCategory.setId(resultSet.getInt(1));
            productCategory.setName(resultSet.getString(2));
            productCategory.setType(CategoryType.fromString(resultSet.getString(3)));

            listProductCategories.add(productCategory);
        }

        connection.close();

        return listProductCategories;
    }
}

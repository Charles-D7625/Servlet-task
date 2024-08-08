package com.example.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.classes.Product;
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
            productCategory.setProducts(getProductsByProductCategoryId(resultSet.getInt(1), connection));

            listProductCategories.add(productCategory);
        }

        connection.close();

        return listProductCategories;
    }

    private List<Product> getProductsByProductCategoryId(int categoryId, Connection connection) throws SQLException{

        List<Product> products = new ArrayList<>();
        String sqlQuery = "SELECT p.id, p.name, p.price, p.quantity, p.available " +
                          "FROM product p " +
                          "JOIN product_productcategory pc ON p.id = pc.product_id " +
                          "WHERE pc.category_id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, categoryId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            
            Product product = new Product();

            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setPrice(resultSet.getBigDecimal(3));
            product.setQuantity(resultSet.getInt(4));
            product.setAvailable(resultSet.getBoolean(5));

            products.add(product);
        }

        return products;
    }
}

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

public class ProductDAO {
    
    public List<Product> listAllProducts(Connection connection) throws SQLException {

        List<Product> listProduct = new ArrayList<>();

        String sqlQuery = "SELECT *" + " FROM product";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next()) {

            Product product = new Product();

            product.setId(resultSet.getInt(1));
            product.setName(resultSet.getString(2));
            product.setPrice(resultSet.getBigDecimal(3));
            product.setQuantity(resultSet.getInt(4));
            product.setAvailable(resultSet.getBoolean(5));
            product.setOrderId(resultSet.getInt(6));
            product.setProductCategories(getProductCategoriesByProductId(resultSet.getInt(1), connection));

            listProduct.add(product);
        }

        connection.close();

        return listProduct;
    }

    public boolean insertProduct(Product product, Connection connection) throws SQLException {

        String sqlQuery = "INSERT INTO product (name, price, quantity, available, order_id) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getPrice());
        statement.setInt(3, product.getQuantity());
        statement.setBoolean(4, product.isAvailable());
        statement.setInt(5, product.getOrderId());

        boolean rowInserted = statement.executeUpdate() > 0;
        connection.close();

        return rowInserted;
    }

    public boolean updateProduct(Product product, Connection connection) throws SQLException {

        String sqlQuery = "UPDATE product SET name = ?, price = ?, quantity = ?, available = ?, order_id = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getPrice());
        statement.setInt(3, product.getQuantity());
        statement.setBoolean(4, product.isAvailable());
        statement.setInt(5, product.getOrderId());
        statement.setInt(6, product.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;

        connection.close();

        return rowUpdated;
    }

    public boolean deleteProduct(int id, Connection connection) throws SQLException {

        String sqlQuery = "DELETE FROM product " + "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        boolean rowDelete = statement.executeUpdate() > 0;

        connection.close();

        return rowDelete;
    }

    public Product getProduct(int id, Connection connection) throws SQLException {

        String sqlQuery = "SELECT * " + " FROM product WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(!resultSet.first()) return null;

        Product product = new Product();

        product.setId(resultSet.getInt(1));
        product.setName(resultSet.getString(2));
        product.setPrice(resultSet.getBigDecimal(3));
        product.setQuantity(resultSet.getInt(4));
        product.setAvailable(resultSet.getBoolean(5));
        product.setOrderId(resultSet.getInt(6));

        connection.close();

        return product;
    }

    private List<ProductCategory> getProductCategoriesByProductId(int productId, Connection connection) throws SQLException {

        List<ProductCategory> productCategories = new ArrayList<>();
        String sqlQuery = "SELECT pc.id, pc.name, pc.type " +
                          "FROM productcategory pc " +
                          "JOIN product_productcategory pcc ON pc.id = pcc.category_id " +
                          "WHERE pcc.product_id = ?";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            
            ProductCategory productCategory = new ProductCategory();

            productCategory.setId(resultSet.getInt(1));
            productCategory.setName(resultSet.getString(2));
            productCategory.setType(CategoryType.fromString(resultSet.getString(3)));

            productCategories.add(productCategory);
        }

        return productCategories;
    }
}

package com.example.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.JdbcConnection;
import com.example.classes.ProductCategory;
import com.example.dao.ProductCategoryDAO;
import com.example.dto.ProductCategoryDTO;
import com.example.mappers.ProductCategotyMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product-category/*")
public class ProductCategotyServlet extends HttpServlet {

    private final transient Logger logger = Logger.getLogger(getClass().getName());

    private static transient ProductCategoryDAO productCategoryDAO = new ProductCategoryDAO();
    private static transient JdbcConnection connection = new JdbcConnection();
    private static transient ProductCategotyMapper productCategotyMapper = ProductCategotyMapper.INSTANCE;

    public ProductCategotyServlet() {
        super();
    }

    @Override
    public void init() {
        try {
            connection.connectionToPostgresDB();
            connection.connectionToPostgresDB().close();
        } catch (SQLException e) {
           logger.info("Connection was failed");
           e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        
        String pathInfo = req.getPathInfo();

        try {
            if( pathInfo== null || pathInfo.equals("/")) {
                showAllProductCategories(req, res);
            } else if(pathInfo.equals("/json")) {
                viewProductCategoryAsJson(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
    }

    private void showAllProductCategories(HttpServletRequest req, HttpServletResponse res) {

        List<ProductCategory> listProductCategory = new ArrayList<>();
        List<ProductCategoryDTO> listProductCategoryDTO = new ArrayList<>();

        try {
            listProductCategory = productCategoryDAO.listAllProductCategory(connection.connectionToPostgresDB());
            listProductCategoryDTO = listProductCategory.stream()
                .map(productCategotyMapper::productCategoryToProductCategoryDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("Connection was failed");
            e.printStackTrace();
        }

        req.setAttribute("listProductCategory", listProductCategoryDTO);

        try {
            req.getRequestDispatcher("/WEB-INF/views/product-category/product-category.jsp").forward(req, res);
        } catch (IOException e) {
            logger.info("File not found");
            e.printStackTrace();
        } catch(ServletException e) {
            logger.info("Servlet Exteption");
            e.printStackTrace();
        }
    }

    private void viewProductCategoryAsJson(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            List<ProductCategory> listProductCategory = productCategoryDAO.listAllProductCategory(connection.connectionToPostgresDB());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listProductCategory);
            res.setContentType("application/json");
            res.getWriter().write(json);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.DAO.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private JdbcConnection connection = new JdbcConnection();

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        List<Product> listProduct = new ArrayList<Product>();
        try {
            listProduct = productDAO.listAllProducts(connection.connectionToPostgresDB());
        } catch (Exception e) {
            System.out.println("Connection was failed");
            e.printStackTrace();
        }
        req.setAttribute("listProduct", listProduct);
        req.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, res);
    }
}

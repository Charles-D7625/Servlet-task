package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.DAO.ProductDAO;
import com.example.DTO.ProductDTO;
import com.example.Mappers.ProductMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private ProductMapper productMapper = ProductMapper.INSTANCE;
    private JdbcConnection connection = new JdbcConnection();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        List<Product> listProduct = new ArrayList<Product>();
        List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
        
        try {
            listProduct = productDAO.listAllProducts(connection.connectionToPostgresDB());
            listProductDTO = listProduct.stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Connection was failed");
            e.printStackTrace();
        }
        req.setAttribute("listProduct", listProductDTO);
        req.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, res);
    }
}

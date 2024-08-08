package com.example.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.JdbcConnection;
import com.example.classes.Product;
import com.example.dao.OrderDetailDAO;
import com.example.dao.ProductDAO;
import com.example.dto.ProductDTO;
import com.example.mappers.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {

    private static final String REDIRECT_URL_PATH = "/product";

    private final transient Logger logger = Logger.getLogger(getClass().getName());

    private static transient ProductDAO productDAO = new ProductDAO();
    private static transient OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private static transient ProductMapper productMapper = ProductMapper.INSTANCE;
    private static transient JdbcConnection connection = new JdbcConnection();

    public ProductServlet() {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String pathInfo = req.getPathInfo();
        
        try {
            if(pathInfo == null) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            } else if (pathInfo.equals("/update")) {
                updateProduct(req, res);
            } else if (pathInfo.equals("/insert")) {
                insertProduct(req, res);
            } else if(pathInfo.equals("/delete")) {
                deleteProduct(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        String pathInfo = req.getPathInfo();
        
        try {
            if(pathInfo == null || pathInfo.equals("/")) {
                showAllProducts(req, res);
            } else if (pathInfo.equals("/edit")) {
                showEditProductForm(req, res);
            } else if (pathInfo.equals("/new")) {
                showCreateProductForm(req, res);
            } else if(pathInfo.equals("/delete")) {
                deleteProduct(req, res);
            } else if(pathInfo.equals("/json")) {
                viewProductAsJson(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      

    }

    private void showAllProducts(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, SQLException {

        List<Product> listProduct = new ArrayList<>();
        List<ProductDTO> listProductDTO = new ArrayList<>();

        try {
            listProduct = productDAO.listAllProducts(connection.connectionToPostgresDB());
            listProductDTO = listProduct.stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("Connection was failed");
            e.printStackTrace();
        }
        req.setAttribute("listProduct", listProductDTO);
        req.getRequestDispatcher("/WEB-INF/views/product/product.jsp").forward(req, res);
    }

    private void showCreateProductForm(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, SQLException {

        List<Integer> orderIds = orderDetailDAO.getAllOrderIds(connection.connectionToPostgresDB());
        req.setAttribute("orderIds", orderIds);

        req.getRequestDispatcher("/WEB-INF/views/product/product-form.jsp").forward(req, res);
    }

    private void showEditProductForm(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        Product existingProduct = new Product();
        ProductDTO existingProductDTO = new ProductDTO();
        List<Integer> orderIds = new ArrayList<>();

        int id = Integer.parseInt(req.getParameter("id"));
        try {

            existingProduct = productDAO.getProduct(id, connection.connectionToPostgresDB());
            existingProductDTO = productMapper.productToProductDTO(existingProduct);
            orderIds = orderDetailDAO.getAllOrderIds(connection.connectionToPostgresDB());
            
        } catch (SQLException e) {
            logger.info("Query was failed");
            e.printStackTrace();
        } catch (Exception e) {
            logger.info("Connection was failed");
            e.printStackTrace();
        }

        req.setAttribute("product", existingProductDTO);
        req.setAttribute("orderIds", orderIds);

        req.getRequestDispatcher("/WEB-INF/views/product/product-form.jsp").forward(req, res);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        boolean available = Boolean.parseBoolean(req.getParameter("available"));
        int orderId = Integer.parseInt(req.getParameter("order_id"));

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setPrice(price);
        productDTO.setQuantity(quantity);
        productDTO.setAvailable(available);
        productDTO.setOrderId(orderId);

        Product product = productMapper.productDTOTProduct(productDTO);

        productDAO.updateProduct(product, connection.connectionToPostgresDB());
        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }

    private void insertProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(req.getParameter("name"));
        productDTO.setPrice(new BigDecimal(req.getParameter("price")));
        productDTO.setQuantity(Integer.parseInt(req.getParameter("quantity")));
        productDTO.setAvailable(Boolean.parseBoolean(req.getParameter("available")));
        productDTO.setOrderId(Integer.parseInt(req.getParameter("order_id")));

        Product product = productMapper.productDTOTProduct(productDTO);

        productDAO.insertProduct(product, connection.connectionToPostgresDB());
        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        productDAO.deleteProduct(id, connection.connectionToPostgresDB());

        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }

    private void viewProductAsJson(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            List<Product> listProducts = productDAO.listAllProducts(connection.connectionToPostgresDB());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listProducts);
            res.setContentType("application/json");
            res.getWriter().write(json);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }
}

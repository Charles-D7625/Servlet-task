package com.example.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.JdbcConnection;
import com.example.classes.OrderDetail;
import com.example.dao.OrderDetailDAO;
import com.example.dto.OrderDetailDTO;
import com.example.enums.OrderStatus;
import com.example.mappers.OrderDetailMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/order-detail/*")
public class OrderDetailServlet extends HttpServlet {

    private static final String REDIRECT_URL_PATH = "/order-detail/";

    private final transient Logger logger = Logger.getLogger(getClass().getName());

    private static transient OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private static transient JdbcConnection connection = new JdbcConnection();
    private static transient OrderDetailMapper orderDetailMapper = OrderDetailMapper.INSTANCE;

    public OrderDetailServlet() {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {

        String action = req.getPathInfo();

        try {
            switch (action) {
                case "/update":
                    updateOrderDetail(req, res);
                    break;
                default:
                    showAllOrderDetail(req, res);
                    break;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        
        String action = req.getPathInfo();

        try {
            switch (action) {
                case "/new":
                    insertOrderDetail(req, res);
                    break;
                case "/delete":
                    deleteOrderDetail(req, res);
                    break;
                case "/edit":
                    showEditOrderDetailForm(req, res);
                    break;
                default:
                    showAllOrderDetail(req, res);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showAllOrderDetail(HttpServletRequest req, HttpServletResponse res) {

        List<OrderDetail> listOrderDetail = new ArrayList<>();
        List<OrderDetailDTO> listOrderDetailDTO = new ArrayList<>();

        try {
            listOrderDetail = orderDetailDAO.listAllOrderDetail(connection.connectionToPostgresDB());
            listOrderDetailDTO = listOrderDetail.stream()
                .map(orderDetailMapper::orderDetailToOrderDetailDTO)
                .collect(Collectors.toList());

        } catch (Exception e) {
            logger.info("Connection was failed");
            e.printStackTrace();
        }

        req.setAttribute("listOrderDetail", listOrderDetailDTO);

        try {
            req.getRequestDispatcher("/WEB-INF/views/order-detail/order-detail.jsp").forward(req, res);
        } 
        catch (IOException e) {
            logger.info("File not found");
            e.printStackTrace();
        }
        catch(ServletException e) {
            logger.info("Servlet Exteption");
            e.printStackTrace();
        }
    }

    private void showEditOrderDetailForm(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException, ServletException {

        OrderDetail existingOrderDetail;
        OrderDetailDTO existingOrderDetailDTO;

        int id = Integer.parseInt(req.getParameter("id"));

        existingOrderDetail = orderDetailDAO.getOrderDetail(id, connection.connectionToPostgresDB());
        existingOrderDetailDTO = orderDetailMapper.orderDetailToOrderDetailDTO(existingOrderDetail);
        
        req.setAttribute("orderDetail", existingOrderDetailDTO);
        req.setAttribute("orderStatuses", OrderStatus.values());

        req.getRequestDispatcher("/WEB-INF/views/order-detail/order-detail-form.jsp").forward(req, res);
    }

    private void updateOrderDetail(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        OrderStatus orderStatus = OrderStatus.valueOf(req.getParameter("orderStatus"));
        double totalAmount = Double.parseDouble(req.getParameter("totalAmount"));

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

        orderDetailDTO.setId(id);
        orderDetailDTO.setOrderStatus(orderStatus);
        orderDetailDTO.setTotalAmount(totalAmount);

        OrderDetail orderDetail = orderDetailMapper.orderDetailDTOToOrderDetail(orderDetailDTO);

        orderDetailDAO.updateOrderDetail(orderDetail, connection.connectionToPostgresDB());
        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }

    private void insertOrderDetail(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        orderDetailDAO.insertOrderDetail(connection.connectionToPostgresDB());
        
        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }

    private void deleteOrderDetail(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {

        int id = Integer.parseInt(req.getParameter("id"));
        orderDetailDAO.deleteOrderDetail(id, connection.connectionToPostgresDB());

        res.sendRedirect(req.getContextPath() + REDIRECT_URL_PATH);
    }
}

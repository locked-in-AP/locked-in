package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.locked_in.service.OrderService;

/**
 * GetOrderStatusController handles HTTP requests for retrieving order status.
 * 
 * It processes requests to fetch the current status of orders, validating
 * user authentication and order existence before returning status information.
 */
@WebServlet("/getOrderStatus")
public class GetOrderStatusController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;

    /**
     * Initializes the GetOrderStatusController with an instance of OrderService.
     * Sets up the service for retrieving order status information.
     */
    public GetOrderStatusController() {
        super();
        orderService = new OrderService();
    }

    /**
     * Handles GET requests for order status information.
     * 
     * Validates user authentication and retrieves the current status
     * of the specified order. Returns status information as JSON response.
     *
     * @param request  the HTTP request containing order ID
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Order ID is required");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            String status = orderService.getOrderStatus(orderId);
            
            if (status != null) {
                response.getWriter().write(status);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Order not found");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid Order ID");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while fetching order status");
        }
    }
} 
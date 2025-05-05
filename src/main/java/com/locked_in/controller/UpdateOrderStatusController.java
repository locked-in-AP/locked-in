package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.sql.SQLException;

import com.locked_in.service.OrderService;
import com.locked_in.util.SessionUtil;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;

    public UpdateOrderStatusController() {
        super();
        orderService = new OrderService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        // Check if user is logged in
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Please log in to continue");
            return;
        }

        try {
            // Get orderId from request parameter
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Order ID is required");
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid Order ID");
                return;
            }

            // Update order status to completed
            boolean success = orderService.updateOrderStatus(orderId, "completed");

            if (success) {
                response.getWriter().write("success");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Failed to update order status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while updating the order status");
        }
    }
} 
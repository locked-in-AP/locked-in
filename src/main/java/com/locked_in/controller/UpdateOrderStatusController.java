package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.locked_in.service.OrderService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    public UpdateOrderStatusController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Check if user is admin
            String role = userService.getUserByEmail(email).getRole();
            if (!"admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            // Get orderId and new status from request parameters
            String orderIdStr = request.getParameter("orderId");
            String newStatus = request.getParameter("status");
            
            if (orderIdStr == null || newStatus == null) {
                request.setAttribute("message", "Order ID and status are required");
                request.setAttribute("messageType", "notification-error");
                request.getRequestDispatcher("/admin/orders").forward(request, response);
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Order ID");
                request.setAttribute("messageType", "notification-error");
                request.getRequestDispatcher("/admin/orders").forward(request, response);
                return;
            }

            // Validate status
            if (!isValidStatus(newStatus)) {
                request.setAttribute("message", "Invalid status value");
                request.setAttribute("messageType", "notification-error");
                request.getRequestDispatcher("/admin/orders").forward(request, response);
                return;
            }

            // Update order status
            boolean success = orderService.updateOrderStatus(orderId, newStatus);

            if (success) {
                request.setAttribute("message", "Order #" + orderId + " status updated to " + newStatus);
                request.setAttribute("messageType", "notification-success");
            } else {
                request.setAttribute("message", "Failed to update order status. The order might not exist or the database connection failed.");
                request.setAttribute("messageType", "notification-error");
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/orders");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the order status: " + e.getMessage());
            request.setAttribute("messageType", "notification-error");
            request.getRequestDispatcher("/admin/orders").forward(request, response);
        }
    }

    private boolean isValidStatus(String status) {
        return "pending".equalsIgnoreCase(status) || "completed".equalsIgnoreCase(status) || "cancelled".equalsIgnoreCase(status);
    }
} 
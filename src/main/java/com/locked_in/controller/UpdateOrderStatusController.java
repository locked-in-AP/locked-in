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

/**
 * UpdateOrderStatusController handles HTTP requests for updating order statuses.
 * 
 * It manages the order status update workflow, allowing administrators to change
 * the status of customer orders. Delegates order operations to OrderService and
 * user-related operations to UserService.
 */
@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    /**
     * Initializes the UpdateOrderStatusController with instances of required services.
     * Sets up OrderService for order status updates and UserService for user operations.
     */
    public UpdateOrderStatusController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    /**
     * Handles POST requests for order status updates.
     * 
     * Processes the status update request, validates admin permissions, and updates
     * the order status in the database. Redirects to appropriate pages based on the result.
     *
     * @param request  the HTTP request containing order ID and new status
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
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

    /**
     * Validates if the provided order status is one of the allowed values.
     * 
     * Checks if the status matches one of the predefined valid order statuses:
     * "pending", "completed", or "cancelled". The comparison is case-insensitive.
     *
     * @param status the order status to validate
     * @return true if the status is valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return "pending".equalsIgnoreCase(status) || "completed".equalsIgnoreCase(status) || "cancelled".equalsIgnoreCase(status);
    }
} 
package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.locked_in.model.OrderModel;
import com.locked_in.model.UserModel;
import com.locked_in.service.OrderService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * AdminOrdersController handles HTTP requests for managing orders in the admin interface.
 * 
 * It provides functionality for viewing, updating, and managing customer orders.
 * Delegates order operations to OrderService and user-related operations to UserService.
 */
@WebServlet("/admin/orders")
public class AdminOrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    /**
     * Initializes the AdminOrdersController with instances of required services.
     * Sets up OrderService for order management and UserService for user operations.
     */
    public AdminOrdersController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display the admin orders page.
     * 
     * Retrieves all orders from the database and forwards to the admin orders JSP.
     * Includes order details and associated user information.
     *
     * @param request  the HTTP request containing admin session information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Check if user is admin
            UserModel adminUser = userService.getUserByEmail(email);
            if (adminUser == null || !"admin".equals(adminUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            // Set admin's user details including profile picture
            request.setAttribute("userDetails", adminUser);

            // Get not completed and completed orders
            List<OrderModel> notCompletedOrders = orderService.getNotCompletedOrders();
            List<OrderModel> completedOrders = orderService.getCompletedOrders();
            request.setAttribute("notCompletedOrders", notCompletedOrders);
            request.setAttribute("completedOrders", completedOrders);
            request.getRequestDispatcher("/WEB-INF/pages/admin/orders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
} 
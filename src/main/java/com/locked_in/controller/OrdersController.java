package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.locked_in.model.OrderModel;
import com.locked_in.service.OrderService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * OrdersController handles HTTP requests for managing user orders.
 * 
 * It provides functionality for viewing and managing customer orders, including
 * order history and details. Delegates order operations to OrderService and
 * user-related operations to UserService.
 */
@WebServlet("/orders")
public class OrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    /**
     * Initializes the OrdersController with instances of required services.
     * Sets up OrderService for order management and UserService for user operations.
     */
    public OrdersController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display user orders.
     * 
     * Retrieves the current user's orders from the database and forwards to the
     * orders JSP. If user is not logged in, redirects to login page.
     *
     * @param request  the HTTP request containing user session information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles POST requests for order actions.
     * 
     * Currently delegates to processRequest as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing order action data
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes requests for order actions.
     * 
     * @param request  the HTTP request containing order action data
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get user ID
            int userId = userService.getUserByEmail(email).getUserId();
            
            // Get user's orders
            List<OrderModel> orders = orderService.getUserOrders(userId);
            
            // Set orders in request
            request.setAttribute("orders", orders);
            
            // Forward to orders page
            request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while fetching your orders");
            request.setAttribute("messageType", "notification-error");
            request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
        }
    }
} 
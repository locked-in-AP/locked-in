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
 * CheckoutController handles HTTP requests related to the checkout process.
 * 
 * It manages the order checkout workflow, including order creation, validation,
 * and processing. Delegates order operations to OrderService and user-related
 * operations to UserService.
 */
@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    /**
     * Initializes the CheckoutController with instances of required services.
     * Sets up OrderService for order processing and UserService for user operations.
     */
    public CheckoutController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    /**
     * Handles POST requests for order checkout processing.
     * 
     * Processes the checkout form submission, creates a new order, and handles
     * the payment workflow. Redirects to appropriate pages based on the result.
     *
     * @param request  the HTTP request containing order and payment information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int userId = userService.getUserByEmail(email).getUserId();
            boolean success = orderService.processCheckout(userId);
            
            if (success) {
                // Update cart size in session
                SessionUtil.setAttribute(request, "cartSize", 0);
                // Redirect to orders page with success message
                response.sendRedirect(request.getContextPath() + "/orders?message=Order placed successfully");
            } else {
                // Redirect back to cart with error message
                response.sendRedirect(request.getContextPath() + "/cart?message=Your cart is empty");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Redirect back to cart with error message
            response.sendRedirect(request.getContextPath() + "/cart?message=An error occurred while processing your order");
        }
    }
} 
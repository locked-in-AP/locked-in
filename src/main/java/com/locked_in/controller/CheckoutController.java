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

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    public CheckoutController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

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
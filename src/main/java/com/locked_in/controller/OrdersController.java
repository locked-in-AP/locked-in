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

@WebServlet("/orders")
public class OrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    public OrdersController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
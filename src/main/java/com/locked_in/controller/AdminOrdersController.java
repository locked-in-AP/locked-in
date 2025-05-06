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
import com.locked_in.service.OrderService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

@WebServlet("/admin/orders")
public class AdminOrdersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;

    public AdminOrdersController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }

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
            String role = userService.getUserByEmail(email).getRole();
            if (!"admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            // Get all orders
            List<OrderModel> orders = orderService.getAllOrders();
            request.setAttribute("orders", orders);
            
            request.getRequestDispatcher("/WEB-INF/pages/admin/orders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
} 
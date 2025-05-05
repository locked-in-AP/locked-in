package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.locked_in.service.ReviewService;
import com.locked_in.service.UserService;
import com.locked_in.service.OrderService;
import com.locked_in.util.SessionUtil;

@WebServlet("/addReview")
public class AddReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewService reviewService;
    private UserService userService;
    private OrderService orderService;

    public AddReviewController() {
        super();
        reviewService = new ReviewService();
        userService = new UserService();
        orderService = new OrderService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is logged in
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String message = "";
        String messageType = "";

        try {
            // Get user ID
            int userId = userService.getUserByEmail(email).getUserId();
            
            // Get parameters
            String orderIdStr = request.getParameter("orderId");
            String productIdStr = request.getParameter("productId");
            String review = request.getParameter("review");
            String ratingStr = request.getParameter("rating");

            // Validate parameters
            if (orderIdStr == null || productIdStr == null || review == null || ratingStr == null) {
                message = "All fields are required";
                messageType = "notification-error";
                redirectWithMessage(request, response, message, messageType);
                return;
            }

            int orderId = Integer.parseInt(orderIdStr);
            int productId = Integer.parseInt(productIdStr);
            int rating = Integer.parseInt(ratingStr);

            // Validate rating
            if (rating < 1 || rating > 5) {
                message = "Rating must be between 1 and 5";
                messageType = "notification-error";
                redirectWithMessage(request, response, message, messageType);
                return;
            }

            // Check if order is completed
            if (!orderService.isOrderCompleted(orderId)) {
                message = "You can only review completed orders";
                messageType = "notification-error";
                redirectWithMessage(request, response, message, messageType);
                return;
            }

            // Check if already reviewed
            if (reviewService.hasReviewed(userId, productId, orderId)) {
                message = "You have already reviewed this product";
                messageType = "notification-error";
                redirectWithMessage(request, response, message, messageType);
                return;
            }

            // Add review
            boolean success = reviewService.addReview(userId, productId, orderId, review, rating);

            if (success) {
                message = "Thank you for your review!";
                messageType = "notification-success";
            } else {
                message = "Failed to add review. Please try again.";
                messageType = "notification-error";
            }

            redirectWithMessage(request, response, message, messageType);
        } catch (NumberFormatException e) {
            message = "Invalid order ID or product ID";
            messageType = "notification-error";
            redirectWithMessage(request, response, message, messageType);
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while processing your review";
            messageType = "notification-error";
            redirectWithMessage(request, response, message, messageType);
        }
    }

    private void redirectWithMessage(HttpServletRequest request, HttpServletResponse response, 
                                  String message, String messageType) throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String encodedType = URLEncoder.encode(messageType, StandardCharsets.UTF_8);
        response.sendRedirect(request.getContextPath() + "/orders?message=" + 
                            encodedMessage + "&messageType=" + encodedType);
    }
} 
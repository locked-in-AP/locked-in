package com.locked_in.controller;

import com.locked_in.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DeleteUserController handles HTTP requests for user account deletion.
 * 
 * It processes requests to remove user accounts from the system, validating
 * admin authentication and user existence before performing the deletion.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/deleteUser" })
public class DeleteUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    /**
     * Initializes the DeleteUserController with an instance of UserService.
     * Sets up the service for handling user deletion operations.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display the user deletion form.
     * 
     * Forwards the request to the user deletion JSP located in /WEB-INF/pages/deleteUser.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/deleteUser.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user account deletion.
     * 
     * Validates admin authentication and user existence before removing
     * the user account from the system. Redirects with appropriate success/error messages.
     *
     * @param request  the HTTP request containing user ID to delete
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        try {
            int userId = Integer.parseInt(userIdStr);
            boolean deleted = userService.deleteUser(userId);
            if (deleted) {
                request.setAttribute("success", "User deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete user. Please check the ID and try again.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/deleteUser.jsp").forward(request, response);
    }
} 
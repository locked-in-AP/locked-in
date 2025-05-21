package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;
import com.locked_in.model.UserModel;

/**
 * DeleteUserController handles HTTP requests for user deletion.
 * 
 * It processes requests to remove users from the system, validating
 * admin authentication before performing the deletion.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/deleteUser" })
public class DeleteUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService;

    /**
     * Initializes the DeleteUserController with an instance of UserService.
     * Sets up the service for handling user deletion operations.
     */
    public DeleteUserController() {
        super();
        this.userService = new UserService();
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
     * the user from the system. Redirects with appropriate success/error messages.
     *
     * @param request  the HTTP request containing user ID to delete
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DeleteUserController - Received delete request");
        
        // Check if user is logged in and is admin
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            System.out.println("DeleteUserController - No user logged in");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = (String) SessionUtil.getAttribute(request, "role");
        if (!"admin".equals(role)) {
            System.out.println("DeleteUserController - User is not admin");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Get user ID from request
        String userIdStr = request.getParameter("userId");
        System.out.println("DeleteUserController - Attempting to delete user with ID: " + userIdStr);
        
        try {
            int userId = Integer.parseInt(userIdStr);
            
            // Get the current admin's user ID
            UserModel currentAdmin = userService.getUserByEmail(email);
            if (currentAdmin != null && currentAdmin.getUserId() == userId) {
                System.out.println("DeleteUserController - Admin attempting to delete their own account");
                response.sendRedirect(request.getContextPath() + "/admindashboard?error=Cannot delete the account you are logged in as");
                return;
            }
            
            // Delete the user
            boolean deleted = userService.deleteUser(userId);
            System.out.println("DeleteUserController - Delete operation result: " + deleted);
            
            if (deleted) {
                // Redirect back to admin dashboard with success message
                response.sendRedirect(request.getContextPath() + "/admindashboard?success=User deleted successfully");
            } else {
                // Redirect back to admin dashboard with error message
                response.sendRedirect(request.getContextPath() + "/admindashboard?error=Failed to delete user");
            }
        } catch (NumberFormatException e) {
            System.out.println("DeleteUserController - Invalid user ID format: " + userIdStr);
            response.sendRedirect(request.getContextPath() + "/admindashboard?error=Invalid user ID");
        } catch (Exception e) {
            System.out.println("DeleteUserController - Error during deletion: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admindashboard?error=An error occurred: " + e.getMessage());
        }
    }
} 
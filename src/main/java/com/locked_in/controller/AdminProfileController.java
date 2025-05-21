package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.model.UserModel;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * AdminProfileController handles HTTP requests for the admin profile page.
 * 
 * It serves the admin profile page, displaying admin information and
 * providing access to profile management features.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminProfile" })
public class AdminProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final UserService userService;
    
    /**
     * Initializes the AdminProfileController with an instance of UserService.
     * Sets up the service for handling admin profile operations.
     */
    public AdminProfileController() {
        super();
        this.userService = new UserService();
    }

    /**
     * Handles GET requests to display the admin profile.
     * 
     * Retrieves admin information and forwards to the admin profile JSP.
     * Validates admin authentication before displaying the profile.
     *
     * @param request  the HTTP request containing admin session information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the current user's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        if (email == null) {
            // If not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Fetch user details from the database
        UserModel userDetails = userService.getUserByEmail(email);
        
        if (userDetails == null) {
            // Something went wrong with fetching user details
            request.setAttribute("error", "Could not retrieve your profile information. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
            return;
        }
        
        // Set user details in the request
        request.setAttribute("userDetails", userDetails);
        
        // Forward to the admin profile page
        request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for admin profile actions.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 
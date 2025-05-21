package com.locked_in.controller;

import com.locked_in.service.UserService;
import com.locked_in.model.UserModel;
import com.locked_in.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * UsersController handles HTTP requests related to user management.
 * 
 * It provides functionality for viewing and managing user accounts.
 * Delegates user-related operations to UserService for data management tasks.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/users" })
public class UsersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    /**
     * Initializes the UsersController with an instance of UserService.
     * Sets up the service during servlet initialization.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display the users list.
     * 
     * Retrieves all users from the database and forwards to the users JSP.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get admin's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get admin's user details including profile picture
        UserModel adminUser = userService.getUserByEmail(email);
        if (adminUser != null) {
            request.setAttribute("userDetails", adminUser);
        }

        List<UserModel> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
} 
package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.model.UserModel;
import com.locked_in.service.UserService;
import com.locked_in.service.OrderService;
import com.locked_in.util.SessionUtil;


/**
 * UserProfileController handles HTTP requests for user profile pages.
 * 
 * It serves the user profile page, displaying user information, order history,
 * and providing access to profile management features.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/userprofile" })
public class UserProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private final UserService userService;
    private final OrderService orderService;
    
    /**
     * Initializes the UserProfileController with instances of required services.
     * Sets up UserService for user information and OrderService for order history.
     */
    public UserProfileController() {
        super();
        this.userService = new UserService();
        this.orderService = new OrderService();
    }

	/**
	 * Handles GET requests to display user profile.
	 * 
	 * Retrieves user information and order history, then forwards to the
	 * profile view JSP with all necessary data.
	 *
	 * @param request  the HTTP request containing user session information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get the current user's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        if (email == null) {
            // If not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Fetch user details from the database
        UserModel userDetails = userService.getUserByEmail(email);
        
        if (userDetails != null) {
            // Set the profile picture in session
            SessionUtil.setAttribute(request, "profilePicture", userDetails.getProfilePicture());
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for user profile actions.
	 * 
	 * Currently delegates to doGet as no specific POST functionality is implemented.
	 *
	 * @param request  the HTTP request containing client request information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

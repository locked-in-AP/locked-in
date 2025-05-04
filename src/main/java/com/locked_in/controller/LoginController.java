package com.locked_in.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.locked_in.model.UserModel;
import com.locked_in.service.LoginService;
import com.locked_in.service.CartService;
import com.locked_in.util.CookieUtil;
import com.locked_in.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LoginController handles HTTP requests related to user login.
 * 
 * It serves the login page and processes login form submissions by delegating authentication 
 * to the LoginService and managing user session/cookie data based on the result.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final LoginService loginService;

	/**
	 * Initializes the LoginController with an instance of LoginService.
	 */
	public LoginController() {
		this.loginService = new LoginService();
	}

	/**
	 * Displays the login page when a GET request is made to /login.
	 * 
	 * Forwards the user to the login JSP located in /WEB-INF/pages/login.jsp.
	 *
	 * @param request  the HTTP request containing client request information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * Processes the login form when a POST request is made to /login.
	 * 
	 * Authenticates the user using LoginService. If successful, stores the username
	 * in the session and sets a role-based cookie. Redirects to dashboard or home.
	 * If authentication fails or the service is unavailable, an error message is shown.
	 *
	 * @param request  the HTTP request containing login form data
	 * @param response the HTTP response used to redirect or show errors
	 * @throws ServletException if servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs during processing
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserModel userModel = new UserModel(email, password);
		Boolean loginStatus = loginService.loginUser(userModel);

		if (loginStatus != null && loginStatus) {
			SessionUtil.setAttribute(request, "email", email);
			SessionUtil.setAttribute(request, "name", userModel.getName());
			SessionUtil.setAttribute(request, "profilePicture", userModel.getProfilePicture());
			SessionUtil.setAttribute(request, "userId", userModel.getUserId());
			
			// Initialize cart size in session
			CartService cartService = new CartService();
			try {
				int cartSize = cartService.getCartSize(userModel.getUserId());
				SessionUtil.setAttribute(request, "cartSize", cartSize);
			} catch (SQLException e) {
				e.printStackTrace();
				// Don't fail login if cart size can't be retrieved
				SessionUtil.setAttribute(request, "cartSize", 0);
			}
			
			String role = userModel.getRole();
			System.out.println("User role from database: " + role);
			System.out.println("User name from database: " + userModel.getName());
			CookieUtil.addCookie(response, "role", role, 5 * 30);
			if ("admin".equals(role)) {
				request.setAttribute("success", true);
				response.sendRedirect(request.getContextPath() + "/admindashboard");
			} else {
				request.setAttribute("success", true);
				request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
			}
		} else {
			handleLoginFailure(request, response, loginStatus);
		}
	}

	/**
	 * Forwards the request back to the login page with an appropriate error message.
	 * 
	 * Distinguishes between server unavailability (null loginStatus) and invalid credentials.
	 *
	 * @param request         the HTTP request used to set attributes
	 * @param response        the HTTP response used to forward to login page
	 * @param loginStatus indicates login result (null for server issue, false for authentication failure)
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	private void handleLoginFailure(HttpServletRequest request, HttpServletResponse response, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		request.setAttribute("error", errorMessage);
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

}

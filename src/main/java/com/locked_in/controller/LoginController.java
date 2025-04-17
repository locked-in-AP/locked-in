package com.locked_in.controller;

import java.io.IOException;

import com.locked_in.model.UserModel;
import com.locked_in.service.LoginService;
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
	 * @param req  the HTTP request containing login form data
	 * @param resp the HTTP response used to redirect or show errors
	 * @throws ServletException if servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs during processing
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		UserModel userModel = new UserModel(username, password);
		Boolean loginStatus = loginService.loginUser(userModel);

		if (loginStatus != null && loginStatus) {
			SessionUtil.setAttribute(req, "username", username);
			if (username.equals("admin")) {
				CookieUtil.addCookie(resp, "role", "admin", 5 * 30);
				resp.sendRedirect(req.getContextPath() + "/dashboard");
			} else {
				CookieUtil.addCookie(resp, "role", "user", 5 * 30);
				resp.sendRedirect(req.getContextPath() + "/home");
			}
		} else {
			handleLoginFailure(req, resp, loginStatus);
		}
	}

	/**
	 * Forwards the request back to the login page with an appropriate error message.
	 * 
	 * Distinguishes between server unavailability (null loginStatus) and invalid credentials.
	 *
	 * @param req         the HTTP request used to set attributes
	 * @param resp        the HTTP response used to forward to login page
	 * @param loginStatus indicates login result (null for server issue, false for auth failure)
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}

}

package com.locked_in.controller;

import java.io.IOException;

import com.locked_in.util.CookieUtil;
import com.locked_in.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LogoutController handles HTTP requests for user logout.
 * 
 * It processes logout requests by invalidating the user's session and
 * clearing any associated cookies before redirecting to the home page.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the LogoutController.
	 * This controller does not require any service dependencies.
	 */
	public LogoutController() {
		super();
	}

	/**
	 * Handles GET requests for user logout.
	 * 
	 * Invalidates the current user session, clears session attributes,
	 * and redirects to the home page.
	 *
	 * @param request  the HTTP request containing session information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs during processing
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session and invalidate it
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Remove the role cookie
		CookieUtil.deleteCookie(response, "role");

		// Clear any session attributes
		SessionUtil.removeAttribute(request, "email");

		// Redirect to login page
		response.sendRedirect(request.getContextPath() + "/home");
	}
}

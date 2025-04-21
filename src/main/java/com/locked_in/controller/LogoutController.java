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
 * Servlet implementation class LogoutController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to log the user out by invalidating the session,
	 * removing the role cookie, and redirecting to the login page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

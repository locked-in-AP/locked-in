package com.locked_in.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.locked_in.util.CookieUtil;
import com.locked_in.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ADMIN_DASHBOARD = "/admindashboard";
	private static final String USER_DASHBOARD = "/userprofile";
	private static final String ADMIN_ORDER = "/adminOrder";
	private static final String ABOUT = "/aboutus";
	private static final String SUPPLEMENTS = "/supplements";
	private static final String MERCHANDISE = "/merchandise";
	private static final String EQUIPMENTS = "/equipments";
	private static final String LOGOUT = "/logout";
	private static final String CONTACT = "/contactus";
	private static final String ORDER_LIST = "/orderlist";
	private static final String PAYMENT = "/payment";
	private static final String ITEM = "/item";
	private static final String CART = "/cart";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if required
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		// Allow access to resources
		if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}

		boolean isLoggedIn = SessionUtil.getAttribute(req, "email") != null;
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
				: null;

		System.out.println("URI: " + uri);
		System.out.println("Is logged in: " + isLoggedIn);
		System.out.println("User role: " + userRole);

		if (!isLoggedIn) {
			// Not logged in
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(SUPPLEMENTS) || uri.endsWith(EQUIPMENTS)
					|| uri.endsWith(MERCHANDISE) || uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT)
					|| uri.endsWith(CONTACT) || uri.endsWith(LOGOUT)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + LOGIN);
			}
			return;
		}

		// User is logged in
		if ("admin".equals(userRole)) {
			// Admin user
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);
			} else if (uri.endsWith(ADMIN_DASHBOARD) || uri.endsWith(ADMIN_ORDER) || uri.endsWith(HOME)
					|| uri.endsWith(ROOT) || uri.endsWith(LOGOUT) || uri.endsWith(ITEM)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + ADMIN_DASHBOARD);
			}
		} else {
			// Regular user
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + HOME);
			} else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT)
					|| uri.endsWith(ORDER_LIST) || uri.endsWith(CART) || uri.endsWith(USER_DASHBOARD)
					|| uri.endsWith(SUPPLEMENTS) || uri.endsWith(EQUIPMENTS) || uri.endsWith(MERCHANDISE)
					|| uri.endsWith(ITEM) || uri.endsWith(PAYMENT) || uri.endsWith(LOGOUT)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + HOME);
			}
		}
	}

	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}

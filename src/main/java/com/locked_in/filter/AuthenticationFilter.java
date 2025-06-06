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

/**
 * AuthenticationFilter implements request filtering for authentication and authorization.
 * 
 * This filter intercepts all incoming requests to enforce security rules and access control.
 * It manages user authentication state and role-based access restrictions:
 * - Public resources (images, CSS) and pages (home, login, register) are always accessible
 * - Unauthenticated users are restricted to public pages and redirected to login
 * - Admin users have access to administrative pages and functions
 * - Regular users have access to customer-facing pages and functions
 * 
 * The filter uses session attributes for authentication state and cookies for role
 * information, working in conjunction with LoginController for user sessions.
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ADMIN_DASHBOARD = "/admindashboard";
	private static final String USER_DASHBOARD = "/userprofile";
	private static final String ADMIN_ORDER = "/admin/orders";
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
	private static final String CHECKOUT = "/checkout";
	private static final String ORDERS = "/orders";
	private static final String ADD = "/addProduct";
	private static final String UPDATE = "/updateProfile";
	private static final String DELETE = "/deleteProduct";
	private static final String USER = "/users";
	private static final String DELETEUSER = "/deleteUser";
	private static final String UPDATE_PRODUCT = "/updateProduct";
	private static final String PRODUCTLIST = "/productList";
	private static final String UPDATE_ORDER_STATUS = "/updateOrderStatus";
	private static final String ADD_REVIEW = "/addReview";
	private static final String USER_PROFILE = "/userProfile";
	private static final String UPDATE_PROFILE = "/updateProfile";
	private static final String SEARCH = "/search";
	private static final String ADMINPROFILE = "/adminProfile";
	private static final String UPDATEADMIN = "/updateAdminProfile";

	

	/**
	 * Initializes the AuthenticationFilter with the given filter configuration.
	 * 
	 * This method is called by the web container to indicate to a filter that it
	 * is being placed into service. Currently, no initialization logic is required.
	 *
	 * @param filterConfig the filter configuration object used by a servlet container
	 *                    to pass information to a filter during initialization
	 * @throws ServletException if an error occurs during initialization
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if required
	}

	/**
	 * Filters requests to enforce authentication and authorization rules.
	 * 
	 * Processes each request to check user authentication status and role-based
	 * access control. Handles different scenarios:
	 * 1. Resource access (images, CSS) - always allowed
	 * 2. Public pages (home, login, register) - always allowed
	 * 3. Unauthenticated users - redirected to login
	 * 4. Admin users - restricted to admin pages
	 * 5. Regular users - restricted to user pages
	 *
	 * @param request  the servlet request to be filtered
	 * @param response the servlet response to be filtered
	 * @param chain    the filter chain for invoking the next filter
	 * @throws IOException      if an I/O error occurs during processing
	 * @throws ServletException if a servlet-related error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());

		// Allow access to resources
		if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}

		// Check if the request is for the home page or login/register pages
		if (path.equals("/") || path.equals("/login") || path.equals("/register")) {
			chain.doFilter(request, response);
			return;
		}

		boolean isLoggedIn = SessionUtil.getAttribute(req, "email") != null;
		String userRole = (String) SessionUtil.getAttribute(req, "role");

		System.out.println("URI: " + uri);
		System.out.println("Is logged in: " + isLoggedIn);
		System.out.println("User role: " + userRole);

		if (!isLoggedIn) {
			// Not logged in
			if (path.endsWith(LOGIN) || path.endsWith(REGISTER) || path.endsWith(SUPPLEMENTS) || path.endsWith(EQUIPMENTS)
					|| path.endsWith(MERCHANDISE) || path.endsWith(HOME) || path.endsWith(ROOT) || path.endsWith(ABOUT)
					|| path.endsWith(CONTACT) || path.endsWith(LOGOUT) || path.endsWith(ITEM) || path.endsWith(SEARCH)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(contextPath + LOGIN);
			}
			return;
		}

		// User is logged in
		if ("admin".equals(userRole)) {
			// Admin user
			if (path.endsWith(LOGIN) || path.endsWith(REGISTER)) {
				res.sendRedirect(contextPath + ADMIN_DASHBOARD);
			} else if (path.endsWith(ADMIN_DASHBOARD) || path.endsWith(ADMIN_ORDER)
					|| path.endsWith(ROOT) || path.endsWith(LOGOUT) || path.endsWith(ADD) || path.endsWith(DELETE) || path.endsWith(USER) || path.endsWith(DELETEUSER)
					|| path.endsWith(UPDATE_PRODUCT) || path.endsWith(PRODUCTLIST) || path.endsWith(UPDATE_ORDER_STATUS) || path.endsWith(ADMINPROFILE) || path.endsWith(UPDATEADMIN)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(contextPath + ADMIN_DASHBOARD);
			}
		} else {
			// Regular user
			if (path.endsWith(LOGIN) || path.endsWith(REGISTER)) {
				res.sendRedirect(contextPath + HOME);
			} else if (path.endsWith(HOME) || path.endsWith(ROOT) || path.endsWith(ABOUT) || path.endsWith(CONTACT)
					|| path.endsWith(ORDER_LIST) || path.endsWith(CART) || path.endsWith(USER_DASHBOARD)
					|| path.endsWith(SUPPLEMENTS) || path.endsWith(EQUIPMENTS) || path.endsWith(MERCHANDISE)
					|| path.endsWith(ITEM) || path.endsWith(PAYMENT) || path.endsWith(LOGOUT) || path.endsWith(UPDATE)
					|| path.endsWith(CHECKOUT) || path.endsWith(ORDERS) || path.endsWith(ADD_REVIEW) || path.endsWith(USER_PROFILE) 
					|| path.endsWith(UPDATE_PROFILE) || path.endsWith(SEARCH)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(contextPath + HOME);
			}
		}
	}

	/**
	 * Destroys the AuthenticationFilter instance.
	 * 
	 * Called by the web container to indicate to a filter that it is being
	 * taken out of service. Currently, no cleanup logic is required.
	 */
	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}

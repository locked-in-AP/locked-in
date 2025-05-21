package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.locked_in.service.ProductService;
import com.locked_in.model.ProductModel;
import java.util.List;
import com.locked_in.service.UserService;
import com.locked_in.model.UserModel;
import com.locked_in.service.OrderService;
import java.math.BigDecimal;

/**
 * AdminDashboardController handles HTTP requests for the admin dashboard.
 * 
 * It provides administrative functionality including viewing and managing products,
 * users, and orders. Delegates operations to ProductService, UserService, and OrderService
 * for handling respective data management tasks.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admindashboard" })
public class AdminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
	private final UserService userService;
	private final OrderService orderService;
       
    /**
     * Initializes the AdminDashboardController with instances of required services.
     * Sets up ProductService, UserService, and OrderService for handling admin operations.
     */
    public AdminDashboardController() {
        super();
        this.productService = new ProductService();
        this.userService = new UserService();
        this.orderService = new OrderService();
    }

	/**
	 * Handles GET requests to display the admin dashboard.
	 * 
	 * Retrieves summary data including total products, users, and orders.
	 * Forwards the request to the admin dashboard JSP with all necessary data.
	 *
	 * @param request  the HTTP request containing admin session information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<ProductModel> products = productService.getLimitedProducts(5, "newest");
			List<UserModel> users = userService.getAllUsers();
			if (users.size() > 5) {
				users = users.subList(0, 5);
			}
			
			// Get total revenue from completed orders in last 30 days
			BigDecimal totalRevenue = orderService.getTotalRevenueLast30Days();
			
			// Get total orders in last 30 days
			int totalOrders = orderService.getTotalOrdersLast30Days();
			
			// Get total customers in last 30 days
			int totalCustomers = userService.getTotalCustomersLast30Days();
			
			// Get pending deliveries count
			int pendingDeliveries = orderService.getPendingDeliveriesCount();
			
			// Get admin's profile picture
			String adminEmail = (String) request.getSession().getAttribute("email");
			UserModel adminUser = userService.getUserByEmail(adminEmail);
			if (adminUser != null) {
				request.setAttribute("userDetails", adminUser);
			}
			
			request.setAttribute("products", products);
			request.setAttribute("users", users);
			request.setAttribute("totalRevenue", totalRevenue);
			request.setAttribute("totalOrders", totalOrders);
			request.setAttribute("totalCustomers", totalCustomers);
			request.setAttribute("pendingDeliveries", pendingDeliveries);

            // Check for success message from redirect
            String successMessage = request.getParameter("success");
            if (successMessage != null && !successMessage.isEmpty()) {
                request.setAttribute("success", successMessage);
            }

            //Check for error message from redirect
            String errorMessage = request.getParameter("error");
           if (errorMessage != null && !errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
            }

			request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("AdminDashboardController - Error: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
		}
	}

	/**
	 * Handles POST requests for admin dashboard actions.
	 * 
	 * Currently delegates to doGet as no specific POST functionality is implemented.
	 * This allows the dashboard to handle any form submissions or AJAX requests
	 * through the same processing logic as GET requests.
	 *
	 * @param request  the HTTP request containing admin action data
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

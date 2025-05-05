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

/**
 * Servlet implementation class AdminDashboard
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admindashboard" })
public class AdminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
	private final UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboardController() {
        super();
        this.productService = new ProductService();
        this.userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<ProductModel> products = productService.getLimitedProducts(5, "newest");
			List<UserModel> users = userService.getAllUsers();
			if (users.size() > 5) {
				users = users.subList(0, 5);
			}
			request.setAttribute("products", products);
			request.setAttribute("users", users);
			request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
		} catch (Exception e) {
			System.out.println("AdminDashboardController - Error: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

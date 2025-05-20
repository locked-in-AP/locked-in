package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.locked_in.model.ProductModel;
import com.locked_in.service.ProductService;

/**
 * MerchandiseController handles HTTP requests for the merchandise products page.
 * 
 * It serves the merchandise products listing page, displaying all available
 * merchandise items with their details and filtering options.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/merchandise"})
public class MerchandiseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
       
	/**
	 * Initializes the MerchandiseController with an instance of ProductService.
	 * Sets up the service for retrieving merchandise product information.
	 */
	public MerchandiseController() {
		super();
		this.productService = new ProductService();
	}

	/**
	 * Handles GET requests to display merchandise products.
	 * 
	 * Retrieves all merchandise products and forwards to the merchandise products view JSP.
	 * Includes product details, categories, and filtering options.
	 *
	 * @param request  the HTTP request containing any filter parameters
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the sort parameter from the request, default to "relevancy" if not specified
		String sortBy = request.getParameter("sort");
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "relevancy";
		}
		
		// Get merchandise from the database with sorting
		List<ProductModel> merchandise = productService.getProductsByCategory("merchandise", sortBy);
		
		// Set the products and sort parameter in the request
		request.setAttribute("products", merchandise);
		request.setAttribute("currentSort", sortBy);
		
		// Forward to the merchandise JSP
		request.getRequestDispatcher("/WEB-INF/pages/merchandise.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for merchandise actions.
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

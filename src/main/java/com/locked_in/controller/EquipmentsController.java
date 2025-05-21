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
 * EquipmentsController handles HTTP requests for the equipment products page.
 * 
 * It serves the equipment products listing page, displaying all available
 * equipment items with their details and filtering options.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/equipments"})
public class EquipmentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
       
	/**
	 * Initializes the EquipmentsController with an instance of ProductService.
	 * Sets up the service for retrieving equipment product information.
	 */
	public EquipmentsController() {
		super();
		this.productService = new ProductService();
	}

	/**
	 * Handles GET requests to display equipment products.
	 * 
	 * Retrieves equipment products from the database with optional sorting and forwards
	 * to the equipments JSP. Supports different sorting options via the 'sort' parameter.
	 *
	 * @param request  the HTTP request containing sorting preferences
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
		
		// Get equipment from the database with sorting
		List<ProductModel> allEquipment = productService.getProductsByCategory("equipment", sortBy);
		
		// Check if this is an AJAX request for loading more products
		String isAjax = request.getParameter("ajax");
		if ("true".equals(isAjax)) {
			// For AJAX requests, send all products
			request.setAttribute("products", allEquipment);
			request.getRequestDispatcher("/WEB-INF/pages/product-grid.jsp").forward(request, response);
			return;
		}
		
		// For initial page load, show only first 6 products
		List<ProductModel> initialEquipment = allEquipment.size() > 6 ? 
			allEquipment.subList(0, 6) : allEquipment;
		
		// Set the products and sort parameter in the request
		request.setAttribute("products", initialEquipment);
		request.setAttribute("currentSort", sortBy);
		request.setAttribute("totalProducts", allEquipment.size());
		
		// Forward to the equipments JSP
		request.getRequestDispatcher("/WEB-INF/pages/equipments.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for equipment actions.
	 * 
	 * Currently delegates to doGet as no specific POST functionality is implemented.
	 * This allows the equipment page to handle any form submissions or AJAX requests
	 * through the same processing logic as GET requests.
	 *
	 * @param request  the HTTP request containing equipment action data
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

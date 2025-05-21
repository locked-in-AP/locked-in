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
 * SupplementsController handles HTTP requests related to supplement products.
 * 
 * It manages the display and sorting of supplement products, delegating product
 * operations to ProductService. The controller handles both viewing supplements
 * and processing supplement-related actions.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/supplements"})
public class SupplementsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
       
    /**
     * Initializes the SupplementsController with an instance of ProductService.
     * Sets up the service for handling supplement product operations.
     */
    public SupplementsController() {
        super();
        this.productService = new ProductService();
    }

	/**
	 * Handles GET requests to display supplements.
	 * 
	 * Retrieves supplements from the database with optional sorting and forwards
	 * to the supplements JSP. Supports different sorting options via the 'sort'
	 * parameter.
	 *
	 * @param request  the HTTP request containing sorting preferences
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// Get the sort parameter from the request, default to "relevancy" if not specified
		String sortBy = request.getParameter("sort");
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "relevancy";
		}
		
		// Get supplements from the database with sorting
		List<ProductModel> allSupplements = productService.getProductsByCategory("supplement", sortBy);
		
		// Check if this is an AJAX request for loading more products
		String isAjax = request.getParameter("ajax");
		if ("true".equals(isAjax)) {
			// For AJAX requests, send all products
			request.setAttribute("products", allSupplements);
			request.getRequestDispatcher("/WEB-INF/pages/product-grid.jsp").forward(request, response);
			return;
		}
		
		// For initial page load, show only first 6 products
		List<ProductModel> initialSupplements = allSupplements.size() > 6 ? 
			allSupplements.subList(0, 6) : allSupplements;
		
		// Set the products and sort parameter in the request
		request.setAttribute("products", initialSupplements);
		request.setAttribute("currentSort", sortBy);
		request.setAttribute("totalProducts", allSupplements.size());
		
		// Forward to the supplements JSP
		request.getRequestDispatcher("/WEB-INF/pages/supplements.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for supplement actions.
	 * 
	 * Currently delegates to doGet as no specific POST functionality is implemented.
	 *
	 * @param request  the HTTP request containing supplement action data
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}

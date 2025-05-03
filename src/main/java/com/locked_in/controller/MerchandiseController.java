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
 * Controller for handling merchandise-related requests
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/merchandise"})
public class MerchandiseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
       
	/**
	 * Constructor initializes the ProductService
	 */
	public MerchandiseController() {
		super();
		this.productService = new ProductService();
	}

	/**
	 * Handles GET requests to display merchandise
	 * Retrieves merchandise from the database and forwards to the merchandise JSP
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get merchandise from the database
		List<ProductModel> merchandise = productService.getProductsByCategory("merchandise");
		
		// Set the products in the request
		request.setAttribute("products", merchandise);
		
		// Forward to the merchandise JSP
		request.getRequestDispatcher("/WEB-INF/pages/merchandise.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for merchandise actions
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

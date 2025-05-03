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
 * Controller for handling equipment-related requests
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/equipments"})
public class EquipmentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
       
	/**
	 * Constructor initializes the ProductService
	 */
	public EquipmentsController() {
		super();
		this.productService = new ProductService();
	}

	/**
	 * Handles GET requests to display equipment
	 * Retrieves equipment from the database and forwards to the equipments JSP
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get equipment from the database
		List<ProductModel> equipment = productService.getProductsByCategory("equipment");
		
		// Set the products in the request
		request.setAttribute("products", equipment);
		
		// Forward to the equipments JSP
		request.getRequestDispatcher("/WEB-INF/pages/equipments.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for equipment actions
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

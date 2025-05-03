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
 * @author  Amiks Karki
 * Servlet implementation class Home
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/supplements"})
public class SupplementsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplementsController() {
        super();
        this.productService = new ProductService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// Get supplements from the database
		List<ProductModel> supplements = productService.getProductsByCategory("supplement");
		
		// Set the products in the request
		request.setAttribute("products", supplements);
		
		// Forward to the supplements JSP
		request.getRequestDispatcher("/WEB-INF/pages/supplements.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

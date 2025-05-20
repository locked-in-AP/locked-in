package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ProductsController handles HTTP requests for the main products page.
 * 
 * It serves as a central hub for product browsing, currently redirecting to the
 * supplements page as the default product category. The controller can be extended
 * to provide a combined view of all product categories.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/products" })
public class ProductsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Initializes the ProductsController.
     * This controller doesn't require any service dependencies.
     */
    public ProductsController() {
        super();
    }
    
    /**
     * Handles GET requests to display all products.
     * 
     * Currently redirects to the supplements page as the default product category.
     * Can be modified to show a combined view of all product categories.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to supplements by default (or could create a combined products page)
        response.sendRedirect(request.getContextPath() + "/supplements");
    }
    
    /**
     * Handles POST requests for product actions.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing product action data
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
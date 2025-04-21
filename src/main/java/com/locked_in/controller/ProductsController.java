package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for handling products listing
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/products" })
public class ProductsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor
     */
    public ProductsController() {
        super();
    }
    
    /**
     * Handles GET requests to display all products
     * Shows a combined view of all product categories
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect to supplements by default (or could create a combined products page)
        response.sendRedirect(request.getContextPath() + "/supplements");
    }
    
    /**
     * Handles POST requests for product actions
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 
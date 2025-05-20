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
 * SearchController handles HTTP requests for product search functionality.
 * 
 * It processes search queries and displays matching products based on
 * various criteria including product name, description, and category.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/search" })
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService;
    
    /**
     * Initializes the SearchController with an instance of ProductService.
     * Sets up the service for retrieving product search results.
     */
    public SearchController() {
        super();
        this.productService = new ProductService();
    }
    
    /**
     * Handles GET requests for product search.
     * 
     * Processes the search query, retrieves matching products, and forwards
     * to the search results view JSP with the results.
     *
     * @param request  the HTTP request containing search parameters
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get search query from request
        String query = request.getParameter("q");
        String category = request.getParameter("category");
        
        // Get sort parameter from the request, default to "relevancy" if not specified
        String sortBy = request.getParameter("sort");
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "relevancy";
        }
        
        // Search products
        List<ProductModel> searchResults = productService.searchProducts(query, category, sortBy);
        
        // Set the search results and parameters in the request
        request.setAttribute("products", searchResults);
        request.setAttribute("searchQuery", query);
        request.setAttribute("currentCategory", category);
        request.setAttribute("currentSort", sortBy);
        
        // Forward to the search results JSP
        request.getRequestDispatcher("/WEB-INF/pages/search-results.jsp").forward(request, response);
    }
    
    /**
     * Handles POST requests for product search.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing client request information
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
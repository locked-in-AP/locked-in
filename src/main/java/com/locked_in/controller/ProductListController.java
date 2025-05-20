package com.locked_in.controller;
import com.locked_in.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.locked_in.service.ProductService;
import com.locked_in.model.ProductModel;
import com.locked_in.model.UserModel;

import java.util.List;

/**
 * ProductListController handles HTTP requests for product listing pages.
 * 
 * It serves product listing pages for different categories (supplements, equipment,
 * merchandise), displaying filtered and sorted product information.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/productList" })
public class ProductListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    /**
     * Initializes the ProductListController with an instance of ProductService.
     * Sets up the service for retrieving product information.
     */
    public ProductListController() {
        super();
        productService = new ProductService();
    }

    /**
     * Handles GET requests to display product listings.
     * 
     * Retrieves products based on category, filters, and sorting options.
     * Forwards to the appropriate product listing view JSP.
     *
     * @param request  the HTTP request containing category, filter, and sort parameters
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ProductModel> allProducts = productService.getAllProducts("newest");
        request.setAttribute("products", allProducts);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
} 
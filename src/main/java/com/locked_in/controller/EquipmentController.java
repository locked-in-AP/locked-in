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
@WebServlet(asyncSupported = true, urlPatterns = { "/equipment"})
public class EquipmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService;
       
    /**
     * Constructor initializes the ProductService
     */
    public EquipmentController() {
        super();
        this.productService = new ProductService();
    }

    /**
     * Handles GET requests to display equipment
     * Retrieves equipment from the database and forwards to the equipment JSP
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the sort parameter from the request, default to "relevancy" if not specified
        String sortBy = request.getParameter("sort");
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "relevancy";
        }
        
        // Get equipment from the database with sorting
        List<ProductModel> equipment = productService.getProductsByCategory("equipment", sortBy);
        
        // Set the products and sort parameter in the request
        request.setAttribute("products", equipment);
        request.setAttribute("currentSort", sortBy);
        
        // Forward to the equipment JSP
        request.getRequestDispatcher("/WEB-INF/pages/equipments.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for equipment actions
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
} 
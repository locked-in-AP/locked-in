package com.locked_in.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.locked_in.model.ProductModel;
import com.locked_in.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * AddProductController handles HTTP requests for adding new products.
 * 
 * It manages the product creation workflow, including form handling, image upload,
 * and product data persistence. Delegates product operations to ProductService
 * and handles multipart form data for file uploads.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/addProduct" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductService productService;
    private static final String UPLOAD_DIR = "resources/images/products";

    /**
     * Initializes the AddProductController with an instance of ProductService.
     * Sets up the service for handling product creation operations.
     */
    public AddProductController() {
        this.productService = new ProductService();
    }

    /**
     * Handles GET requests to display the add product form.
     * 
     * Forwards the request to the add product JSP located in /WEB-INF/pages/addProduct.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for product creation.
     * 
     * Processes the product form submission, including image upload and product data.
     * Creates a new product in the database and handles file storage.
     *
     * @param request  the HTTP request containing product form data and image
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get form parameters
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");
            String tags = request.getParameter("tags");
            String priceStr = request.getParameter("price");
            String stockQuantityStr = request.getParameter("stockQuantity");
            String weightStr = request.getParameter("weight");
            String dimensions = request.getParameter("dimensions");

            // Handle image URL input
            String imageUrl = request.getParameter("image");

            // Create ProductModel
            ProductModel product = new ProductModel(
                name,
                description,
                brand,
                category,
                tags,
                new BigDecimal(priceStr),
                Integer.parseInt(stockQuantityStr),
                new BigDecimal(weightStr),
                imageUrl,
                dimensions
            );

            // Add product to database
            boolean success = productService.createProduct(product);

            if (success) {
                request.setAttribute("success", "Product added successfully!");
            } else {
                request.setAttribute("error", "Failed to add product. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the product: " + e.getMessage());
        }

        // Forward back to the form
        request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
    }
} 
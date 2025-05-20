package com.locked_in.controller;

import com.locked_in.model.ProductModel;
import com.locked_in.service.ProductService;
import com.locked_in.service.UserService;
import com.locked_in.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * UpdateProductController handles HTTP requests for product updates.
 * 
 * It processes requests to modify existing product information, validating
 * admin authentication and product existence before performing updates.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updateProduct" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private UserService userService;
    private static final String UPLOAD_DIR = "resources/images/products";

    /**
     * Initializes the UpdateProductController with an instance of ProductService.
     * Sets up the service for handling product update operations.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display the product update form.
     * 
     * Retrieves product information based on the provided ID and forwards
     * to the update product JSP with the product details.
     *
     * @param request  the HTTP request containing product ID
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                ProductModel product = productService.getProductById(productId);
                if (product != null) {
                    request.setAttribute("product", product);
                } else {
                    request.setAttribute("error", "Product not found.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid product ID.");
            }
        }
        request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for product updates.
     * 
     * Validates admin authentication and processes product information updates.
     * Updates product details in the database and redirects with appropriate messages.
     *
     * @param request  the HTTP request containing updated product information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            request.setAttribute("error", "Product ID is required.");
            request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            ProductModel existing = productService.getProductById(productId);
            if (existing == null) {
                request.setAttribute("error", "Product with the given ID does not exist.");
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Create a new product model with only the updated values
            ProductModel product = new ProductModel();
            product.setProductId(productId);
            
            // Only set fields that have been provided
            String name = request.getParameter("name");
            if (name != null && !name.isEmpty()) {
                product.setName(name);
            }
            
            String description = request.getParameter("description");
            if (description != null && !description.isEmpty()) {
                product.setDescription(description);
            }
            
            String brand = request.getParameter("brand");
            if (brand != null && !brand.isEmpty()) {
                product.setBrand(brand);
            }
            
            String category = request.getParameter("category");
            if (category != null && !category.isEmpty()) {
                product.setCategory(category);
            }
            
            String tags = request.getParameter("tags");
            if (tags != null && !tags.isEmpty()) {
                product.setTags(tags);
            }
            
            String priceStr = request.getParameter("price");
            if (priceStr != null && !priceStr.isEmpty()) {
                try {
                    product.setPrice(new BigDecimal(priceStr));
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid price format.");
                    request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                    return;
                }
            }
            
            String stockQuantityStr = request.getParameter("stockQuantity");
            if (stockQuantityStr != null && !stockQuantityStr.isEmpty()) {
                try {
                    product.setStockQuantity(Integer.parseInt(stockQuantityStr));
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid stock quantity format.");
                    request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                    return;
                }
            }
            
            String weightStr = request.getParameter("weight");
            if (weightStr != null && !weightStr.isEmpty()) {
                try {
                    product.setWeight(new BigDecimal(weightStr));
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid weight format.");
                    request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                    return;
                }
            }
            
            // Handle image URL input
            String imageUrl = request.getParameter("image");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                product.setImage(imageUrl);
            }
            
            String dimensions = request.getParameter("dimensions");
            if (dimensions != null && !dimensions.isEmpty()) {
                product.setDimensions(dimensions);
            }

            // Merge with existing values for fields not entered
            if (product.getName() == null) product.setName(existing.getName());
            if (product.getDescription() == null) product.setDescription(existing.getDescription());
            if (product.getBrand() == null) product.setBrand(existing.getBrand());
            if (product.getCategory() == null) product.setCategory(existing.getCategory());
            if (product.getTags() == null) product.setTags(existing.getTags());
            if (product.getPrice() == null) product.setPrice(existing.getPrice());
            if (product.getStockQuantity() == null) product.setStockQuantity(existing.getStockQuantity());
            if (product.getWeight() == null) product.setWeight(existing.getWeight());
            if (product.getImage() == null) product.setImage(existing.getImage());
            if (product.getDimensions() == null) product.setDimensions(existing.getDimensions());

            boolean updated = productService.updateProduct(product);
            if (updated) {
                request.setAttribute("success", "Product updated successfully!");
                response.sendRedirect(request.getContextPath() + "/admindashboard?success=Product updated successfully!");
                return;
            } else {
                request.setAttribute("error", "Failed to update product. Please check the ID and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
    }

    /**
     * Retrieves a new value if provided, otherwise returns the existing value.
     * 
     * Used to handle form field updates where empty or null values should
     * preserve the existing data rather than overwriting it with null.
     *
     * @param newValue      the new value to check
     * @param existingValue the existing value to return if newValue is null or empty
     * @return the new value if provided and non-empty, otherwise the existing value
     */
    private String getOrDefault(String newValue, String existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            return newValue;
        }
        return existingValue;
    }

    /**
     * Retrieves a new BigDecimal value if provided, otherwise returns the existing value.
     * 
     * Used to handle price and weight updates, preserving existing values when
     * new values are invalid or not provided. Handles NumberFormatException
     * by returning the existing value.
     *
     * @param newValue      the new value to parse and check
     * @param existingValue the existing value to return if newValue is invalid
     * @return the parsed new value if valid, otherwise the existing value
     */
    private BigDecimal getPriceOrDefault(String newValue, BigDecimal existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            try {
                return new BigDecimal(newValue);
            } catch (NumberFormatException e) {
                return existingValue;
            }
        }
        return existingValue;
    }

    /**
     * Retrieves a new integer value if provided, otherwise returns the existing value.
     * 
     * Used to handle numeric field updates like stock quantity, preserving
     * existing values when new values are invalid or not provided. Handles
     * NumberFormatException by returning the existing value.
     *
     * @param newValue      the new value to parse and check
     * @param existingValue the existing value to return if newValue is invalid
     * @return the parsed new value if valid, otherwise the existing value
     */
    private int getIntOrDefault(String newValue, int existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            try {
                return Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                return existingValue;
            }
        }
        return existingValue;
    }
} 
package com.locked_in.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.locked_in.model.ProductModel;
import com.locked_in.model.UserModel;
import com.locked_in.service.ProductService;
import com.locked_in.service.UserService;
import com.locked_in.util.ValidationUtil;
import com.locked_in.util.SessionUtil;

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
    private final UserService userService;
    private static final String UPLOAD_DIR = "resources/images/products";

    /**
     * Initializes the AddProductController with an instance of ProductService.
     * Sets up the service for handling product creation operations.
     */
    public AddProductController() {
        this.productService = new ProductService();
        this.userService = new UserService();
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
        // Get admin's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email != null) {
            // Get admin's user details including profile picture
            UserModel adminUser = userService.getUserByEmail(email);
            if (adminUser != null) {
                request.setAttribute("userDetails", adminUser);
            }
        }

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
        int errors = 0;

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
        String imageUrl = request.getParameter("image");

        // Validate name
        if (ValidationUtil.isNullOrEmpty(name)) {
            request.setAttribute("nameError", "Product name is required.");
            errors++;
        } else if (name.length() < 3 || name.length() > 100) {
            request.setAttribute("nameError", "Product name must be between 3 and 100 characters.");
            errors++;
        }

        // Validate description
        if (ValidationUtil.isNullOrEmpty(description)) {
            request.setAttribute("descriptionError", "Product description is required.");
            errors++;
        } else if (description.length() < 10 || description.length() > 1000) {
            request.setAttribute("descriptionError", "Description must be between 10 and 1000 characters.");
            errors++;
        }

        // Validate brand
        if (ValidationUtil.isNullOrEmpty(brand)) {
            request.setAttribute("brandError", "Brand name is required.");
            errors++;
        } else if (brand.length() < 2 || brand.length() > 50) {
            request.setAttribute("brandError", "Brand name must be between 2 and 50 characters.");
            errors++;
        }

        // Validate category
        if (ValidationUtil.isNullOrEmpty(category)) {
            request.setAttribute("categoryError", "Category is required.");
            errors++;
        } else if (!category.matches("^(equipment|supplement|merchandise)$")) {
            request.setAttribute("categoryError", "Invalid category selected.");
            errors++;
        }

        // Validate tags
        if (ValidationUtil.isNullOrEmpty(tags)) {
            request.setAttribute("tagsError", "At least one tag is required.");
            errors++;
        } else {
            String[] tagArray = tags.split(",");
            for (String tag : tagArray) {
                String trimmed = tag.trim();
                if (trimmed.length() < 2 || trimmed.length() > 20) {
                    request.setAttribute("tagsError", "Each tag must be between 2 and 20 characters.");
                    errors++;
                    break;
                }
            }
        }

        // Validate price
        BigDecimal price = null;
        try {
            if (ValidationUtil.isNullOrEmpty(priceStr)) {
                request.setAttribute("priceError", "Price is required.");
                errors++;
            } else {
                price = new BigDecimal(priceStr);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    request.setAttribute("priceError", "Price must be greater than 0.");
                    errors++;
                } else if (price.compareTo(new BigDecimal("999999.99")) > 0) {
                    request.setAttribute("priceError", "Price cannot exceed $999,999.99.");
                    errors++;
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("priceError", "Invalid price format.");
            errors++;
        }

        // Validate stock quantity
        Integer stockQuantity = null;
        try {
            if (ValidationUtil.isNullOrEmpty(stockQuantityStr)) {
                request.setAttribute("stockQuantityError", "Stock quantity is required.");
                errors++;
            } else {
                stockQuantity = Integer.parseInt(stockQuantityStr);
                if (stockQuantity < 0) {
                    request.setAttribute("stockQuantityError", "Stock quantity cannot be negative.");
                    errors++;
                } else if (stockQuantity > 10000) {
                    request.setAttribute("stockQuantityError", "Stock quantity cannot exceed 10,000.");
                    errors++;
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("stockQuantityError", "Invalid stock quantity format.");
            errors++;
        }

        // Validate weight
        BigDecimal weight = null;
        try {
            if (ValidationUtil.isNullOrEmpty(weightStr)) {
                request.setAttribute("weightError", "Weight is required.");
                errors++;
            } else {
                weight = new BigDecimal(weightStr);
                if (weight.compareTo(BigDecimal.ZERO) <= 0) {
                    request.setAttribute("weightError", "Weight must be greater than 0.");
                    errors++;
                } else if (weight.compareTo(new BigDecimal("1000")) > 0) {
                    request.setAttribute("weightError", "Weight cannot exceed 1000 kg.");
                    errors++;
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("weightError", "Invalid weight format.");
            errors++;
        }

        // Validate dimensions
        if (ValidationUtil.isNullOrEmpty(dimensions)) {
            request.setAttribute("dimensionsError", "Dimensions are required.");
            errors++;
        } else if (!dimensions.matches("^\\d+x\\d+x\\d+$")) {
            request.setAttribute("dimensionsError", "Dimensions must be in format: length x width x height (e.g., 10x10x10)");
            errors++;
        }

        // Validate image URL
        if (ValidationUtil.isNullOrEmpty(imageUrl)) {
            request.setAttribute("imageError", "Image URL is required.");
            errors++;
        } else if (!imageUrl.matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
            request.setAttribute("imageError", "Please enter a valid image URL.");
            errors++;
        }

        // If there are validation errors, preserve form data and return to form
        if (errors > 0) {
            handleError(request, response, "You have " + errors + " invalid field(s).");
            return;
        }

        // Create product model
        ProductModel product = new ProductModel(
            name,
            description,
            brand,
            category,
            tags,
            price,
            stockQuantity,
            weight,
            imageUrl,
            dimensions
        );

        // Add product to database
        boolean success = productService.createProduct(product);
        if (success) {
            handleSuccess(request, response, "Product added successfully!", "/admindashboard");
        } else {
            handleError(request, response, "Failed to add product. Please try again.");
        }
    }

    /**
     * Handles successful product addition by redirecting to the specified page with a success message.
     * 
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param message  the success message to display
     * @param page     the page to redirect to
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while redirecting
     */
    private void handleSuccess(HttpServletRequest request, HttpServletResponse response,
                             String message, String page)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + page + "?success=" + message);
    }

    /**
     * Handles product addition errors by preserving form data and displaying error messages.
     * 
     * @param request  the HTTP request containing form data and for setting attributes
     * @param response the HTTP response for forwarding
     * @param message  the error message to display
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        // Preserve form data
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("description", request.getParameter("description"));
        request.setAttribute("brand", request.getParameter("brand"));
        request.setAttribute("category", request.getParameter("category"));
        request.setAttribute("tags", request.getParameter("tags"));
        request.setAttribute("price", request.getParameter("price"));
        request.setAttribute("stockQuantity", request.getParameter("stockQuantity"));
        request.setAttribute("weight", request.getParameter("weight"));
        request.setAttribute("dimensions", request.getParameter("dimensions"));
        request.setAttribute("image", request.getParameter("image"));
        
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/pages/addProduct.jsp").forward(request, response);
    }
} 
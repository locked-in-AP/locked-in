package com.locked_in.controller;

import com.locked_in.model.ProductModel;
import com.locked_in.service.ProductService;
import com.locked_in.service.UserService;
import com.locked_in.util.ValidationUtil;
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

            // Create a new product model with only the updated values
            ProductModel product = new ProductModel();
            product.setProductId(productId);

            // Validate name if provided
            if (name != null && !name.isEmpty()) {
                if (!ValidationUtil.isAlphanumericStartingWithLetter(name)) {
                    request.setAttribute("nameError", "Product name must start with a letter and contain only letters and numbers.");
                    errors++;
                } else if (name.length() < 3 || name.length() > 100) {
                    request.setAttribute("nameError", "Product name must be between 3 and 100 characters.");
                    errors++;
                } else {
                    product.setName(name);
                }
            }

            // Validate description if provided
            if (description != null && !description.isEmpty()) {
                if (description.length() < 10 || description.length() > 1000) {
                    request.setAttribute("descriptionError", "Description must be between 10 and 1000 characters.");
                    errors++;
                } else {
                    product.setDescription(description);
                }
            }

            // Validate brand if provided
            if (brand != null && !brand.isEmpty()) {
                if (!ValidationUtil.isAlphanumericStartingWithLetter(brand)) {
                    request.setAttribute("brandError", "Brand name must start with a letter and contain only letters and numbers.");
                    errors++;
                } else if (brand.length() < 2 || brand.length() > 50) {
                    request.setAttribute("brandError", "Brand name must be between 2 and 50 characters.");
                    errors++;
                } else {
                    product.setBrand(brand);
                }
            }

            // Validate category if provided
            if (category != null && !category.isEmpty()) {
                if (!category.matches("^(equipment|supplement|merchandise)$")) {
                    request.setAttribute("categoryError", "Invalid category selected.");
                    errors++;
                } else {
                    product.setCategory(category);
                }
            }

            // Validate tags if provided
            if (tags != null && !tags.isEmpty()) {
                String[] tagArray = tags.split(",");
                boolean tagsValid = true;
                for (String tag : tagArray) {
                    if (!ValidationUtil.isAlphanumericStartingWithLetter(tag.trim())) {
                        request.setAttribute("tagsError", "Each tag must start with a letter and contain only letters and numbers.");
                        tagsValid = false;
                        errors++;
                        break;
                    } else if (tag.trim().length() < 2 || tag.trim().length() > 20) {
                        request.setAttribute("tagsError", "Each tag must be between 2 and 20 characters.");
                        tagsValid = false;
                        errors++;
                        break;
                    }
                }
                if (tagsValid) {
                    product.setTags(tags);
                }
            }

            // Validate price if provided
            if (priceStr != null && !priceStr.isEmpty()) {
                try {
                    BigDecimal price = new BigDecimal(priceStr);
                    if (price.compareTo(BigDecimal.ZERO) <= 0) {
                        request.setAttribute("priceError", "Price must be greater than 0.");
                        errors++;
                    } else if (price.compareTo(new BigDecimal("999999.99")) > 0) {
                        request.setAttribute("priceError", "Price cannot exceed $999,999.99.");
                        errors++;
                    } else {
                        product.setPrice(price);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("priceError", "Invalid price format.");
                    errors++;
                }
            }

            // Validate stock quantity if provided
            if (stockQuantityStr != null && !stockQuantityStr.isEmpty()) {
                try {
                    int stockQuantity = Integer.parseInt(stockQuantityStr);
                    if (stockQuantity < 0) {
                        request.setAttribute("stockQuantityError", "Stock quantity cannot be negative.");
                        errors++;
                    } else if (stockQuantity > 10000) {
                        request.setAttribute("stockQuantityError", "Stock quantity cannot exceed 10,000.");
                        errors++;
                    } else {
                        product.setStockQuantity(stockQuantity);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("stockQuantityError", "Invalid stock quantity format.");
                    errors++;
                }
            }

            // Validate weight if provided
            if (weightStr != null && !weightStr.isEmpty()) {
                try {
                    BigDecimal weight = new BigDecimal(weightStr);
                    if (weight.compareTo(BigDecimal.ZERO) <= 0) {
                        request.setAttribute("weightError", "Weight must be greater than 0.");
                        errors++;
                    } else if (weight.compareTo(new BigDecimal("1000")) > 0) {
                        request.setAttribute("weightError", "Weight cannot exceed 1000 kg.");
                        errors++;
                    } else {
                        product.setWeight(weight);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("weightError", "Invalid weight format.");
                    errors++;
                }
            }

            // Validate dimensions if provided
            if (dimensions != null && !dimensions.isEmpty()) {
                if (!dimensions.matches("^\\d+x\\d+x\\d+$")) {
                    request.setAttribute("dimensionsError", "Dimensions must be in format: length x width x height (e.g., 10x10x10)");
                    errors++;
                } else {
                    product.setDimensions(dimensions);
                }
            }

            // Validate image URL if provided
            if (imageUrl != null && !imageUrl.isEmpty()) {
                if (!imageUrl.matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                    request.setAttribute("imageError", "Please enter a valid image URL.");
                    errors++;
                } else {
                    product.setImage(imageUrl);
                }
            }

            // If there are validation errors, preserve form data and return to form
            if (errors > 0) {
                // Preserve form data, using existing values as fallback
                request.setAttribute("product", existing);
                request.setAttribute("name", name != null && !name.isEmpty() ? name : existing.getName());
                request.setAttribute("description", description != null && !description.isEmpty() ? description : existing.getDescription());
                request.setAttribute("brand", brand != null && !brand.isEmpty() ? brand : existing.getBrand());
                request.setAttribute("category", category != null && !category.isEmpty() ? category : existing.getCategory());
                request.setAttribute("tags", tags != null && !tags.isEmpty() ? tags : existing.getTags());
                request.setAttribute("price", priceStr != null && !priceStr.isEmpty() ? priceStr : existing.getPrice().toString());
                request.setAttribute("stockQuantity", stockQuantityStr != null && !stockQuantityStr.isEmpty() ? stockQuantityStr : String.valueOf(existing.getStockQuantity()));
                request.setAttribute("weight", weightStr != null && !weightStr.isEmpty() ? weightStr : existing.getWeight().toString());
                request.setAttribute("dimensions", dimensions != null && !dimensions.isEmpty() ? dimensions : existing.getDimensions());
                request.setAttribute("image", imageUrl != null && !imageUrl.isEmpty() ? imageUrl : existing.getImage());
                request.setAttribute("error", "You have " + errors + " invalid field(s).");
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Update product in database
            boolean success = productService.updateProduct(product);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admindashboard?success=Product updated successfully");
            } else {
                request.setAttribute("error", "Failed to update product. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid product ID format.");
            request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
        }
    }
} 
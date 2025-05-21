package com.locked_in.controller;

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
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UpdateProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private UserService userService;
    private static final String UPLOAD_DIR = "resources/images/products";

    /**
     * Initializes the UpdateProductController with instances of required services.
     * Sets up ProductService and UserService for handling product update operations.
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
        // Get admin's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get admin's user details including profile picture
        UserModel adminUser = userService.getUserByEmail(email);
        if (adminUser != null) {
            request.setAttribute("userDetails", adminUser);
        }

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
            System.out.println("UpdateProductController - Error: Product ID is required");
            request.setAttribute("error", "Product ID is required.");
            request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            ProductModel existing = productService.getProductById(productId);
            if (existing == null) {
                System.out.println("UpdateProductController - Error: Product with ID " + productId + " does not exist");
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

            // Check if any field is empty
            if (name == null || name.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                brand == null || brand.trim().isEmpty() ||
                category == null || category.trim().isEmpty() ||
                tags == null || tags.trim().isEmpty() ||
                priceStr == null || priceStr.trim().isEmpty() ||
                stockQuantityStr == null || stockQuantityStr.trim().isEmpty() ||
                weightStr == null || weightStr.trim().isEmpty() ||
                dimensions == null || dimensions.trim().isEmpty() ||
                imageUrl == null || imageUrl.trim().isEmpty()) {
                request.setAttribute("error", "All fields are required. Please fill in all fields.");
                request.setAttribute("product", existing);
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Check if any field has changed
            boolean hasChanges = false;
            if (!name.equals(existing.getName()) ||
                !description.equals(existing.getDescription()) ||
                !brand.equals(existing.getBrand()) ||
                !category.equals(existing.getCategory()) ||
                !tags.equals(existing.getTags()) ||
                !priceStr.equals(existing.getPrice().toString()) ||
                !stockQuantityStr.equals(String.valueOf(existing.getStockQuantity())) ||
                !weightStr.equals(existing.getWeight().toString()) ||
                !dimensions.equals(existing.getDimensions()) ||
                !imageUrl.equals(existing.getImage())) {
                hasChanges = true;
            }

            if (!hasChanges) {
                // Get admin's email from session and set user details
                String email = (String) SessionUtil.getAttribute(request, "email");
                if (email != null) {
                    UserModel adminUser = userService.getUserByEmail(email);
                    if (adminUser != null) {
                        request.setAttribute("userDetails", adminUser);
                    }
                }
                
                request.setAttribute("error", "No changes detected. Please modify at least one field to update the product.");
                request.setAttribute("product", existing);
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Create a new product model with the updated values
            ProductModel product = new ProductModel();
            product.setProductId(productId);

            // Validate name
            if (name.length() < 3 || name.length() > 100) {
                request.setAttribute("nameError", "Product name must be between 3 and 100 characters.");
                errors++;
            } else {
                product.setName(name);
            }

            // Validate description
            if (description.length() < 10 || description.length() > 1000) {
                request.setAttribute("descriptionError", "Description must be between 10 and 1000 characters.");
                errors++;
            } else {
                product.setDescription(description);
            }

            // Validate brand
            if (brand.length() < 2 || brand.length() > 50) {
                request.setAttribute("brandError", "Brand name must be between 2 and 50 characters.");
                errors++;
            } else {
                product.setBrand(brand);
            }

            // Validate category
            if (!category.matches("^(equipment|supplement|merchandise)$")) {
                request.setAttribute("categoryError", "Invalid category selected.");
                errors++;
            } else {
                product.setCategory(category);
            }

            // Validate tags
            String[] tagArray = tags.split(",");
            boolean tagsValid = true;
            for (String tag : tagArray) {
                String trimmed = tag.trim();
                if (trimmed.length() < 2 || trimmed.length() > 20) {
                    request.setAttribute("tagsError", "Each tag must be between 2 and 20 characters.");
                    tagsValid = false;
                    errors++;
                    break;
                }
            }
            if (tagsValid) {
                product.setTags(tags);
            }

            // Validate price
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

            // Validate stock quantity
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

            // Validate weight
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

            // Validate dimensions
            if (!dimensions.matches("^\\d+x\\d+x\\d+$")) {
                request.setAttribute("dimensionsError", "Dimensions must be in format: length x width x height (e.g., 10x10x10)");
                errors++;
            } else {
                // Additional validation for reasonable dimension values
                String[] dims = dimensions.split("x");
                try {
                    int length = Integer.parseInt(dims[0]);
                    int width = Integer.parseInt(dims[1]);
                    int height = Integer.parseInt(dims[2]);
                    
                    if (length <= 0 || width <= 0 || height <= 0) {
                        request.setAttribute("dimensionsError", "All dimensions must be greater than 0.");
                        errors++;
                    } else if (length > 1000 || width > 1000 || height > 1000) {
                        request.setAttribute("dimensionsError", "No dimension can exceed 1000 units.");
                        errors++;
                    } else {
                        product.setDimensions(dimensions);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("dimensionsError", "Invalid dimension values.");
                    errors++;
                }
            }

            // Validate image URL
            if (!imageUrl.matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                request.setAttribute("imageError", "Please enter a valid image URL.");
                errors++;
            } else {
                product.setImage(imageUrl);
            }

            // If there are validation errors, preserve form data and return to form
            if (errors > 0) {
                System.out.println("UpdateProductController - Found " + errors + " validation errors");
                
                // Get admin's email from session and set user details
                String email = (String) SessionUtil.getAttribute(request, "email");
                if (email != null) {
                    UserModel adminUser = userService.getUserByEmail(email);
                    if (adminUser != null) {
                        request.setAttribute("userDetails", adminUser);
                    }
                }
                
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
                
                // Create a detailed error message
                StringBuilder errorMessage = new StringBuilder("Validation Errors:<br>");
                if (request.getAttribute("nameError") != null) {
                    errorMessage.append("- Name: ").append(request.getAttribute("nameError")).append("<br>");
                }
                if (request.getAttribute("descriptionError") != null) {
                    errorMessage.append("- Description: ").append(request.getAttribute("descriptionError")).append("<br>");
                }
                if (request.getAttribute("brandError") != null) {
                    errorMessage.append("- Brand: ").append(request.getAttribute("brandError")).append("<br>");
                }
                if (request.getAttribute("categoryError") != null) {
                    errorMessage.append("- Category: ").append(request.getAttribute("categoryError")).append("<br>");
                }
                if (request.getAttribute("tagsError") != null) {
                    errorMessage.append("- Tags: ").append(request.getAttribute("tagsError")).append("<br>");
                }
                if (request.getAttribute("priceError") != null) {
                    errorMessage.append("- Price: ").append(request.getAttribute("priceError")).append("<br>");
                }
                if (request.getAttribute("stockQuantityError") != null) {
                    errorMessage.append("- Stock Quantity: ").append(request.getAttribute("stockQuantityError")).append("<br>");
                }
                if (request.getAttribute("weightError") != null) {
                    errorMessage.append("- Weight: ").append(request.getAttribute("weightError")).append("<br>");
                }
                if (request.getAttribute("dimensionsError") != null) {
                    errorMessage.append("- Dimensions: ").append(request.getAttribute("dimensionsError")).append("<br>");
                }
                if (request.getAttribute("imageError") != null) {
                    errorMessage.append("- Image: ").append(request.getAttribute("imageError")).append("<br>");
                }
                
                request.setAttribute("error", errorMessage.toString());
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Update product in database
            boolean success = productService.updateProduct(product);
            if (success) {
                System.out.println("UpdateProductController - Product updated successfully");
                response.sendRedirect(request.getContextPath() + "/admindashboard?success=Product updated successfully");
            } else {
                System.out.println("UpdateProductController - Failed to update product in database");
                request.setAttribute("error", "Failed to update product. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("UpdateProductController - Error: Invalid product ID format - " + e.getMessage());
            request.setAttribute("error", "Invalid product ID format.");
            request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
        }
    }
} 
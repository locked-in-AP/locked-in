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

@WebServlet(asyncSupported = true, urlPatterns = { "/updateProduct" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private UserService userService;
    private static final String UPLOAD_DIR = "resources/images/products";

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        try {
            int productId = Integer.parseInt(productIdStr);
            ProductModel existing = productService.getProductById(productId);
            if (existing == null) {
                request.setAttribute("error", "Product with the given ID does not exist.");
                request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
                return;
            }

            // Handle file upload
            Part filePart = request.getPart("image");
            String fileName = existing.getImage(); // Default to existing image
            if (filePart != null && filePart.getSize() > 0) {
                // Get the application's real path
                String appPath = request.getServletContext().getRealPath("");
                String uploadPath = appPath + File.separator + UPLOAD_DIR;
                
                // Create upload directory if it doesn't exist
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                // Generate unique filename
                String originalFileName = filePart.getSubmittedFileName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                fileName = UUID.randomUUID().toString() + fileExtension;
                
                // Save the file
                Path filePath = Paths.get(uploadPath, fileName);
                Files.copy(filePart.getInputStream(), filePath);
                
                // Set the image path for the database
                fileName = UPLOAD_DIR + "/" + fileName;
            }

            // For each field, use the new value if provided, otherwise use the existing value
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");
            String tags = request.getParameter("tags");
            String priceStr = request.getParameter("price");
            String stockQuantityStr = request.getParameter("stockQuantity");
            String weightStr = request.getParameter("weight");
            String dimensions = request.getParameter("dimensions");

            // Create a new product model with existing values
            ProductModel product = new ProductModel(
                productId,
                name != null && !name.isEmpty() ? name : existing.getName(),
                description != null && !description.isEmpty() ? description : existing.getDescription(),
                brand != null && !brand.isEmpty() ? brand : existing.getBrand(),
                category != null && !category.isEmpty() ? category : existing.getCategory(),
                tags != null && !tags.isEmpty() ? tags : existing.getTags(),
                priceStr != null && !priceStr.isEmpty() ? new BigDecimal(priceStr) : existing.getPrice(),
                stockQuantityStr != null && !stockQuantityStr.isEmpty() ? Integer.parseInt(stockQuantityStr) : existing.getStockQuantity(),
                weightStr != null && !weightStr.isEmpty() ? new BigDecimal(weightStr) : existing.getWeight(),
                fileName,
                dimensions != null && !dimensions.isEmpty() ? dimensions : existing.getDimensions(),
                existing.getCreatedAt()
            );
            boolean updated = productService.updateProduct(product);
            if (updated) {
                request.setAttribute("success", "Product updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update product. Please check the ID and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/updateProduct.jsp").forward(request, response);
    }

    // Helper method to use new value if provided, otherwise existing value
    private String getOrDefault(String newValue, String existingValue) {
        if (newValue != null && !newValue.isEmpty()) {
            return newValue;
        }
        return existingValue != null ? existingValue : "";
    }
} 
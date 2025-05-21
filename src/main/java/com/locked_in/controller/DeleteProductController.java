package com.locked_in.controller;

import com.locked_in.service.ProductService;
import com.locked_in.service.UserService;
import com.locked_in.model.UserModel;
import com.locked_in.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DeleteProductController handles HTTP requests for product deletion.
 * 
 * It processes requests to remove products from the system, validating admin
 * authentication and product existence before performing the deletion.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/deleteProduct" })
public class DeleteProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private UserService userService;

    /**
     * Initializes the DeleteProductController with instances of required services.
     * Sets up ProductService and UserService for handling product deletion operations.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
        userService = new UserService();
    }

    /**
     * Handles GET requests to display the product deletion form.
     * 
     * Forwards the request to the product deletion JSP located in /WEB-INF/pages/deleteProduct.jsp.
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
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get admin's user details including profile picture
        UserModel adminUser = userService.getUserByEmail(email);
        if (adminUser != null) {
            request.setAttribute("userDetails", adminUser);
        }

        request.getRequestDispatcher("/WEB-INF/pages/deleteProduct.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for product deletion.
     * 
     * Validates admin authentication and product existence before removing
     * the product from the system. Redirects with appropriate success/error messages.
     *
     * @param request  the HTTP request containing product ID to delete
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admindashboard?error=Product ID is required");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            boolean deleted = productService.deleteProduct(productId);
            
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/admindashboard?success=Product deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/admindashboard?error=Failed to delete product");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admindashboard?error=Invalid product ID");
        }
    }
} 
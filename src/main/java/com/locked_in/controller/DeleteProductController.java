package com.locked_in.controller;

import com.locked_in.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = { "/deleteProduct" })
public class DeleteProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/deleteProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        try {
            int productId = Integer.parseInt(productIdStr);
            boolean deleted = productService.deleteProduct(productId);
            if (deleted) {
                request.setAttribute("success", "Product deleted successfully!");
            } else {
                request.setAttribute("error", "Failed to delete product. Please check the ID and try again.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/deleteProduct.jsp").forward(request, response);
    }
} 
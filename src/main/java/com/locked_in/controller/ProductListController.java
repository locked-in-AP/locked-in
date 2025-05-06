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

@WebServlet(asyncSupported = true, urlPatterns = { "/productList" })
public class ProductListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductListController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService productService = new ProductService();
        List<ProductModel> allProducts = productService.getAllProducts("newest");
        request.setAttribute("products", allProducts);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
} 
package com.locked_in.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.locked_in.model.ProductModel;
import com.locked_in.model.UserModel;
import com.locked_in.service.CartService;
import com.locked_in.service.ProductService;
import com.locked_in.service.ReviewService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * Servlet implementation class Item
 */
@WebServlet("/item")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private CartService cartService;
	private UserService userService;
	private ReviewService reviewService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemController() {
        super();
        productService = new ProductService();
        cartService = new CartService();
        userService = new UserService();
        reviewService = new ReviewService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
        String productIdStr = request.getParameter("id");
        
        try {
            int productId = Integer.parseInt(productIdStr);
            ProductModel product = productService.getProductById(productId);
            
            if (product != null) {
                // Get reviews for the product
                product.setReviews(reviewService.getProductReviews(productId));
                product.setAverageRating(reviewService.getAverageRating(productId));

                // Check if user is logged in
                String email = (String) SessionUtil.getAttribute(request, "email");
                if (email != null) {
                    int userId = userService.getUserByEmail(email).getUserId();
                    product.setHasUserOrdered(reviewService.hasUserOrderedProduct(userId, productId));
                    product.setHasUserReviewed(reviewService.hasReviewed(userId, productId, 0)); // 0 for any order
                }
                
                request.setAttribute("product", product);
                // Check for any messages in the request
                String message = request.getParameter("message");
                if (message != null && !message.isEmpty()) {
                    request.setAttribute("message", message);
                }
                request.getRequestDispatcher("/WEB-INF/pages/item.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		// If action is addToCart, handle it
		if ("addToCart".equals(action)) {
            String email = (String) SessionUtil.getAttribute(request, "email");
            
            // Redirect to login if not logged in
            if (email == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            try {
                // Get user
                UserModel user = userService.getUserByEmail(email);
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }
                
                // Get product id and quantity
                String productIdStr = request.getParameter("productId");
                String quantityStr = request.getParameter("quantity");
                
                if (productIdStr == null || quantityStr == null) {
                    String errorMessage = "Missing required parameters";
                    response.sendRedirect(request.getContextPath() + "/item?id=" + productIdStr + "&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                    return;
                }
                
                try {
                    int productId = Integer.parseInt(productIdStr);
                    int quantity = Integer.parseInt(quantityStr);
                    
                    if (quantity <= 0) {
                        throw new NumberFormatException();
                    }
                    
                    // Add to cart
                    boolean success = cartService.addToCart(user.getUserId(), productId, quantity);
                    
                    // Update cart size in session
                    int cartSize = cartService.getCartSize(user.getUserId());
                    SessionUtil.setAttribute(request, "cartSize", cartSize);
                    
                    // Redirect with message only if there's an error
                    if (!success) {
                        String errorMessage = "Not enough stock available";
                        response.sendRedirect(request.getContextPath() + "/item?id=" + productId + "&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                    } else {
                        // Just redirect without a message on success
                        response.sendRedirect(request.getContextPath() + "/item?id=" + productId);
                    }
                } catch (NumberFormatException e) {
                    String errorMessage = "Invalid quantity or product ID";
                    response.sendRedirect(request.getContextPath() + "/item?id=" + productIdStr + "&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                String errorMessage = "An error occurred while processing your request";
                response.sendRedirect(request.getContextPath() + "/item?id=" + request.getParameter("productId") + "&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
            }
        } else {
            // If no specific action, forward to get method
            doGet(request, response);
        }
	}
}

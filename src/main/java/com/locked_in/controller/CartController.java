package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;

import com.locked_in.model.CartModel;
import com.locked_in.model.UserModel;
import com.locked_in.service.CartService;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * CartController handles HTTP requests related to shopping cart operations.
 * 
 * It manages cart-related functionality including viewing cart contents, adding/removing items,
 * updating quantities, and processing checkout. Delegates cart operations to CartService
 * and user-related operations to UserService.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/cart", "/cart/*" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService;
	private UserService userService;
       
    /**
     * Initializes the CartController with instances of required services.
     * Sets up CartService for cart operations and UserService for user-related operations.
     */
    public CartController() {
        super();	
        cartService = new CartService();
        userService = new UserService();
    }

	/**
	 * Handles GET requests to display cart contents.
	 * 
	 * Retrieves the current user's cart items and forwards to the cart view JSP.
	 * If user is not logged in, redirects to login page.
	 *
	 * @param request  the HTTP request containing user session information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String email = (String) SessionUtil.getAttribute(request, "email");
		
		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			UserModel user = userService.getUserByEmail(email);
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			List<CartModel> cartItems = cartService.getCartItems(user.getUserId());
			double total = 0;
			for (CartModel item : cartItems) {
				total += item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue();
			}
			
			// Update cart size in session
			int cartSize = cartService.getCartSize(user.getUserId());
			SessionUtil.setAttribute(request, "cartSize", cartSize);
			
			request.setAttribute("cartItems", cartItems);
			request.setAttribute("total", total);
			
			// Check if there's a message parameter
			String message = request.getParameter("message");
			if (message != null && !message.isEmpty()) {
			    request.setAttribute("message", message);
			}
			
			request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles POST requests for cart operations.
	 * 
	 * Processes cart actions including adding, updating, and removing items.
	 * Validates user login status, product availability, and quantity.
	 * Updates cart size in session and redirects with appropriate messages.
	 *
	 * @param request  the HTTP request containing cart action data and parameters
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs during processing
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) SessionUtil.getAttribute(request, "email");
		
		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			UserModel user = userService.getUserByEmail(email);
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			// Debug: log incoming parameters
			String action = request.getParameter("action");
			String productIdStr = request.getParameter("productId");
			String quantityStr = request.getParameter("quantity");
			System.out.println("[CartController] POST params -> action: " + action + ", productId: " + productIdStr + ", quantity: " + quantityStr);
			
			if (action == null || productIdStr == null) {
				response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("Missing required parameters", "UTF-8"));
				return;
			}

			int productId;
			try {
				productId = Integer.parseInt(productIdStr);
			} catch (NumberFormatException e) {
				response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("Invalid product ID", "UTF-8"));
				return;
			}

			boolean success = false;
			String message = "";
			
			switch (action) {
				case "add":
				case "update": {
					if (quantityStr == null) {
						response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("Missing quantity parameter", "UTF-8"));
						return;
					}
					int quantity;
					try {
						quantity = Integer.parseInt(quantityStr);
						if (quantity <= 0) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("Invalid quantity", "UTF-8"));
						return;
					}
					if (action.equals("add")) {
						success = cartService.addToCart(user.getUserId(), productId, quantity);
						message = success ? "" : "Not enough stock available";
					} else {
						success = cartService.updateCartItemQuantity(user.getUserId(), productId, quantity);
						message = success ? "" : "Not enough stock available";
					}
					break;
				}
				case "remove":
					cartService.removeFromCart(user.getUserId(), productId);
					success = true;
					message = "";
					break;
				default:
					response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("Invalid action", "UTF-8"));
					return;
			}
			
			// Get and update cart size in session
			int cartSize = cartService.getCartSize(user.getUserId());
			SessionUtil.setAttribute(request, "cartSize", cartSize);
			
			// Redirect back to cart with a message only if there's an error
			if (!success && !message.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
			} else {
				response.sendRedirect(request.getContextPath() + "/cart");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode("An error occurred while processing your request", "UTF-8"));
		}
	}
}

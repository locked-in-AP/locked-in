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
 * Servlet implementation class Cart
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/cart", "/cart/*" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService;
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();	
        cartService = new CartService();
        userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
			request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String email = (String) SessionUtil.getAttribute(request, "email");
		
		if (email == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\":\"Please log in to continue\"}");
			return;
		}

		try {
			UserModel user = userService.getUserByEmail(email);
			if (user == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"error\":\"User not found\"}");
				return;
			}

			// Debug: log incoming parameters
			String debugAction = request.getParameter("action");
			String debugProdId = request.getParameter("productId");
			String debugQty = request.getParameter("quantity");
			System.out.println("[CartController] POST params -> action: " + debugAction + ", productId: " + debugProdId + ", quantity: " + debugQty);

			String action = debugAction;
			String productIdStr = debugProdId;
			
			if (action == null || productIdStr == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\":\"Missing required parameters\"}");
				return;
			}

			int productId;
			try {
				productId = Integer.parseInt(productIdStr);
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\":\"Invalid product ID\"}");
				return;
			}

			boolean success = false;
			String message = "";
			
			switch (action) {
				case "add":
				case "update": {
					String quantityStr = request.getParameter("quantity");
					if (quantityStr == null) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("{\"error\":\"Missing quantity parameter\"}");
						return;
					}
					int quantity;
					try {
						quantity = Integer.parseInt(quantityStr);
						if (quantity <= 0) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("{\"error\":\"Invalid quantity\"}");
						return;
					}
					if (action.equals("add")) {
						success = cartService.addToCart(user.getUserId(), productId, quantity);
						message = success ? "Product added to cart" : "Not enough stock available";
					} else {
						success = cartService.updateCartItemQuantity(user.getUserId(), productId, quantity);
						message = success ? "Cart updated" : "Not enough stock available";
					}
					break;
				}
				case "remove":
					cartService.removeFromCart(user.getUserId(), productId);
					success = true;
					message = "Product removed from cart";
					break;
				default:
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write("{\"error\":\"Invalid action\"}");
					return;
			}
			
			// Get and update cart size in session
			int cartSize = cartService.getCartSize(user.getUserId());
			SessionUtil.setAttribute(request, "cartSize", cartSize);
			
			// Redirect back to cart with a message
			response.sendRedirect(request.getContextPath() + "/cart?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\":\"An error occurred while processing your request\"}");
		}
	}

}

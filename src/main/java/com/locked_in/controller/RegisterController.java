package com.locked_in.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.locked_in.model.RoleModel;
import com.locked_in.model.UserModel;
import com.locked_in.service.RegisterService;
import com.locked_in.util.ImageUtil;
import com.locked_in.util.PasswordUtil;
import com.locked_in.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * RegisterController handles user registration requests and processes form
 * submissions. 
 * 
 * It also manages file uploads and account creation.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	maxFileSize       = 1024 * 1024 * 10, // 10MB
	maxRequestSize    = 1024 * 1024 * 50  // 50MB
) 
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ImageUtil imageUtil = new ImageUtil();
	private final RegisterService registerService = new RegisterService();

	/**
	 * Handles GET requests and forwards the user to the registration page.
	 *
	 * @param req  the HttpServletRequest object that contains the request the client made to the servlet
	 * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	/**
	 * Handles POST requests for user registration, including validation, user creation, and image upload.
	 *
	 * @param req  the HttpServletRequest object that contains the request the client made to the servlet
	 * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Validate and extract user model
			String validationMessage = validateRegistrationForm(req);
			if (validationMessage != null) {
				handleError(req, resp, validationMessage);
				return;
			}

			UserModel userModel = extractUserModel(req);
			Boolean isAdded = registerService.addUser(userModel);

			if (isAdded == null) {
				handleError(req, resp, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				try {
					if (uploadImage(req)) {
						handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
					} else {
						handleError(req, resp, "Could not upload the image. Please try again later!");
					}
				} catch (IOException | ServletException e) {
					handleError(req, resp, "An error occurred while uploading the image. Please try again later!");
					e.printStackTrace(); // Log the exception
				}
			} else {
				handleError(req, resp, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}
	}

	/**
	 * Validates the registration form fields from the HTTP request.
	 *
	 * @param req the HttpServletRequest containing the registration form parameters
	 * @return a validation error message if validation fails, or null if all validations pass
	 */
	private String validateRegistrationForm(HttpServletRequest req) {
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");
		String contactNum = req.getParameter("contactNum");
		String dateJoinedStr = req.getParameter("dateJoined");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(firstName)) {
			return "First name is required.";
		}
		if (ValidationUtil.isNullOrEmpty(lastName)) {
			return "Last name is required.";
		}
		if (ValidationUtil.isNullOrEmpty(email)) {
			return "Email is required.";
		}
		if (ValidationUtil.isNullOrEmpty(password)) {
			return "Password is required.";
		}
		if (ValidationUtil.isNullOrEmpty(retypePassword)) {
			return "Please retype your password.";
		}
		if (ValidationUtil.isNullOrEmpty(contactNum)) {
			return "Contact number is required.";
		}
		if (ValidationUtil.isNullOrEmpty(dateJoinedStr)) {
			return "Date of birth is required.";
		}

		// Validate fields
		if (!ValidationUtil.isAlphanumericStartingWithLetter(firstName)) {
			return "First name must start with a letter and contain only letters and contactNums.";
		}
		if (middleName != null && !middleName.isEmpty()) {
			if (!ValidationUtil.isAlphanumericStartingWithLetter(middleName)) {
				return "Middle name must start with a letter and contain only letters and contactNums.";
			}
		}
		if (!ValidationUtil.isAlphanumericStartingWithLetter(lastName)) {
			return "Last name must start with a letter and contain only letters and contactNums.";
		}
		if (!ValidationUtil.isValidEmail(email)) {
			return "Invalid email format.";
		}
		if (!ValidationUtil.isValidPassword(password)) {
			return "Password must be at least 8 characters long, with 1 uppercase letter, 1 contactNum, and 1 symbol.";
		}
		if (!ValidationUtil.doPasswordsMatch(password, retypePassword)) {
			return "Passwords do not match.";
		}
		if (!ValidationUtil.isValidContactNum(contactNum)) {
			return "Contact number must be 10 digits and start with 98.";
		}
		try {
			LocalDate.parse(dateJoinedStr);
		} catch (Exception e) {
			return "Invalid date format. Please use YYYY-MM-DD.";
		}
		try {
			Part image = req.getPart("image");
			if (!ValidationUtil.isValidImageExtension(image)) {
				return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
			}
		} catch (IOException | ServletException e) {
			return "Error handling image file. Please ensure the file is valid.";
		}
		return null; // All validations passed
	}

	/**
	 * Extracts user information from the HTTP request and constructs a UserModel.
	 *
	 * @param req the HttpServletRequest containing form parameters and image part
	 * @return a populated UserModel instance
	 * @throws Exception if an error occurs during parsing or image handling
	 */
	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String contactNum = req.getParameter("contactNum");
		LocalDate dateJoined = LocalDate.parse(req.getParameter("dateJoined"));
		Part image = req.getPart("image");
		String imageUrl = imageUtil.getImageNameFromPart(image);
		Integer cartSize = 0;

		password = PasswordUtil.encrypt(email, password);

		return new UserModel(firstName, middleName, lastName, email, password, contactNum, dateJoined, imageUrl, cartSize, "CUSTOMER");
	}

	/**
	 * Uploads the image file from the registration form to the server directory.
	 *
	 * @param req the HttpServletRequest containing the image file part
	 * @return true if the image upload succeeds, false otherwise
	 * @throws IOException if an I/O error occurs during upload
	 * @throws ServletException if the file part cannot be retrieved
	 */
	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
	}

	/**
	 * Forwards the request to the success page with a success message.
	 *
	 * @param req the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @param message the success message to display
	 * @param redirectPage the path to the page to forward to
	 * @throws ServletException if the forwarding fails
	 * @throws IOException if an I/O error occurs
	 */
	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	/**
	 * Forwards the request to the registration page with an error message and preserves form data.
	 *
	 * @param req the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @param message the error message to display
	 * @throws ServletException if the forwarding fails
	 * @throws IOException if an I/O error occurs
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("firstName", req.getParameter("firstName"));
		req.setAttribute("middleName", req.getParameter("middleName"));
		req.setAttribute("lastName", req.getParameter("lastName"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("contactNum", req.getParameter("contactNum"));
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}

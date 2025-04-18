package com.locked_in.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.locked_in.model.UserModel;
import com.locked_in.service.RegisterService;
import com.locked_in.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final RegisterService registerService = new RegisterService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
<<<<<<< HEAD
<<<<<<< HEAD
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String repassword = request.getParameter("repassword");
			String phoneNumber = request.getParameter("phoneNumber");
			String gender = request.getParameter("gender");
			String dateOfBirthStr = request.getParameter("dateOfBirth");

			// Basic email validation
			if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
				handleError(request, response, "Please enter a valid email address.");
=======
=======
>>>>>>> 81b7e44 (Registration partially working)
//			 Validate and extract user model
			String validationMessage = validateRegistrationForm(request);
			if (validationMessage != null) {
				handleError(request, response, validationMessage);
>>>>>>> 81b7e44 (Registration partially working)
				return;
			}

			// Phone number: digits only, length 10–15
			if (!phoneNumber.matches("^[0-9]{10}$")) {
				handleError(request, response, "Please enter a valid phone number (10 digits).");
				return;
			}

			// Strong password validation
			if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]).{8,}$")) {
				handleError(request, response, "Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a digit, and a special character.");
				return;
			}

			// Match passwords
			if (!password.equals(repassword)) {
				handleError(request, response, "Passwords do not match.");
				return;
			}

			// Date of birth validation (must be at least 16 years old)
			LocalDate dateOfBirth;
			try {
				dateOfBirth = LocalDate.parse(dateOfBirthStr);
				LocalDate today = LocalDate.now();
				if (dateOfBirth.isAfter(today.minusYears(16))) {
					handleError(request, response, "You must be at least 16 years old to register.");
					return;
				}
			} catch (DateTimeParseException e) {
				handleError(request, response, "Invalid date format. Please select a valid date.");
				return;
			}

			// Hash the password
			String passwordHash = PasswordUtil.encrypt(email, password);
			
			// Create user object
			UserModel userModel = new UserModel(name, nickname, email, passwordHash, phoneNumber, gender, dateOfBirth);
			Boolean isAdded = registerService.addUser(userModel);

			if (isAdded == null) {
				handleError(request, response, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				handleSuccess(request, response, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 81b7e44 (Registration partially working)

//				try {
//					if (uploadImage(request)) {
//						handleSuccess(request, response, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
//					} else {
//						handleError(request, response, "Could not upload the image. Please try again later!");
//					}
//				} catch (IOException | ServletException e) {
//					handleError(request, response, "An error occurred while uploading the image. Please try again later!");
//					e.printStackTrace(); // Log the exception
//				}
<<<<<<< HEAD
>>>>>>> 81b7e44 (Registration partially working)
=======
>>>>>>> 81b7e44 (Registration partially working)
			} else {
				handleError(request, response, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleError(request, response, "An unexpected error occurred. Please try again later!");
		}
	}

<<<<<<< HEAD
=======
	/**
	 * Validates the registration form fields from the HTTP request.
	 *
	 * @param request the HttpServletRequest containing the registration form parameters
	 * @return a validation error message if validation fails, or null if all validations pass
	 */
	private String validateRegistrationForm(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String retypePassword = request.getParameter("retypePassword");
		String contactNum = request.getParameter("contactNum");
		// String dateJoinedStr = request.getParameter("dateJoined");

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
//		if (ValidationUtil.isNullOrEmpty(dateJoinedStr)) {
//			return "Date of birth is required.";
//		}

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
		if (!ValidationUtil.isValidPhoneNumber(contactNum)) {
			return "Contact number must be 10 digits and start with 98.";
		}
		// try {
		// 	LocalDate.parse(dateJoinedStr);
		// } catch (Exception e) {
		// 	return "Invalid date format. Please use YYYY-MM-DD.";
		// }
//		try {
//			Part image = request.getPart("image");
//			if (!ValidationUtil.isValidImageExtension(image)) {
//				return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
//			}
//		} catch (IOException | ServletException e) {
//			return "Error handling image file. Please ensure the file is valid.";
//		}
		return null; // All validations passed
	}

	/**
	 * Extracts user information from the HTTP request and constructs a UserModel.
	 *
	 * @param request the HttpServletRequest containing form parameters and image part
	 * @return a populated UserModel instance
	 * @throws Exception if an error occurs during parsing or image handling
	 */
	private UserModel extractUserModel(HttpServletRequest request) throws Exception {
		String firstName = request.getParameter("firstName");
		String middleName = request.getParameter("middleName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String contactNum = request.getParameter("contactNum");
		// LocalDate dateJoined = LocalDate.parse(request.getParameter("dateJoined"));
//		Part image = request.getPart("image");
//		String imageUrl = imageUtil.getImageNameFromPart(image);
		password = PasswordUtil.encrypt(email, password);

		return new UserModel(firstName, middleName, lastName, email, password, contactNum, null, null, null, "CUSTOMER");
	}

	/**
	 * Uploads the image file from the registration form to the server directory.
	 *
	 * @param request the HttpServletRequest containing the image file part
	 * @return true if the image upload succeeds, false otherwise
	 * @throws IOException if an I/O error occurs during upload
	 * @throws ServletException if the file part cannot be retrieved
	 */
//	private boolean uploadImage(HttpServletRequest request) throws IOException, ServletException {
//		Part image = request.getPart("image");
//		return imageUtil.uploadImage(image, request.getServletContext().getRealPath("/"), "user");
//	}

	/**
	 * Forwards the request to the success page with a success message.
	 *
	 * @param request the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @param message the success message to display
	 * @param redirectPage the path to the page to forward to
	 * @throws ServletException if the forwarding fails
	 * @throws IOException if an I/O error occurs
	 */
>>>>>>> 81b7e44 (Registration partially working)
	private void handleSuccess(HttpServletRequest request, HttpServletResponse response, String message, String redirectPage)
			throws ServletException, IOException {
		request.setAttribute("success", message);
		request.getRequestDispatcher(redirectPage).forward(request, response);
	}

	private void handleError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("error", message);
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("nickname", request.getParameter("nickname"));
		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("phoneNumber", request.getParameter("phoneNumber"));
		request.setAttribute("gender", request.getParameter("gender"));
		request.setAttribute("dateOfBirth", request.getParameter("dateOfBirth"));
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}
}

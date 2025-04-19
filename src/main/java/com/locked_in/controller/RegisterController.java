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
			} else {
				handleError(request, response, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			handleError(request, response, "An unexpected error occurred. Please try again later!");
		}
	}

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

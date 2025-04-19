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

			// Validation
			if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
				handleError(request, response, "Please enter a valid email address.");
				return;
			}

			if (!phoneNumber.matches("^[0-9]{10,15}$")) {
				handleError(request, response, "Please enter a valid phone number (10–15 digits).");
				return;
			}

			if (!password.equals(repassword)) {
				handleError(request, response, "Passwords do not match.");
				return;
			}

			LocalDate dateOfBirth;
			try {
				dateOfBirth = LocalDate.parse(dateOfBirthStr);
			} catch (DateTimeParseException e) {
				handleError(request, response, "Invalid date format. Please select a valid date.");
				return;
			}

			String passwordHash = PasswordUtil.encrypt(email, password);
			
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

package com.locked_in.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.locked_in.model.UserModel;
import com.locked_in.service.RegisterService;
import com.locked_in.util.PasswordUtil;
import com.locked_in.util.ValidationUtil;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name           = request.getParameter("name");
        String nickname       = request.getParameter("nickname");
        String email          = request.getParameter("email");
        String password       = request.getParameter("password");
        String repassword     = request.getParameter("repassword");
        String phoneNumber    = request.getParameter("phoneNumber");
        String gender         = request.getParameter("gender");
        String dateOfBirthStr = request.getParameter("dateOfBirth");

        int errors = 0;

        // Name & nickname
        if (ValidationUtil.isNullOrEmpty(name) || !ValidationUtil.isAlphabetic(name)) {
            request.setAttribute("nameError", "Name is required and must be alphabetic.");
            errors++;
        }
        if (!ValidationUtil.isNullOrEmpty(nickname)
                && !ValidationUtil.isAlphanumericStartingWithLetter(nickname)) {
            request.setAttribute("nicknameError", "Nickname must start with a letter and be alphanumeric.");
            errors++;
        }

        // Email
        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Please enter a valid email address.");
            errors++;
        }

        // Phone
        if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            request.setAttribute("phoneNumberError", 
                "Phone must be 10 digits and start with 97 or 98.");
            errors++;
        }

        // Gender
        if (!ValidationUtil.isValidGender(gender)) {
            request.setAttribute("genderError", "Please select Male or Female.");
            errors++;
        }

        // Password
        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("passwordError", 
                "Password must be ≥8 chars, include upper, lower, digit & special.");
            errors++;
        }
        if (!ValidationUtil.doPasswordsMatch(password, repassword)) {
            request.setAttribute("repasswordError", "Passwords do not match.");
            errors++;
        }

        // Date of birth
        LocalDate dob = null;
        try {
            dob = LocalDate.parse(dateOfBirthStr);
            if (!ValidationUtil.isAgeAtLeast16(dob)) {
                request.setAttribute("dateOfBirthError", 
                    "You must be at least 16 years old to register.");
                errors++;
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("dateOfBirthError", "Invalid date format.");
            errors++;
        }

        if (errors > 0) {
            request.setAttribute("error", "You have " + errors + " invalid field(s).");
            // preserve entered values
            request.setAttribute("name", name);
            request.setAttribute("nickname", nickname);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("gender", gender);
            request.setAttribute("dateOfBirth", dateOfBirthStr);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // All validations passed → hash password & register
        String passwordHash = PasswordUtil.encrypt(email, password);
        UserModel user = new UserModel(name, nickname, email, passwordHash,
                                       phoneNumber, gender, dob);
        Boolean added = registerService.addUser(user);

        if (added == null) {
            handleError(request, response, 
                "Our server is under maintenance. Please try again later!");
        } else if (added) {
            handleSuccess(request, response, 
                "Your account was created successfully!", "/WEB-INF/pages/login.jsp");
        } else {
            handleError(request, response, 
                "Could not register your account. Please try again later!");
        }
    }

    private void handleSuccess(HttpServletRequest request, HttpServletResponse response,
                               String message, String page)
            throws ServletException, IOException {
        request.setAttribute("success", message);
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }
}

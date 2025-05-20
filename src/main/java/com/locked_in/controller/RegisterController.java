package com.locked_in.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.locked_in.model.UserModel;
import com.locked_in.service.RegisterService;
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
 * RegisterController handles HTTP requests related to user registration.
 * 
 * It serves the registration page and processes registration form submissions
 * by validating user input and creating new user accounts.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RegisterService registerService = new RegisterService();

    /**
     * Initializes the RegisterController with an instance of RegisterService.
     * Sets up the service for handling user registration operations.
     */
    public RegisterController() {
        super();
    }

    /**
     * Handles GET requests to display the registration page.
     * 
     * Forwards the request to the registration JSP located in /WEB-INF/pages/register.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user registration.
     * 
     * Processes the registration form submission, validates user input,
     * creates a new user account, and redirects with appropriate messages.
     *
     * @param request  the HTTP request containing registration form data
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name           = request.getParameter("name");
        String nickname       = request.getParameter("nickname");
        String email          = request.getParameter("email");
        String password       = request.getParameter("password");
        String repassword     = request.getParameter("repassword");
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
            handleError(request, response, "You have " + errors + " invalid field(s).");
            return;
        }

        // Handle profile picture upload
        String profilePicturePath = "resources/images/system/userpfp.png"; // Default path
        Part filePart = request.getPart("profilePicture");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String savePath = getServletContext().getRealPath("/") + "resources/images/system/";
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            
            // Save the file
            filePart.write(savePath + fileName);
            profilePicturePath = "resources/images/system/" + fileName;
        }

        // All validations passed → hash password & register
        String passwordHash = PasswordUtil.encrypt(email, password);
        UserModel user = new UserModel(name, nickname, email, passwordHash, dob);
        user.setProfilePicture(profilePicturePath);
        String addedStatus = registerService.addUser(user);

        if (addedStatus != null) {
            handleError(request, response, addedStatus);
        } else {
            handleSuccess(request, response, "Your account was created successfully!", "/WEB-INF/pages/login.jsp");
        }
    }

    /**
     * Handles successful registration by forwarding to the specified page with a success message.
     * 
     * Sets the success message in the request attributes and forwards to the specified
     * JSP page, typically the login page after successful registration.
     *
     * @param request  the HTTP request to set attributes and forward
     * @param response the HTTP response for forwarding
     * @param message  the success message to display
     * @param page     the JSP page to forward to
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    private void handleSuccess(HttpServletRequest request, HttpServletResponse response,
                               String message, String page)
            throws ServletException, IOException {
        request.setAttribute("success", message);
        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * Handles registration errors by preserving form data and displaying error messages.
     * 
     * Preserves all form input values in the request attributes and forwards back to
     * the registration page with the error message. This allows users to correct
     * their input without losing previously entered data.
     *
     * @param request  the HTTP request containing form data and for setting attributes
     * @param response the HTTP response for forwarding
     * @param message  the error message to display
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
    	request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("nickname", request.getParameter("nickname"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("password", request.getParameter("password"));
        request.setAttribute("repassword", request.getParameter("repassword"));
        request.setAttribute("dateOfBirth", request.getParameter("dateOfBirth"));
        
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }
}

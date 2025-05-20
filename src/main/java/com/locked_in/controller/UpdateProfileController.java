package com.locked_in.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.locked_in.model.UserModel;
import com.locked_in.service.UserService;
import com.locked_in.util.PasswordUtil;
import com.locked_in.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * UpdateProfileController handles HTTP requests for user profile updates.
 * 
 * It processes requests to modify user profile information, including
 * personal details and profile picture, with proper validation.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updateProfile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final UserService userService;
    
    /**
     * Initializes the UpdateProfileController with an instance of UserService.
     * Sets up the service for handling user profile update operations.
     */
    public UpdateProfileController() {
        this.userService = new UserService();
    }
    
    /**
     * Handles GET requests to display the update profile form.
     * 
     * Retrieves user details and forwards to the update profile JSP.
     * Validates user authentication before displaying the form.
     *
     * @param request  the HTTP request containing user session information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the current user's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        System.out.println("UpdateProfileController - doGet - User email from session: " + email);
        
        if (email == null) {
            // If not logged in, redirect to login page
            System.out.println("UpdateProfileController - No email in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Fetch user details from the database
        UserModel userDetails = userService.getUserByEmail(email);
        
        if (userDetails == null) {
            // Something went wrong with fetching user details
            System.out.println("UpdateProfileController - Failed to retrieve user details for email: " + email);
            request.setAttribute("error", "Could not retrieve your profile information. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/updateProfile.jsp").forward(request, response);
            return;
        }
        
        System.out.println("UpdateProfileController - Retrieved user details: Name=" + userDetails.getName() + 
                          ", Nickname=" + userDetails.getNickname() + ", Email=" + userDetails.getEmail());
        
        // Set user details in the request
        request.setAttribute("userDetails", userDetails);
        
        // Forward to the update profile page
        request.getRequestDispatcher("/WEB-INF/pages/updateProfile.jsp").forward(request, response);
    }
    
    /**
     * Handles POST requests for profile updates.
     * 
     * Validates user authentication and processes profile information updates.
     * Updates user details in the database and redirects with appropriate messages.
     *
     * @param request  the HTTP request containing updated profile information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the current user's email from session
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        System.out.println("UpdateProfileController - doPost - User email from session: " + email);
        
        if (email == null) {
            // If not logged in, redirect to login page
            System.out.println("UpdateProfileController - No email in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get form parameters
        String name = request.getParameter("name");
        String nickname = request.getParameter("nickname");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        System.out.println("UpdateProfileController - Form data received: Name=" + name + 
                          ", Nickname=" + nickname + 
                          ", New password provided=" + (newPassword != null && !newPassword.isEmpty()));
        
        // Fetch current user from the database
        UserModel currentUser = userService.getUserByEmail(email);
        
        if (currentUser == null) {
            System.out.println("UpdateProfileController - Failed to retrieve current user for email: " + email);
            handleUpdateError(request, response, "User information not found.");
            return;
        }
        
        // Verify current password
        boolean passwordVerified = verifyPassword(currentUser, currentPassword);
        System.out.println("UpdateProfileController - Current password verified: " + passwordVerified);
        
        if (!passwordVerified) {
            handleUpdateError(request, response, "Current password is incorrect.");
            return;
        }
        
        // Check if we need to update the password
        String passwordToSave = currentUser.getPassword(); // Keep current by default
        
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            // Validate that new password and confirm password match
            if (!newPassword.equals(confirmPassword)) {
                handleUpdateError(request, response, "New password and confirmation do not match.");
                return;
            }
            
            // Encrypt the new password
            passwordToSave = PasswordUtil.encrypt(email, newPassword);
            if (passwordToSave == null) {
                handleUpdateError(request, response, "Password encryption failed. Please try again.");
                return;
            }
        }
        
        // Update the user model
        currentUser.setName(name);
        currentUser.setNickname(nickname);
        currentUser.setPassword(passwordToSave);
        
        // Save changes to the database
        boolean updateSuccess = userService.updateUser(currentUser);
        
        if (!updateSuccess) {
            handleUpdateError(request, response, "Failed to update your profile. Please try again later.");
            return;
        }
        
        // Handle profile picture upload
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
            
            // Set the profile picture path in the user model and database
            String relativePath = "resources/images/system/" + fileName;
            
            currentUser.setProfilePicture(relativePath);
            
            // Update the database with the new profile picture path
            if (!userService.updateUser(currentUser)) {
                handleUpdateError(request, response, "Failed to update profile picture in database.");
                return;
            }
            
            // Set the profile picture path in session
            
            // Set the profile picture path in session
            SessionUtil.setAttribute(request, "profilePicture", relativePath);
        }
        
        // Update session name attribute
        SessionUtil.setAttribute(request, "name", name);
        
        // Set success message and redirect to profile
        request.setAttribute("success", "Your profile has been successfully updated.");
        request.setAttribute("userDetails", currentUser);
        request.getRequestDispatcher("/WEB-INF/pages/updateProfile.jsp").forward(request, response);
    }
    
    /**
     * Verifies if the provided password matches the user's stored password.
     * 
     * Attempts multiple password verification methods:
     * 1. Direct comparison with stored password
     * 2. Decryption of stored password and comparison
     * 3. Encryption of provided password and comparison with stored
     *
     * @param user     the user model containing stored password information
     * @param password the password to verify
     * @return true if the password matches, false otherwise
     */
    private boolean verifyPassword(UserModel user, String password) {
        // Direct comparison for testing
        if (user.getPassword().equals(password)) {
            return true;
        }
        
        // Try to decrypt the stored password
        String decryptedPassword = PasswordUtil.decrypt(user.getPassword(), user.getEmail());
        if (decryptedPassword != null && decryptedPassword.equals(password)) {
            return true;
        }
        
        // Try to encrypt the provided password and compare with stored
        String encryptedPassword = PasswordUtil.encrypt(user.getEmail(), password);
        return user.getPassword().equals(encryptedPassword);
    }
    
    /**
     * Handles profile update errors by preserving user data and displaying error messages.
     * 
     * Retrieves the current user's details and forwards back to the update profile page
     * with the error message. This allows users to correct their input without losing
     * previously entered data.
     *
     * @param request       the HTTP request containing user session information
     * @param response      the HTTP response for forwarding
     * @param errorMessage  the error message to display
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    private void handleUpdateError(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws ServletException, IOException {
        UserModel userDetails = userService.getUserByEmail((String) SessionUtil.getAttribute(request, "email"));
        request.setAttribute("userDetails", userDetails);
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/updateProfile.jsp").forward(request, response);
    }
} 
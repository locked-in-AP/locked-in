package com.locked_in.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.locked_in.model.UserModel;
import com.locked_in.service.UserService;
import com.locked_in.util.SessionUtil;

/**
 * Servlet implementation class updateAdmin
 */
@WebServlet("/updateAdminProfile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UpdateAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private static final String UPLOAD_DIRECTORY = "resources/images/profile";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAdminController() {
        super();
        userService = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) SessionUtil.getAttribute(request, "email");
		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		UserModel userDetails = userService.getUserByEmail(email);
		if (userDetails != null) {
			request.setAttribute("userDetails", userDetails);
			request.getRequestDispatcher("/WEB-INF/pages/updateAdminProfile.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "User details not found");
			response.sendRedirect(request.getContextPath() + "/adminProfile");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) SessionUtil.getAttribute(request, "email");
		if (email == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		System.out.println("UpdateAdminController - Processing update for email: " + email);

		// Get form data
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String newEmail = request.getParameter("email");
		String dateOfBirth = request.getParameter("dateOfBirth");

		System.out.println("UpdateAdminController - Form data received: " +
						 "name=" + name + 
						 ", nickname=" + nickname + 
						 ", newEmail=" + newEmail + 
						 ", dateOfBirth=" + dateOfBirth);

		// Get current user details
		UserModel currentUser = userService.getUserByEmail(email);
		if (currentUser == null) {
			System.out.println("UpdateAdminController - User not found for email: " + email);
			SessionUtil.setAttribute(request, "error", "User not found");
			response.sendRedirect(request.getContextPath() + "/adminProfile");
			return;
		}

		System.out.println("UpdateAdminController - Current user role: " + currentUser.getRole());

		// Handle profile picture upload
		Part filePart = request.getPart("profilePicture");
		String profilePicturePath = currentUser.getProfilePicture(); // Keep existing path by default

		if (filePart != null && filePart.getSize() > 0) {
			String fileName = UUID.randomUUID().toString() + getFileExtension(filePart);
			String uploadPath = getUploadPath(request);
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			filePart.write(uploadPath + File.separator + fileName);
			profilePicturePath = UPLOAD_DIRECTORY + "/" + fileName;
			System.out.println("UpdateAdminController - New profile picture uploaded: " + profilePicturePath);
		}

		// Update user details
		currentUser.setName(name);
		currentUser.setNickname(nickname);
		currentUser.setEmail(newEmail);
		if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
			try {
				currentUser.setDateOfBirth(java.time.LocalDate.parse(dateOfBirth));
				System.out.println("UpdateAdminController - Date of birth set to: " + dateOfBirth);
			} catch (Exception e) {
				System.out.println("UpdateAdminController - Error parsing date: " + e.getMessage());
				SessionUtil.setAttribute(request, "error", "Invalid date format");
				response.sendRedirect(request.getContextPath() + "/adminProfile");
				return;
			}
		}
		currentUser.setProfilePicture(profilePicturePath);

		// Save to database using admin-specific update method
		boolean updated = userService.updateAdminDetails(currentUser);
		if (updated) {
			System.out.println("UpdateAdminController - Profile updated successfully");
			// Update session attributes
			SessionUtil.setAttribute(request, "name", name);
			SessionUtil.setAttribute(request, "profilePicture", profilePicturePath);
			SessionUtil.setAttribute(request, "success", "Profile updated successfully");
			response.sendRedirect(request.getContextPath() + "/adminProfile");
		} else {
			System.out.println("UpdateAdminController - Failed to update profile");
			SessionUtil.setAttribute(request, "error", "Failed to update profile. Please try again.");
			response.sendRedirect(request.getContextPath() + "/adminProfile");
		}
	}

	private String getUploadPath(HttpServletRequest request) {
		return request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
	}

	private String getFileExtension(Part part) {
		String submittedFileName = part.getSubmittedFileName();
		return submittedFileName.substring(submittedFileName.lastIndexOf("."));
	}
}

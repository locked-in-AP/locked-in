package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.locked_in.config.DbConfig;
import com.locked_in.model.UserModel;
import com.locked_in.util.PasswordUtil;

/**
 * Service class for handling user authentication operations.
 * 
 * This service class manages all aspects of user login including:
 * - Credential validation
 * - Password verification
 * - User session initialization
 * - Security checks
 * 
 * The class maintains database connections and collaborates with
 * PasswordUtil for secure password handling.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Creates a new LoginService instance.
	 * 
	 * Initializes the database connection and sets the connection error flag
	 * if the connection fails.
	 */
	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
			System.out.println("Database connection successful");
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
			System.out.println("Database connection failed: " + ex.getMessage());
		}
	}

	/**
	 * Authenticates a user's login attempt.
	 * 
	 * This method performs the following steps:
	 * 1. Retrieves user information from the database
	 * 2. Validates the provided password
	 * 3. Updates the user model with additional information if successful
	 * 
	 * @param userModel the UserModel object containing user credentials
	 * @return true if authentication is successful, false if credentials are invalid,
	 *         null if a database error occurs
	 */
	public Boolean loginUser(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		System.out.println("Attempting login for email: " + userModel.getEmail());
		String query = "SELECT user_id, email, password, role, name, profile_picture FROM users WHERE email = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getEmail());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				System.out.println("User found in database");
				return validatePassword(result, userModel);
			} else {
				System.out.println("No user found with email: " + userModel.getEmail());
			}
		} catch (SQLException e) {
			System.out.println("SQL Error during login: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return false;
	}

	/**
	 * Validates a user's password against the stored credentials.
	 * 
	 * This method attempts multiple password validation strategies:
	 * 1. Direct comparison (for testing)
	 * 2. Decryption of stored password
	 * 3. Encryption of provided password
	 * 
	 * If validation is successful, the user model is updated with:
	 * - User ID
	 * - Role
	 * - Name
	 * - Profile picture
	 * 
	 * @param result the ResultSet containing the stored user data
	 * @param userModel the UserModel object containing the provided credentials
	 * @return true if the password is valid, false otherwise
	 * @throws SQLException if there is an error accessing the ResultSet data
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String storedPassword = result.getString("password");
		String providedPassword = userModel.getPassword();
		Integer userId = result.getInt("user_id");
		String role = result.getString("role");
		String name = result.getString("name");
		String profilePicture = result.getString("profile_picture");
		
		System.out.println("Stored password hash: " + storedPassword);
		System.out.println("Provided password: " + providedPassword);
		
		// First try direct comparison for testing
		if (storedPassword.equals(providedPassword)) {
			System.out.println("Password matched directly");
			userModel.setUserId(userId);
			userModel.setRole(role);
			userModel.setName(name);
			userModel.setProfilePicture(profilePicture);
			return true;
		}
		
		// Try to decrypt the stored password hash
		String decryptedStoredPassword = PasswordUtil.decrypt(storedPassword, userModel.getEmail());
		System.out.println("Decrypted stored password: " + (decryptedStoredPassword != null ? "success" : "failed"));
		
		if (decryptedStoredPassword != null && decryptedStoredPassword.equals(providedPassword)) {
			System.out.println("Password matched after decryption");
			userModel.setUserId(userId);
			userModel.setRole(role);
			userModel.setName(name);
			userModel.setProfilePicture(profilePicture);
			return true;
		}
		
		// Try encrypting the provided password
		String encryptedProvidedPassword = PasswordUtil.encrypt(userModel.getEmail(), providedPassword);
		System.out.println("Encrypted provided password: " + (encryptedProvidedPassword != null ? "success" : "failed"));
		
		if (storedPassword.equals(encryptedProvidedPassword)) {
			System.out.println("Password matched after encryption");
			userModel.setUserId(userId);
			userModel.setRole(role);
			userModel.setName(name);
			userModel.setProfilePicture(profilePicture);
			return true;
		}
		
		System.out.println("Password validation failed");
		return false;
	}
}
package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.locked_in.config.DbConfig;
import com.locked_in.model.UserModel;
import com.locked_in.util.PasswordUtil;

/**
 * Service class for handling login operations. 
 * Connects to the database, verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. 
	 * Sets the connection error flag if the connection fails.
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
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public Boolean loginUser(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		System.out.println("Attempting login for email: " + userModel.getEmail());
		String query = "SELECT email, password, role, name, profile_picture FROM users WHERE email = ?";
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
	 * Validates the password retrieved from the database.
	 *
	 * @param result    the ResultSet containing the email and password from
	 *                  the database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String storedPassword = result.getString("password");
		String providedPassword = userModel.getPassword();
		String role = result.getString("role");
		String name = result.getString("name");
		String profilePicture = result.getString("profile_picture");
		
		System.out.println("Stored password hash: " + storedPassword);
		System.out.println("Provided password: " + providedPassword);
		
		// First try direct comparison for testing
		if (storedPassword.equals(providedPassword)) {
			System.out.println("Password matched directly");
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
			userModel.setRole(role);
			userModel.setName(name);
			userModel.setProfilePicture(profilePicture);
			return true;
		}
		
		System.out.println("Password validation failed");
		return false;
	}
}
package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.locked_in.config.DbConfig;
import com.locked_in.model.UserModel;

/**
 * RegisterService handles the registration of new users. It manages database
 * interactions for user registration.
 */
public class RegisterService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
			System.out.println("RegisterService - Database connection established successfully");
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("RegisterService - Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel the user details to be registered
	 * @return String error message if registration fails, null if successful
	 */
	public String addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("RegisterService - Database connection is not available.");
			return "Database connection error. Please try again later.";
		}
		
		System.out.println("RegisterService - Attempting to register user: " + userModel.getEmail());
		System.out.println("RegisterService - User details: Name=" + userModel.getName() + 
						 ", Nickname=" + userModel.getNickname() + 
						 ", DOB=" + userModel.getDateOfBirth() +
						 ", Profile Picture=" + userModel.getProfilePicture());
		
		String insertQuery = """
INSERT INTO users (
	name,
	nickname,
	email,
	password,	
	date_of_birth,
	profile_picture
)
VALUES (?, ?, ?, ?, ?, ?)
""";
		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
			System.out.println("RegisterService - Prepared statement created successfully");

			insertStmt.setString(1, userModel.getName());
			insertStmt.setString(2, userModel.getNickname());
			insertStmt.setString(3, userModel.getEmail());
			insertStmt.setString(4, userModel.getPassword());
			insertStmt.setDate(5, java.sql.Date.valueOf(userModel.getDateOfBirth()));
			insertStmt.setString(6, userModel.getProfilePicture());

			System.out.println("RegisterService - Executing insert statement...");
			int rowsAffected = insertStmt.executeUpdate();
			System.out.println("RegisterService - Insert result: " + rowsAffected + " rows affected");

			if (rowsAffected > 0) {
				System.out.println("RegisterService - User registration successful");
				return null;
			} else {
				System.out.println("RegisterService - No rows were affected by the insert");
				return "Failed to register user. Please try again.";
			}
		} catch (SQLException e) {
			System.err.println("RegisterService - SQL Error during registration: " + e.getMessage());
			e.printStackTrace();
			return "Database error: " + e.getMessage();
		}
	}
}

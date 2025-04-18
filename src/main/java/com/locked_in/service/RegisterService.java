package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel the user details to be registered
	 * @return Boolean indicating the success of the operation
	 */
	public Boolean addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		String insertQuery = """
INSERT INTO User (
	first_name,
	middle_name,
	last_name,
	email,
	password,
	contact_num,
	date_joined,
	image_url,
	cart_size,
	role
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
""";
		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
			// Insert user details
			insertStmt.setString(1, userModel.getFirstName());
			insertStmt.setString(2, userModel.getMiddleName());
			insertStmt.setString(3, userModel.getLastName());
			insertStmt.setString(4, userModel.getEmail());
			insertStmt.setString(5, userModel.getPassword());
			insertStmt.setString(6, userModel.getContactNum());
			insertStmt.setDate  (7, java.sql.Date.valueOf(LocalDate.now()));
			insertStmt.setString(8, userModel.getImageUrl());
			insertStmt.setInt   (9, 0);
			insertStmt.setString(10, userModel.getRole());
			// Execute statement
			return insertStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

package com.locked_in.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.locked_in.config.DbConfig;
import com.locked_in.model.UserModel;

/**
 * RegisterService handles the registration of new users. It manages database
 * interactions for user registration.
 */
public class RegisterService {

	private Connection dbConn;
	private String final DEFAULT_CART_SIZE = 1

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

		String roleQuery = "SELECT role_id FROM Role WHERE role = ?";
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
	role_id
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
""";

		try (PreparedStatement roleStmt = dbConn.prepareStatement(roleQuery);
				PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

			// Fetch role ID
			roleStmt.setString(1, userModel.getRole().getName());
			ResultSet result = roleStmt.executeQuery();
			int roleId = result.next() ? result.getInt("role_id") : 1;

			// Insert user details
			insertStmt.setString(1, userModel.getFirstName());
			insertStmt.setString(2, userModel.getMiddleName());
			insertStmt.setString(3, userModel.getLastName());
			insertStmt.setString(4, userModel.getEmail());
			insertStmt.setString(5, userModel.getPassword());
			insertStmt.setString(6, userModel.getContactNum());
			insertStmt.setDate(7, Date.valueOf(userModel.getDateJoined()));
			insertStmt.setString(8, userModel.getImageUrl());
			insertStmt.setString(9, this.DEFAULT_CART_SIZE);
			insertStmt.setInt(10, roleId);

			return insertStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

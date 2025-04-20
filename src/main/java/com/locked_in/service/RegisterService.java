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
	public String addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		String insertQuery = """
INSERT INTO users (
	name,
	nickname,
	email,
	password,
	date_of_birth
)
VALUES (?, ?, ?, ?, ?)
""";
		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

			insertStmt.setString(1, userModel.getName());
			insertStmt.setString(2, userModel.getNickname());
			insertStmt.setString(3, userModel.getEmail());
			insertStmt.setString(4, userModel.getPassword());
			insertStmt.setDate  (5, java.sql.Date.valueOf(userModel.getDateOfBirth()));

			if (insertStmt.executeUpdate() > 0) {
				return null;
			};
		} catch (SQLException e) {
			return e.getMessage();
		}
		return null;
	}
}

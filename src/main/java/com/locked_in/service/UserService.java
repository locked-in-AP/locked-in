package com.locked_in.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.locked_in.config.DbConfig;
import com.locked_in.model.UserModel;

/**
 * Service class for handling user-related operations.
 * Provides methods to retrieve, update, and manage user data.
 */
public class UserService {
    
    private Connection dbConn;
    private boolean isConnectionError = false;
    
    /**
     * Constructor initializes the database connection.
     * Sets the connection error flag if the connection fails.
     */
    public UserService() {
        try {
            dbConn = DbConfig.getDbConnection();
            System.out.println("Database connection successful in UserService");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
            System.out.println("Database connection failed in UserService: " + ex.getMessage());
        }
    }
    
    /**
     * Retrieves a user by their email address
     * 
     * @param email the email of the user to retrieve
     * @return UserModel containing the user's details, or null if not found
     */
    public UserModel getUserByEmail(String email) {
        if (isConnectionError) {
            System.out.println("UserService - Connection Error!");
            return null;
        }
        
        System.out.println("UserService - Looking up user with email: " + email);
        
        String query = "SELECT user_id, name, nickname, email, password, role, date_of_birth, profile_picture FROM users WHERE email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                Integer userId = result.getInt("user_id");
                String name = result.getString("name");
                String nickname = result.getString("nickname");
                String password = result.getString("password");
                String role = result.getString("role");
                String profilePicture = result.getString("profile_picture");
                LocalDate dateOfBirth = null;
                if (result.getDate("date_of_birth") != null) {
                    dateOfBirth = result.getDate("date_of_birth").toLocalDate();
                }
                
                System.out.println("UserService - Found user: ID=" + userId + ", Name=" + name + 
                                 ", Nickname=" + nickname + ", Role=" + role);
                
                // Create user model
                UserModel user = new UserModel(userId, name, nickname, email, password, role, dateOfBirth, null, null);
                user.setProfilePicture(profilePicture);
                return user;
            } else {
                System.out.println("UserService - No user found with email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error retrieving user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Updates a user's profile information in the database
     * 
     * @param user the UserModel containing updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUser(UserModel user) {
        if (isConnectionError) {
            System.out.println("UserService - Connection Error during update!");
            return false;
        }
        
        System.out.println("UserService - Updating user: Email=" + user.getEmail() + 
                         ", Name=" + user.getName() + ", Nickname=" + user.getNickname());
        
        String query = "UPDATE users SET name = ?, nickname = ?, password = ?, profile_picture = ? WHERE email = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getNickname());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getProfilePicture());
            stmt.setString(5, user.getEmail());
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("UserService - Update result: " + rowsAffected + " rows affected");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves all users from the database
     * @return List of UserModel objects
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        if (isConnectionError) {
            System.out.println("UserService - Connection Error!");
            return users;
        }
        String query = "SELECT user_id, name, nickname, email, password, role, date_of_birth, joined_at, cart_size, profile_picture FROM users";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Integer userId = result.getInt("user_id");
                String name = result.getString("name");
                String nickname = result.getString("nickname");
                String email = result.getString("email");
                String password = result.getString("password");
                String role = result.getString("role");
                LocalDate dateOfBirth = null;
                if (result.getDate("date_of_birth") != null) {
                    dateOfBirth = result.getDate("date_of_birth").toLocalDate();
                }
                LocalDateTime joinedAt = null;
                if (result.getTimestamp("joined_at") != null) {
                    joinedAt = result.getTimestamp("joined_at").toLocalDateTime();
                }
                Integer cartSize = result.getInt("cart_size");
                String profilePicture = result.getString("profile_picture");
                UserModel user = new UserModel(userId, name, nickname, email, password, role, dateOfBirth, joinedAt, cartSize);
                user.setProfilePicture(profilePicture);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error retrieving all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Deletes a user from the database by user ID
     * @param userId the ID of the user to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        if (isConnectionError) {
            System.out.println("UserService - Connection Error!");
            return false;
        }
        String query = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the total number of users in the database.
     * 
     * This method is useful for tracking user growth and can be used
     * in analytics or dashboard displays.
     * 
     * @return the number of new users  
     * @throws SQLException if there is an error accessing the database
     */
    public int getTotalCustomersLast30Days() {
        if (isConnectionError) {
            System.out.println("UserService - Connection Error!");
            return 0;
        }
        
        String query = "SELECT COUNT(*) as total_users FROM users";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("total_users");
            }
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error getting total users: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Updates an admin's profile information in the database
     * 
     * @param user the UserModel containing updated admin information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateAdminDetails(UserModel user) {
        if (isConnectionError) {
            System.out.println("UserService - Connection Error during admin update!");
            return false;
        }
        
        System.out.println("UserService - Updating admin: Email=" + user.getEmail() + 
                         ", Name=" + user.getName() + 
                         ", Nickname=" + user.getNickname() + 
                         ", DateOfBirth=" + user.getDateOfBirth() + 
                         ", ProfilePicture=" + user.getProfilePicture());
        
        String query = "UPDATE users SET name = ?, nickname = ?, email = ?, date_of_birth = ?, profile_picture = ? WHERE email = ? AND role = 'admin'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getNickname());
            stmt.setString(3, user.getEmail());
            
            // Handle date of birth
            if (user.getDateOfBirth() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
                System.out.println("Setting date of birth: " + user.getDateOfBirth());
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
                System.out.println("Setting date of birth to NULL");
            }
            
            stmt.setString(5, user.getProfilePicture());
            stmt.setString(6, user.getEmail());
            
            System.out.println("Executing SQL: " + query);
            System.out.println("Parameters: name=" + user.getName() + 
                             ", nickname=" + user.getNickname() + 
                             ", email=" + user.getEmail() + 
                             ", profilePicture=" + user.getProfilePicture());
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("UserService - Admin update result: " + rowsAffected + " rows affected");
            
            if (rowsAffected == 0) {
                System.out.println("No rows were updated. This might mean the user is not an admin or the email doesn't exist.");
                // Verify if the user exists and is an admin
                String verifyQuery = "SELECT role FROM users WHERE email = ?";
                try (PreparedStatement verifyStmt = dbConn.prepareStatement(verifyQuery)) {
                    verifyStmt.setString(1, user.getEmail());
                    ResultSet rs = verifyStmt.executeQuery();
                    if (rs.next()) {
                        String role = rs.getString("role");
                        System.out.println("User exists with role: " + role);
                    } else {
                        System.out.println("User does not exist with email: " + user.getEmail());
                    }
                }
            }
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("UserService - SQL Error updating admin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 
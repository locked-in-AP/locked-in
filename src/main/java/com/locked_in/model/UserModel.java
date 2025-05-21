package com.locked_in.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * UserModel represents a user in the system with personal, contact, account,
 * and cart details.
 * 
 * This model class encapsulates all user-related data including:
 * - Personal information (name, nickname, date of birth)
 * - Account details (email, password, role)
 * - Profile settings (profile picture)
 * - Shopping information (cart size)
 * - Account metadata (user ID, join date)
 * 
 * The class provides constructors for different user creation scenarios
 * and maintains default values for new users.
 * 
 */
public class UserModel {

	private Integer userId;
	private String name;
	private String nickname;
	private String email;
	private String password;
	private String role;
	private LocalDate dateOfBirth;
	private LocalDateTime joinedAt;
	private Integer cartSize;
	private String profilePicture;
	
	/**
	 * Creates a complete user model with all fields specified.
	 * 
	 * Used when retrieving a user from the database or creating a user
	 * with all information available. Sets a default profile picture.
	 *
	 * @param userId      the unique identifier for the user
	 * @param name        the user's full name
	 * @param nickname    the user's nickname or display name
	 * @param email       the user's email address
	 * @param password    the user's encrypted password
	 * @param role        the user's role (customer/admin)
	 * @param dateOfBirth the user's date of birth
	 * @param joinedAt    the date and time the user joined
	 * @param cartSize    the number of items in the user's cart
	 */
	public UserModel(Integer userId, String name, String nickname, String email, String password, String role,
			LocalDate dateOfBirth, LocalDateTime joinedAt, Integer cartSize) {
		super();
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.dateOfBirth = dateOfBirth;
		this.joinedAt = joinedAt;
		this.cartSize = cartSize;
		this.profilePicture = "resources/images/system/userpfp.png";
	}

	/**
	 * Creates a new user model for registration.
	 * 
	 * Used when registering a new user. Sets default values for:
	 * - role (customer)
	 * - join date (current time)
	 * - cart size (0)
	 * - profile picture (default image)
	 *
	 * @param name        the user's full name
	 * @param nickname    the user's nickname or display name
	 * @param email       the user's email address
	 * @param password    the user's encrypted password
	 * @param dateOfBirth the user's date of birth
	 */
	public UserModel(String name, String nickname, String email, String password, LocalDate dateOfBirth) {
		super();
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.role = "customer";
		this.joinedAt = LocalDateTime.now();
		this.cartSize = 0;
		this.profilePicture = "resources/images/system/userpfp.png";
	}

	/**
	 * Creates a minimal user model for login.
	 * 
	 * Used when authenticating a user. Only requires email and password.
	 *
	 * @param email    the user's email address
	 * @param password the user's password
	 */
	public UserModel(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets the user's unique identifier.
	 *
	 * @return the user ID
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user's unique identifier.
	 *
	 * @param userId the user ID to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user's full name.
	 *
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user's full name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user's nickname or display name.
	 *
	 * @return the user's nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the user's nickname or display name.
	 *
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Gets the user's email address.
	 *
	 * @return the user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email address.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's encrypted password.
	 *
	 * @return the user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's encrypted password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user's role in the system.
	 *
	 * @return the user's role (customer/admin)
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the user's role in the system.
	 *
	 * @param role the role to set (customer/admin)
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the user's date of birth.
	 *
	 * @return the user's date of birth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the user's date of birth.
	 *
	 * @param dateOfBirth the date of birth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the date and time the user joined.
	 *
	 * @return the join date and time
	 */
	public LocalDateTime getJoinedAt() {
		return joinedAt;
	}

	/**
	 * Sets the date and time the user joined.
	 *
	 * @param joinedAt the join date and time to set
	 */
	public void setJoinedAt(LocalDateTime joinedAt) {
		this.joinedAt = joinedAt;
	}

	/**
	 * Gets the number of items in the user's cart.
	 *
	 * @return the cart size
	 */
	public Integer getCartSize() {
		return cartSize;
	}

	/**
	 * Sets the number of items in the user's cart.
	 *
	 * @param cartSize the cart size to set
	 */
	public void setCartSize(Integer cartSize) {
		this.cartSize = cartSize;
	}

	/**
	 * Gets the path to the user's profile picture.
	 *
	 * @return the profile picture path
	 */
	public String getProfilePicture() {
		return profilePicture;
	}

	/**
	 * Sets the path to the user's profile picture.
	 *
	 * @param profilePicture the profile picture path to set
	 */
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	

}
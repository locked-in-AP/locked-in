package com.locked_in.model;

import java.time.LocalDate;

/**
 * UserModel represents a user in the system with personal, contact, account, and cart details.
 */
public class UserModel {

	private Integer userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String contactNum;
	private LocalDate dateJoined;
	private String imageUrl;
	private Integer cartSize;
	private String role;

	/**
	 * Default constructor for creating an empty UserModel instance.
	 */
	public UserModel() {
		// No-args constructor
	}

	/**
	 * Constructs a fully-initialized UserModel including user ID.
	 * 
	 * @param userId     unique identifier for the user
	 * @param firstName  user's first name
	 * @param middleName user's middle name
	 * @param lastName   user's last name
	 * @param email      user's email address
	 * @param password   user's password
	 * @param contactNum user's contact number
	 * @param dateJoined date the user joined the system
	 * @param imageUrl   URL of user's profile image
	 * @param cartSize   number of items in user's cart
	 * @param role       user's role in the system (e.g., admin, user)
	 */
	public UserModel(Integer userId, String firstName, String middleName, String lastName, String email,
					 String password, String contactNum, LocalDate dateJoined, String imageUrl,
					 Integer cartSize, String role) {
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contactNum = contactNum;
		this.dateJoined = dateJoined;
		this.imageUrl = imageUrl;
		this.cartSize = cartSize;
		this.role = role;
	}

	/**
	 * Constructs a UserModel without a user ID (useful for registration).
	 * 
	 * @param firstName  user's first name
	 * @param middleName user's middle name
	 * @param lastName   user's last name
	 * @param email      user's email address
	 * @param password   user's password
	 * @param contactNum user's contact number
	 * @param dateJoined date the user joined the system
	 * @param imageUrl   URL of user's profile image
	 * @param cartSize   number of items in user's cart
	 * @param role       user's role in the system (e.g., admin, user)
	 */
	public UserModel(String firstName, String middleName, String lastName, String email, String password,
					 String contactNum, LocalDate dateJoined, String imageUrl, Integer cartSize, String role) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contactNum = contactNum;
		this.dateJoined = dateJoined;
		this.imageUrl = imageUrl;
		this.cartSize = cartSize;
		this.role = role;
	}

	/**
	 * Constructs a UserModel with only email and password (useful for login).
	 * 
	 * @param email      user's email address
	 * @param password   user's password
	 */
	public UserModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/** @return user ID */
	public Integer getUserId() {
		return userId;
	}

	/** @param userId sets the user ID */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** @return first name */
	public String getFirstName() {
		return firstName;
	}

	/** @param firstName sets the user's first name */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/** @return middle name */
	public String getMiddleName() {
		return middleName;
	}

	/** @param middleName sets the user's middle name */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/** @return last name */
	public String getLastName() {
		return lastName;
	}

	/** @param lastName sets the user's last name */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/** @return email address */
	public String getEmail() {
		return email;
	}

	/** @param email sets the user's email address */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return password */
	public String getPassword() {
		return password;
	}

	/** @param password sets the user's password */
	public void setPassword(String password) {
		this.password = password;
	}

	/** @return contact number */
	public String getContactNum() {
		return contactNum;
	}

	/** @param contactNum sets the user's contact number */
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	/** @return the date the user joined */
	public LocalDate getDateJoined() {
		return dateJoined;
	}

	/** @param dateJoined sets the date the user joined */
	public void setDateJoined(LocalDate dateJoined) {
		this.dateJoined = dateJoined;
	}

	/** @return profile image URL */
	public String getImageUrl() {
		return imageUrl;
	}

	/** @param imageUrl sets the user's profile image URL */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/** @return number of items in user's cart */
	public Integer getCartSize() {
		return cartSize;
	}

	/** @param cartSize sets the number of items in user's cart */
	public void setCartSize(Integer cartSize) {
		this.cartSize = cartSize;
	}

	/** @return user's role in the system */
	public String getRole() {
		return role;
	}

	/** @param role sets the user's system role */
	public void setRole(String role) {
		this.role = role;
	}

}

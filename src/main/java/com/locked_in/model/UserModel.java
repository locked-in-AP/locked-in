/**
 * 
 */
package com.locked_in.model;

import java.time.LocalDate;

/**
 * 
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
	 * 
	 */
	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param contactNum
	 * @param dateJoined
	 * @param imageUrl
	 * @param cartSize
	 * @param role
	 */
	public UserModel(Integer userId, String firstName, String middleName, String lastName, String email,
			String password, String contactNum, LocalDate dateJoined, String imageUrl, Integer cartSize,
			String role) {
		super();
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
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the contactNum
	 */
	public String getContactNum() {
		return contactNum;
	}

	/**
	 * @param contactNum the contactNum to set
	 */
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	/**
	 * @return the dateJoined
	 */
	public LocalDate getDateJoined() {
		return dateJoined;
	}

	/**
	 * @param dateJoined the dateJoined to set
	 */
	public void setDateJoined(LocalDate dateJoined) {
		this.dateJoined = dateJoined;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the cartSize
	 */
	public Integer getCartSize() {
		return cartSize;
	}

	/**
	 * @param cartSize the cartSize to set
	 */
	public void setCartSize(Integer cartSize) {
		this.cartSize = cartSize;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}

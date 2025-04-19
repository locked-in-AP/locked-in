package com.locked_in.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * UserModel represents a user in the system with personal, contact, account,
 * and cart details.
 */
public class UserModel {

	private Integer userId;
	private String name;
	private String nickname;
	private String email;
	private String passwordHash;
	private String phoneNumber;
	private String role;
	private String gender;
	private LocalDate dateOfBirth;
	private LocalDateTime joinedAt;
	private Integer cartSize;
	
	/**
	 * Full constructor with all fields
	 */
	public UserModel(Integer userId, String name, String nickname, String email, String passwordHash,
			String phoneNumber, String role, String gender, LocalDate dateOfBirth, LocalDateTime joinedAt,
			Integer cartSize) {
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.passwordHash = passwordHash;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.joinedAt = joinedAt;
		this.cartSize = cartSize;
	}

	/**
	 * Constructor for user registration
	 */
	public UserModel(String name, String nickname, String email, String passwordHash, String phoneNumber, String gender,
			LocalDate dateOfBirth) {
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.passwordHash = passwordHash;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = "customer"; // Default role
	}
	
	/**
	 * Constructor for login
	 */
	public UserModel(String email, String password) {
		this.email = email;
		this.passwordHash = password;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the joinedAt
	 */
	public LocalDateTime getJoinedAt() {
		return joinedAt;
	}

	/**
	 * @param joinedAt the joinedAt to set
	 */
	public void setJoinedAt(LocalDateTime joinedAt) {
		this.joinedAt = joinedAt;
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

}

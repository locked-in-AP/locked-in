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
	private String password;
	private String role;
	private LocalDate dateOfBirth;
	private LocalDateTime joinedAt;
	private Integer cartSize;
	private String profilePicture;
	
	/**
	 * @param userId
	 * @param name
	 * @param nickname
	 * @param email
	 * @param password
	 * @param role
	 * @param dateOfBirth
	 * @param joinedAt
	 * @param cartSize
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
	 * @param name
	 * @param nickname
	 * @param email
	 * @param password
	 * @param dateOfBirth
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
	 * @param email
	 * @param password
	 */
	public UserModel(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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

	/**
	 * @return the profilePicture
	 */
	public String getProfilePicture() {
		return profilePicture;
	}

	/**
	 * @param profilePicture the profilePicture to set
	 */
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	

}
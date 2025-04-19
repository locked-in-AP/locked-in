package com.locked_in.model;

import java.time.LocalDate;

/**
 * UserModel represents a user in the system with personal, contact, account, and cart details.
 */
public class UserModel {

	private Integer   id;
	private String    name;
	private String    email;
	private String    password;
	private String    phone;
	private LocalDate dateJoined;
	private Integer   cartSize;
	private String    role;

}

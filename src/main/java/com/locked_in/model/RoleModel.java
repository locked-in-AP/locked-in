/**
 * 
 */
package com.locked_in.model;

/**
 * 
 */
public class RoleModel {
	
	private Integer roleId;
	private String role;

	/**
	 * 
	 */
	public RoleModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param roleId
	 * @param role
	 */
	public RoleModel(Integer roleId, String role) {
		super();
		this.roleId = roleId;
		this.role = role;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

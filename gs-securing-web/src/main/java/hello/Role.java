/**
 * 
 */
package hello;

import java.io.Serializable;

/**
 *  Class that represents an individual Roles (permissions) available to users of the system
 * 
 * @author robert.hinds
 * 
 */

public class Role implements Serializable {

	public static final long serialVersionUID = 42L;

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	private Long id;

	private String role;

}

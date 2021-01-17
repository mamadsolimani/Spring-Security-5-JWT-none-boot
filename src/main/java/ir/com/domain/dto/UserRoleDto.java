package ir.com.domain.dto;

import java.io.Serializable;
import java.util.List;

import ir.com.domain.enums.Permission;

public class UserRoleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	List<Permission> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}

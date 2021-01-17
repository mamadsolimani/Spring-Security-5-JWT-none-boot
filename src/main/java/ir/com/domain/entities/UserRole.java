package ir.com.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import ir.com.domain.dto.UserRoleDto;
import ir.com.domain.enums.Permission;

@Entity
@Table(name = "USER_ROLES6")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<Permission> permissions;

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
	
	public UserRoleDto toDtoLite() {
		UserRoleDto dto = new UserRoleDto();
		dto.setId(this.id);
		dto.setName(this.name);

		return dto;
	}

	public UserRoleDto toDto() {
		UserRoleDto dto = toDtoLite();

		if ( this.permissions != null ) {
			
			List<Permission> permissions = new ArrayList<>();
			for (Permission permission : this.permissions) {
				permissions.add(permission);
			}
			
			dto.setPermissions(permissions);
		}
		
		return dto;
	}

	public void fromDto(UserRoleDto dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		
		if ( dto.getPermissions() != null ) {
			this.permissions = new ArrayList<>();
			
			for (Permission permission : dto.getPermissions()) {
				permissions.add(permission);
			}
			
		}

	}

}

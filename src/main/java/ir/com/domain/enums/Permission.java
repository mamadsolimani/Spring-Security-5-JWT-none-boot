package ir.com.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
	
	CREATE_USER,
	EDIT_USER,
	REMOVE_USER,
	GET_USER,
	SEE_USERS,
	LOCK_USER,;

	@Override
	public String getAuthority() {
		return this.name();
	}
	
}

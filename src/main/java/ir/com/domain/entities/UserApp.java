package ir.com.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ir.com.domain.dto.UserAppDto;
import ir.com.domain.dto.UserRoleDto;

@Entity
@Table(name = "USER_6")
public class UserApp implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName;
	private String lastName;
	
	private String username;
	private String password;
	
	private String email;

	private Integer accountNonExpired;
	private Integer accountNonLocked;
	private Integer credentialsNonExpired;
	private Integer enabled;

	private String photo;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UserRole> userRoles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Integer getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Integer getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		for (UserRole userRole : this.userRoles) {
			grantedAuthorities.addAll(userRole.getPermissions());
		}
		
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		if (this.enabled == 0) {
			return false;
		} else {
			return true;
		}
	}

	public UserAppDto toDtoLite() {
		UserAppDto dto = new UserAppDto();
		dto.setId(this.id);
		dto.setFirstName(this.firstName);
		dto.setLastName(this.lastName);
		dto.setUsername(this.username);
		dto.setPassword(this.password);
		dto.setEmail(this.email);

		if (this.accountNonExpired == 0) {
			dto.setAccountNonExpired(false);
		} else {
			dto.setAccountNonExpired(true);
		}

		if (this.accountNonLocked == 0) {
			dto.setAccountNonLocked(false);
		} else {
			dto.setAccountNonLocked(true);
		}

		if (this.credentialsNonExpired == 0) {
			dto.setCredentialsNonExpired(false);
		} else {
			dto.setCredentialsNonExpired(true);
		}

		if (this.enabled == 0) {
			dto.setEnabled(false);
		} else {
			dto.setEnabled(true);
		}

		dto.setPhoto(this.photo);
		
		return dto;
	}

	public UserAppDto toDto() {
		UserAppDto dto = toDtoLite();

		if (this.userRoles != null) {

			List<UserRoleDto> userRoleDtos = new ArrayList<>();
			for (UserRole userRole : this.userRoles) {
				userRoleDtos.add(userRole.toDto());
			}

			dto.setUserRoles(userRoleDtos);

		}

		return dto;
	}

	public void fromDto(UserAppDto dto) {
		this.id = dto.getId();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.email = dto.getEmail();

		if (dto.getAccountNonExpired()) {
			this.accountNonExpired = 1;
		} else {
			this.accountNonExpired = 0;
		}

		if (dto.getAccountNonLocked()) {
			this.accountNonLocked = 1;
		} else {
			this.accountNonLocked = 0;
		}

		if (dto.getCredentialsNonExpired()) {
			this.credentialsNonExpired = 1;
		} else {
			this.credentialsNonExpired = 0;
		}

		if (dto.getEnabled()) {
			this.enabled = 1;
		} else {
			this.enabled = 0;
		}

		this.photo = dto.getPhoto();
		
		if (dto.getUserRoles() != null) {
			List<UserRole> userRoles = new ArrayList<>();

			for (UserRoleDto userRoleDto : dto.getUserRoles()) {
				UserRole userRole = new UserRole();
				userRole.fromDto(userRoleDto);
				
				userRoles.add(userRole);
			}

			this.setUserRoles(userRoles);
		}

	}

}



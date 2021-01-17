package ir.com.domain.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ir.com.domain.entities.UserApp;

public interface UserService extends UserDetailsService {

	public UserApp create(UserApp userApp) throws Exception;

	public UserApp edit(UserApp userApp) throws Exception;

	public boolean remove(Long id) throws Exception;

	public boolean exists(String username) throws Exception;
	
	public UserApp findById(Long id) throws Exception;

	public List<UserApp> findAll() throws Exception;

}

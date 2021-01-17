package ir.com.domain.repository;

import java.util.List;

import ir.com.domain.entities.UserApp;

public interface UserRepository {

	public UserApp create(UserApp userApp) throws Exception;
	
	public UserApp edit(UserApp userApp) throws Exception;
	
	public boolean remove(Long id) throws Exception;
	
	public boolean exists(String username) throws Exception;

	public UserApp loadUserByUsername(String username) throws Exception;
	
	public UserApp findById(Long id) throws Exception;
	
	public List<UserApp> findAll() throws Exception;
	
}

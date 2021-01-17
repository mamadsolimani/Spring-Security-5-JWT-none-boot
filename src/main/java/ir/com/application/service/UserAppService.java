package ir.com.application.service;

import java.util.List;

import ir.com.domain.dto.UserAppDto;

public interface UserAppService {

	public UserAppDto create(UserAppDto userAppDto) throws Exception;

	public UserAppDto edit(UserAppDto userAppDto) throws Exception;
	
	public boolean remove(Long id) throws Exception;
	
	public boolean exists(String username) throws Exception;
	
	public UserAppDto loadUserByUsername(String username) throws Exception;

	public UserAppDto findById(Long id) throws Exception;

	public List<UserAppDto> findAll() throws Exception;

}

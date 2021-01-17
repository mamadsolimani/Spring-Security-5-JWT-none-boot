package ir.com.application.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ir.com.domain.dto.UserAppDto;
import ir.com.domain.entities.UserApp;
import ir.com.domain.service.UserService;

@Service
@Transactional
public class UserAppServiceImpl implements UserAppService {

	@Autowired
	UserService userService;

	@Override
	public UserAppDto create(UserAppDto userAppDto) throws Exception {
		
		try {
			UserApp userApp = new UserApp();
			userApp.fromDto(userAppDto);
			
			userApp = userService.create(userApp);

			userAppDto = userApp.toDto();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userAppDto;
	}

	@Override
	public UserAppDto edit(UserAppDto userAppDto) throws Exception {
		
		try {
			
			UserApp userApp = new UserApp();
			userApp.fromDto(userAppDto);
			
			userApp = userService.edit(userApp);

			userAppDto = userApp.toDto();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userAppDto;
	}
	
	@Override
	public boolean remove(Long id) throws Exception {
		
		boolean result = false;

		try {
			
			UserAppDto currentUserApp = (UserAppDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if ( currentUserApp.getId().equals(id) ) {
				throw new Exception("You can't remove yourself");
			} else {
				result = userService.remove(id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	@Override
	public boolean exists(String username) throws Exception {
		
		boolean result = false;

		try {

			result = userService.exists(username);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public UserAppDto loadUserByUsername(String username) throws UsernameNotFoundException {

		UserAppDto userAppDto = null;

		try {
			UserApp userApp = (UserApp) userService.loadUserByUsername(username);

			if (userApp == null) {
				throw new UsernameNotFoundException("username not found");
			} else {
				userAppDto = userApp.toDto();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userAppDto;
	}

	@Override
	public UserAppDto findById(Long id) throws Exception {

		UserAppDto userappDto = null;

		try {
			UserApp userApp = userService.findById(id);

			if (userApp == null) {
				throw new UsernameNotFoundException("username not found");
			} else {
				userappDto = userApp.toDto();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userappDto;
	}

	@Override
	public List<UserAppDto> findAll() throws Exception {

		List<UserAppDto> userAppDtos = new ArrayList<>();

		try {
			List<UserApp> userApps = userService.findAll();

			for (UserApp userApp : userApps) {
				userAppDtos.add(userApp.toDto());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userAppDtos;
	}

}

package ir.com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ir.com.application.service.UserAppService;
import ir.com.domain.dto.UserAppDto;

@RestController
@RequestMapping(value = "/users" )
public class UserController {

	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public UserAppDto create(UserAppDto userApp) {
		
		try {
			userApp = userAppService.create(userApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userApp;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public UserAppDto edit(UserAppDto userApp) {

		try {
			userApp = userAppService.edit(userApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userApp;
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public Boolean remove(@PathVariable Long id) {

		boolean result = false;
		
		try {
			
			UserAppDto currentUserApp = (UserAppDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if ( currentUserApp.getId().equals(id) ) {
				throw new Exception("You can't remove yourself");
			} else {
				result = userAppService.remove(id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public UserAppDto findById(@PathVariable Long id) {

		UserAppDto userAppDto = null;
		
		try {
			userAppDto = userAppService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userAppDto;
	}
	
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	@PostAuthorize("returnObject.username != authentication.name")
	public UserAppDto getProfile(@PathVariable Long id) {

		UserAppDto userAppDto = null;
		
		try {
			userAppDto = userAppService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userAppDto;
	}
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<UserAppDto> findAll() {

		List<UserAppDto> list = null;
		
		try {
			list = userAppService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}

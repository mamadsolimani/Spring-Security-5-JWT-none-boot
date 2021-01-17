package ir.com.application.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ir.com.application.entites.LoginRequestDto;
import ir.com.application.entites.ResponseClient;
import ir.com.application.entites.UserChangePassword;
import ir.com.application.service.UserAppService;
import ir.com.config.JwtUtils;
import ir.com.domain.dto.UserAppDto;
import ir.com.domain.dto.UserRoleDto;
import ir.com.domain.entities.UserApp;
import ir.com.domain.enums.Permission;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@RequestBody UserAppDto userAppDto) {
	public ResponseEntity<?> registerUser() {

		try {
			
//			boolean isExists = userAppService.exists(userAppDto.getUsername());
//
//			if (isExists) {
//				ResponseClient body = new ResponseClient(false, "Username is already taken!");
//				return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//			}

//			isExists = userAppService.existsByEmail(userAppDto.getEmail());
//			
//			if (isExists) {
//				ResponseClient body = new ResponseClient(false, "Email Address already in use");
//				return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//			}
			
			UserAppDto userAppDto = new UserAppDto();
			userAppDto.setFirstName("mohammad");
			userAppDto.setLastName("soleimani");
			userAppDto.setUsername("mamad@test.com");
			userAppDto.setPassword("123");
			userAppDto.setEmail("mamad@gmail.com");
			userAppDto.setAccountNonExpired(true);
			userAppDto.setAccountNonLocked(true);
			userAppDto.setCredentialsNonExpired(true);
			userAppDto.setEnabled(true);
			
			List<Permission> permissions = new ArrayList<>();
			permissions.add(Permission.GET_USER);
			permissions.add(Permission.SEE_USERS);
			
			UserRoleDto roleDto = new UserRoleDto();
			roleDto.setPermissions(permissions);
			
			List<UserRoleDto> userRoles = new ArrayList<>();
			roleDto.setName("admin");
			roleDto.setPermissions(permissions);
			
			userRoles.add(roleDto);
			
			userAppDto.setUserRoles(userRoles);
			
			userAppDto = userAppService.create(userAppDto);
			
			ResponseClient body = new ResponseClient(true, "User registered successfully");
			return new ResponseEntity<>(body, HttpStatus.CREATED);
			
		} catch (Exception e) {
			ResponseClient body = new ResponseClient(false, e.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest, HttpServletResponse res) {

		try {

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());

			authenticationManager.authenticate(authenticationToken);

			String jwt = jwtUtils.generateToken(loginRequest.getUsername());

			res.addHeader("Authorization", jwt);

		} catch (BadCredentialsException e) {
			ResponseClient body = new ResponseClient(false, "User not found");
			return new ResponseEntity<>(body, HttpStatus.NON_AUTHORITATIVE_INFORMATION);

		} catch (Exception e) {
			new Exception("Error in jwtLogin()", e);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> logout(@RequestBody LoginRequestDto loginRequest, HttpServletResponse res) {
		
		return null;
	}

	@RequestMapping(value = "/crrent/user", method = RequestMethod.GET)
	public Principal getCurrentUser(Principal principal) {

		return principal;
	}

	@PostMapping("/change/password")
	private ResponseEntity<?> changePassword(@RequestBody UserChangePassword userChangePassword) {
		
		try {
			
//			UserAppDto userAppDto = userAppService.findByUsername(userChangePassword.getUsername());
			
			UserAppDto userAppDto = userAppService.findById(userChangePassword.getId());
			
			//TODO change password
			
			ResponseClient body = new ResponseClient(false, "User change password successfully");
			return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			ResponseClient body = new ResponseClient(false, e.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}

	}
	
}

package ir.com.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ir.com.application.service.UserAppService;
import ir.com.domain.dto.UserAppDto;
import ir.com.domain.entities.UserApp;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserAppService userAppService;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(
				HttpServletRequest req, 
				HttpServletResponse res, 
				FilterChain filterChain) throws ServletException, IOException {

		try {
			String jwt = req.getHeader("Authorization");

			if (jwt != null) {
				String username = jwtUtils.getUsername(jwt);

				// isUserAuthentication
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (username != null && authentication == null) {
					UserAppDto userAppDto = userAppService.loadUserByUsername(username);
					
					UserApp userApp = new UserApp();
					userApp.fromDto(userAppDto);
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAppDto, null, userApp.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}

			}

			filterChain.doFilter(req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

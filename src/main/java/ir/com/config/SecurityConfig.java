package ir.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ir.com.domain.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
//	@Autowired
//	private Environment env;
//	
//	String clientId = env.getProperty("sso.client-id");
//	String clientSecret = env.getProperty("sso.client-secret");
//	String clientScope[] = env.getProperty("sso.client-scope", String[].class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/auth/signin").permitAll()
			.antMatchers("/auth/signup").permitAll()
			
			.antMatchers("/users/create").hasAuthority("CREATE_USER")
			.antMatchers("/users/edit").hasAuthority("EDIT_USER")
			.antMatchers("/users/remove/**").hasAuthority("REMOVE_USER")
			.antMatchers("/users/get/**").hasAuthority("GET_USER")
			.antMatchers("/users/findAll").hasAuthority("SEE_USERS")
			//.antMatchers("/users/lock").hasAuthority("LOCK_USER")
			
			.anyRequest().authenticated()
			
			.and()
			.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and()
			.rememberMe()
			.rememberMeCookieName("remember-cookie")
			.rememberMeParameter("remember-me")
			//.tokenValiditySeconds(365 * 24 * 60 * 60) // one Year
			//.tokenValiditySeconds(1 * 24 * 60 * 60) // one Day
			.tokenValiditySeconds(30 * 24 * 60 * 60) // one Month
			
			.and()
			.logout().logoutSuccessUrl("/login?logout").logoutSuccessUrl("/login").deleteCookies("remember-cookie")
			
			;
		
		// Add our custom JWT security filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userService);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}

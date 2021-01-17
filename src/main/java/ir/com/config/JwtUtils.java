package ir.com.config;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
@PropertySource("classpath:security.properties")
public class JwtUtils {

	@Autowired
	private Environment env;
	
	public String generateToken(String username) {
		
		long currentTime = System.currentTimeMillis();
		Date expireDate = new Date(currentTime + (60 * 60 * 24 * 1000)); // 24 hours
		
		JwtBuilder jwtBuilder = Jwts.builder().setSubject(username);
		jwtBuilder.setExpiration(expireDate);
		jwtBuilder.signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.secret-key"));
		String jwt = jwtBuilder.compact();
		
		return jwt;
	}

	public String getUsername(String token) {
		
		JwtParser jwtParser = Jwts.parser().setSigningKey( env.getProperty("jwt.secret-key") );
		Claims claims = jwtParser.parseClaimsJws(token).getBody();
		
		return claims.getSubject();
	}
	
	// io.jsonwebtoken.ExpiredJwtException
	
}

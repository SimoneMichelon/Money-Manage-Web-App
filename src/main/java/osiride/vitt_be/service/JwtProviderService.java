package osiride.vitt_be.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import osiride.vitt_be.constant.JwtConstant;

@Service
public class JwtProviderService {
	
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+600000))
				.claim("email", auth.getName()).signWith(key).compact();
		return jwt;
	}
	
	public String getEmailFromJwt(String jwt) {
		jwt = jwt.substring(7);
		Claims claims =  Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		
		String email = String.valueOf(claims.get("email"));
		return email;
	}
}

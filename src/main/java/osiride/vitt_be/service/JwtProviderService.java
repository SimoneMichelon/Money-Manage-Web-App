package osiride.vitt_be.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.JwtConstant;

@Slf4j
@Service
public class JwtProviderService {
	
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+6000000))
				.claim("email", auth.getName()).signWith(key).compact();
		return jwt;
	}
	
	public String getEmailFromJwt(String jwt) {

		jwt = jwt.substring(7);
		log.debug("Inizio Parse jwt : {} ", jwt);

		
		Claims claims =  Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		
		log.debug("FINE Parse jwt : {} ", jwt);

		String email = String.valueOf(claims.get("email"));
		return email;
	}
	
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
                
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token) {
    	
    	token = token.substring(7);
    	log.debug("JWT Token: {}", token);
        Date expiration = getExpirationDateFromToken(token);
        log.info("Expiration Date : {} ",expiration);
        return expiration.before(new Date());
    }
}

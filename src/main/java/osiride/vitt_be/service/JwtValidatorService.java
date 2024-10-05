package osiride.vitt_be.service;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.JwtConstant;

@Slf4j
public class JwtValidatorService extends OncePerRequestFilter{
	
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt != null) {
			jwt = jwt.substring(7);
			try {
				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
						
				String email = String.valueOf(claims.get("email"));
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch(Exception e) {
				log.info("Authentication Failed");
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
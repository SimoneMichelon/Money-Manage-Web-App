package osiride.vitt_be.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.JwtConstant;
import osiride.vitt_be.error.BadRequestException;

@Slf4j

public class JwtValidatorService extends OncePerRequestFilter {

	@Autowired
	private JwtProviderService jwtProviderService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		String email;
		if(jwt != null) {
			try {
				
				try {
					log.error("EMAIL,  {}", jwt);
					email = jwtProviderService.getEmailFromJwt(jwt);
				}catch (Exception e) {
					throw new BadRequestException();
				}
				
				
				log.error("EMAIL,  {}", email);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
				SecurityContextHolder
				.getContext()
				.setAuthentication(authentication);
			}
			catch(BadRequestException e) {
				log.error("GESU VOLANTE");
				throw new BadCredentialsException("QUALCOSA E ANDATO STORTO");
			}catch(Exception e) {
				log.error("LA MADONNA");
				throw new BadCredentialsException("invalid token ...");
			}
		}

		filterChain.doFilter(request, response);
	}

}

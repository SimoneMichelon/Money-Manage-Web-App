package osiride.vitt_be.config;


import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import osiride.vitt_be.service.JwtValidatorService;

@Configuration
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/api/**")
				.authenticated()
				.anyRequest() 
				.permitAll())
					.addFilterBefore(new JwtValidatorService(), BasicAuthenticationFilter.class)
					.csrf(csrf -> csrf.disable())
					.cors(cors -> cors.configurationSource(corsConfigurationSource()))
					.formLogin(withDefaults());
		
		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Collections.singletonList("*"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

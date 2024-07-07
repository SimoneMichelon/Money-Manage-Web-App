package osiride.vitt_be.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.error.NotFoundException;

@Service
public class CustomCredentialDetailsService implements UserDetailsService{

	@Autowired
	private CredentialService credentialService;

	@Override
	public UserDetails loadUserByUsername(String email){
		try {
			Credential credential = credentialService
					.findByEmail(email);
			List<GrantedAuthority> authorities = new ArrayList<>();

			return new User(
					credential.getEmail(), 
					credential.getPassword(), 
					authorities);
			
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException("User not found with email : {} "+ email);
		}
	}
}

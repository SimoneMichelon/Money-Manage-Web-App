package osiride.vitt_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.dto.CredentialDTO;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InvalidPasswordException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.CredentialMapper;
import osiride.vitt_be.mapper.UserMapper;
import osiride.vitt_be.utils.AuthResponse;
import osiride.vitt_be.utils.LoginRequest;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private CredentialMapper credentialMapper;
	
	@Autowired
	private JwtProviderService jwtProviderService;
	
	@Autowired
	private CustomCredentialDetailsService credentialDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserMapper userMapper;
	
	public AuthResponse signUpHandler(CredentialDTO credentialDTO) throws BadRequestException, DuplicatedValueException, NotFoundException {
		Credential credential = credentialMapper.toEntity(credentialService.create(credentialDTO));
		Authentication authentication = new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword());
		
		SecurityContextHolder
		.getContext()
		.setAuthentication(authentication);
		
		String token = jwtProviderService
				.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		
		res.setJwt(token);
		res.setMessage("Sign Up success");
		
		return res;
	}
	
	public AuthResponse signInHandler(LoginRequest loginRequest ) throws BadRequestException, InvalidPasswordException {
		if(loginRequest == null) {
			log.error("SERVICE - Login Request Data is Null - SIGN IN");
			throw new BadRequestException();
		}
		
		Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProviderService
				.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		
		res.setJwt(token);
		res.setMessage("Sign In Success");
		
		log.info("SERVICE - Sign In Success - SIGN IN");
		return res;
	}
	
	public UserDTO getPrincipal(String jwt) throws InvalidTokenException, NotFoundException {
		String email = jwtProviderService.getEmailFromJwt(jwt);
		
		if(email == null) {
			log.error("SERVICE - Invalid Token, Email is null - USER BY JWT");
			throw new InvalidTokenException();
		}
		Credential credential = credentialService.findByEmail(email);
		
		log.info("SERVICE - Login Request Data is Null - SIGN IN");
		return userMapper.toDto(credential.getUser());
	}

	private Authentication authenticate(String email, String password) throws BadRequestException, InvalidPasswordException {
		UserDetails userDetails = credentialDetailsService.loadUserByUsername(email);
		
		if(userDetails == null) {
			log.error("SERVICE - Login Request Data is Null - AUTHENTICATE");
			throw new BadRequestException();
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			log.error("SERVICE - Invalid Password - AUTHENTICATE");
			throw new InvalidPasswordException();
		}
		
		log.info("SERVICE - Authenticated Credentials - AUTHENTICATE");
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
	
	}
}

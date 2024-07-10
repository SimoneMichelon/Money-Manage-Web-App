package osiride.vitt_be.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.JwtConstant;
import osiride.vitt_be.constant.Role;
import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.dto.CredentialDTO;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InvalidPasswordException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.CredentialMapper;
import osiride.vitt_be.utils.AuthResponse;
import osiride.vitt_be.utils.LoginRequest;

@Slf4j
@Service
public class AuthService {

	private final CredentialService credentialService;
    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final JwtProviderService jwtProviderService;
    private final CustomCredentialDetailsService credentialDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CredentialService credentialService, 
    				   UserService userService,
                       CredentialMapper credentialMapper, 
                       JwtProviderService jwtProviderService,
                       CustomCredentialDetailsService credentialDetailsService, 
                       PasswordEncoder passwordEncoder) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.jwtProviderService = jwtProviderService;
        this.credentialDetailsService = credentialDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


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

	public UserDTO getPrincipal() throws InvalidTokenException, NotFoundException, BadRequestException {
		String email = jwtProviderService.getEmailFromJwt(getTokenJwt());

		if(email == null) {
			log.error("SERVICE - Invalid Token, Email is null - USER BY JWT");
			throw new InvalidTokenException();
		}
		Credential credential = credentialService.findByEmail(email);

		UserDTO userDTO = userService.findById(credential.getUser().getId());

		log.info("SERVICE - Login Request Data is Null - SIGN IN");
		return userDTO;
	}

	public boolean isPrincipal(User user) throws BadRequestException, InvalidTokenException, NotFoundException {
		String jwt = getTokenJwt();
		if(jwt == null) {
			log.error("SERVICE - JWT Token is null - IS AUTH USER");
			throw new BadRequestException();
		}
		UserDTO userValidated = getPrincipal();
		UserDTO userChecker = userService.findById(userValidated.getId());

		log.info("SERVICE - Check is Auth User - IS AUTH USER");
		return userValidated.equals(userChecker);
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

	public String getTokenJwt() throws InvalidTokenException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt == null || jwt.isEmpty()) {
			log.error("SERVICE - Invalid Token : {}  - GET TOKEN", jwt);
			throw new InvalidTokenException();
		}

		return jwt;
	}

	public boolean isAdmin() throws InvalidTokenException, NotFoundException, BadRequestException {
		return Role.ADMIN
				.equals(getPrincipal()
						.getRole()) ? 
								true : 
									false;
	}
	
	public boolean isGuest() throws InvalidTokenException, NotFoundException, BadRequestException {
		return Role.ADMIN
				.equals(getPrincipal()
						.getRole()) ? 
								true : 
									false;
	}
}

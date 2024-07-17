package osiride.vitt_be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.CredentialDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InvalidPasswordException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.AuthService;
import osiride.vitt_be.utils.AuthResponse;
import osiride.vitt_be.utils.LoginRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Operation(summary = "Sign Up", description = "Sign up givin Credentials Details")
	@PostMapping( value ="/signUp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<AuthResponse> signUp(@RequestBody CredentialDTO credentialDTO) {
		try {
			AuthResponse result = authService.signUpHandler(credentialDTO);
			log.info("REST - Sign Up success: email = {} - SIGN UP", credentialDTO.getEmail());
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - SIGN UP");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Email Error - SIGN UP");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			log.error("REST - User for Credential NOT found - SIGN UP");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	 

	@Operation(summary = "Sign Ip", description = "Sign ip givin email and password")
	@PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest){
		try {
			AuthResponse result = authService.signInHandler(loginRequest);
			log.info("REST - Sign Up success: email = {} - SIGN IN", loginRequest.getEmail());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - SIGN IN");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (InvalidPasswordException e) {
			log.error("REST - Invalid Password given - SIGN IN");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@Operation(summary = "Is Auth", description = "Is Token Valid")
	@GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> isAuth() {
		boolean result = authService.isAuth();
		log.info("REST - Is Authenticated : {} - IS AUTH", result);
		return ResponseEntity.status(HttpStatus.OK).body(Boolean.valueOf(result));
	}
}

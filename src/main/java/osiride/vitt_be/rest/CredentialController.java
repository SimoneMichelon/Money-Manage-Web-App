package osiride.vitt_be.rest;

import java.util.List;

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
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.CredentialService;

@Slf4j
@RestController
@RequestMapping("/auth-management")
public class CredentialController {

	@Autowired
	private CredentialService credentialService;
	
	@Operation(summary = "Get all Credentials", description = "Get all credentials ")
	@GetMapping(value = "/credentials")
	public ResponseEntity<List<CredentialDTO>> getAllCredentials(){
		List<CredentialDTO> result = credentialService.getAll();
		log.info("REST - Vault's list size : {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Operation(summary = "Sign up with Credentials", description = "Creation credential by given data")
	@PostMapping(value = "/credentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CredentialDTO> createCredential(@RequestBody CredentialDTO credentialDTO) throws BadRequestException {
		try {
			CredentialDTO result = credentialService.create(credentialDTO);
			log.info("REST - Credential created - CREATE");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Email Error - CREATE");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (NotFoundException e) {
			log.error("REST - User for Credential NOT found - CREATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}

package osiride.vitt_be.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.AuthService;
import osiride.vitt_be.service.VaultService;
import osiride.vitt_be.utils.VaultSummary;

@Slf4j
@RestController
@RequestMapping("/api/vault-management")
public class VaultController {

	@Autowired 
	private VaultService vaultService;
	
	@Autowired
	private AuthService authService;

	@Operation(summary = "Get all Vault - ADMIN", description = "Get all vault ")
	@GetMapping(value = "/vaults", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VaultDTO>> getAllVaults(){
		try {	
			if(authService.isAdmin()) 
			{
				List<VaultDTO> result = vaultService.getAll();
				log.info("REST - Vault's list size : {} - READ ALL", result.size());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			else {
				log.error("REST - Vault Operation not allowed - READ ALL");
				throw new NotAuthorizedException();
			}
		} catch (BadRequestException e) {
			log.error("REST - User Bad Info - READ ALL");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - READ ALL");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - READ ALL");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  - READ ALL");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@Operation(summary = "Get all vault by User Id - ADMIN", description = "Get all vault by User given")
	@GetMapping(value = "/vaults/user/{id}" )
	public ResponseEntity<List<VaultDTO>> getAllVaultsByUserId(@PathVariable Long id){
		try {
			List<VaultDTO> result = vaultService.getAllVaultByUserId(id);
			log.info("REST - Vault's list size : {} - READ ALL by USER ID", result.size());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ALL by USER ID");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - READ ALL by USER ID");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - READ ALL by USER ID");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  -  READ ALL by USER ID");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@Operation(summary = "Get all vault by Principal", description = "Get all vault Principal")
	@GetMapping(value = "/vaults/user" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VaultDTO>> getAllVaultsByPrincipal(){
		try {
			List<VaultDTO> result = vaultService.getAllVaultByPrincipal();
			log.info("REST - Vault's list size : {} - READ ALL by PRINCIPAL", result.size());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ALL by PRINCIPAL");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - READ ALL by PRINCIPAL");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - READ ALL by PRINCIPAL");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	@Operation(summary = "Get vaults Report", description = "Get vaults Report")
	@GetMapping(value = "/vaults/report" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VaultSummary> > getVaultsReport(){
		try {
			List<VaultSummary>  result = vaultService.getPrincipalVaultReport();
			log.info("REST - Vault's list size : {} - REPORT");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - REPORT");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - REPORT");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - REPORT");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	


	@Operation(summary = "Find vault by id - ADMIN / GUEST", description = "Find vault by id")
	@GetMapping(value = "/vaults/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VaultDTO> getVaultById(@PathVariable Long id){
		try {			
			VaultDTO result = vaultService.findById(id);
			log.info("REST - Vault found : {} - READ ONE", result);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ONE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Vault NOT found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NotAuthorizedException | InvalidTokenException e) {
			log.error("REST - Not Authorized || Invalid Token - READ ONE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Create vault ", description = "Creation vault by given data")
	@PostMapping(value = "/vaults", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VaultDTO> createVault(@RequestBody VaultDTO vaultDTO){
		try {			
			VaultDTO result = vaultService.create(vaultDTO);
			log.info("REST - Vault created - CREATE");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User for Vault NOT found - CREATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Vault Name Error - CREATE");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - CREATE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Update vault", description = "Update vault by given data")
	@PutMapping(value = "/vaults", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<VaultDTO> updateVault(@RequestBody VaultDTO vaultDTO){
		try {
			VaultDTO result = vaultService.update(vaultDTO);
			log.info("REST - Vault Updated - UPDATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - UPDATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Vault NOT found - UPDATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (DuplicatedValueException e) {
			log.error("REST - Duplicated Vault Name Error - UPDATE");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}  catch (InvalidTokenException  e) {
			log.error("REST - Invalid Token - UPDATE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  - UPDATE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@Operation(summary = "Delete vault by id", description = "Delete vault by id")
	@DeleteMapping(value = "/vaults/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VaultDTO> deleteVaultById(@PathVariable Long id){
		try {
			VaultDTO result = vaultService.deleteById(id);
			log.info("REST - Vault Deleted, ID : {} - DELETE", id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - DELETE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Vault NOT found - DELETE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InternalServerException e) {
			log.error("REST - Error on deleting Vault - DELETE");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}  catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - DELETE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  - DELETE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}

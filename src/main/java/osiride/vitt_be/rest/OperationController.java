package osiride.vitt_be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.AuthService;
import osiride.vitt_be.service.OperationService;
import osiride.vitt_be.utils.OperationListsWrapper;

@Slf4j
@RestController
@RequestMapping("/api/operation-management")
public class OperationController {
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private AuthService authService;
	
	@Operation(summary = "Get all Operations - ADMIN", description = "Get all Operations ")
	@GetMapping(value = "/operations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperationListsWrapper> getAllOperations(){
		try {	
			if(authService.isAdmin()) 
			{
				OperationListsWrapper result = operationService.getAllOperations();
				log.info("REST - Operation's list size : {} - READ ALL", (result.getListExpense().size()+result.getListRevenue().size()));
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			else {
				log.error("REST - Operation not allowed - READ ALL");
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
	
	@Operation(summary = "Get all Operations by Vault Id - ADMIN", description = "Get all Operations by Vault given")
	@GetMapping(value = "/operations/vault/{id}" )
	public ResponseEntity<OperationListsWrapper> getAllOperationsByUserId(@PathVariable Long id){
		try {
			OperationListsWrapper result = operationService.getAllOperationsByVault(id);
			log.info("REST - Operation's list size : {} - READ ALL", (result.getListExpense().size()+result.getListRevenue().size()));
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ALL by VAULT ID");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - READ ALL by VAULT ID");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - READ ALL by VAULT ID");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  -  READ ALL by VAULT ID");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}

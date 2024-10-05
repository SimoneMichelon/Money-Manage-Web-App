package osiride.vitt_be.rest;

import java.util.List;
import java.util.Map;

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
import osiride.vitt_be.utils.CategoryReportDTO;
import osiride.vitt_be.utils.OperationDTO;
import osiride.vitt_be.utils.PriceHistoryObj;
import osiride.vitt_be.utils.TransactionType;

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
	public ResponseEntity<List<OperationDTO>> getAllOperations(){
		try {	
			if(authService.isAdmin()) 
			{
				List<OperationDTO> result = operationService.getAllOperations();
				log.info("REST - Operation's list size : {} - READ ALL", (result.size()));
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
	
	@Operation(summary = "Get all Operations by Vault Id", description = "Get all Operations by Vault ")
	@GetMapping(value = "/operations/vault/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OperationDTO>> getAllOperationsByVaultId(@PathVariable Long id){
		try {
			List<OperationDTO> result = operationService.getAllOperationsByVaultId(id);
			log.info("REST - Operation's list size by VAULT: {} - READ ALL", (result.size()));
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
	
	@Operation(summary = "Get all Operations by principal", description = "Get all Operations by principal")
	@GetMapping(value = "/operations/principal" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OperationDTO>> getAllOperationsByPrincipal(){
		try {
			List<OperationDTO> result = operationService.getAllOperationsByPrincipal();
			log.info("REST - Operation's list size by PRINCIPAL: {} - READ ALL", (result.size()));
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
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  -  READ ALL by PRINCIPAL");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@Operation(summary = "Get the vault report", description = "Get the vault report")
	@GetMapping(value = "/report/vault/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PriceHistoryObj>> getVaultHistoryReport(@PathVariable Long id){
		try {
			List<PriceHistoryObj> result = operationService.getOperationHistoryReport(id);
			log.info("REST - Report list size : {} - REPORT", (result.size()));
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
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  -  REPORT");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@Operation(summary = "Get the category report on vault id", description = "Get the category report on vault id")
	@GetMapping(value = "/report/category/vault/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<TransactionType,List<CategoryReportDTO>>> getOperationCategoryReportPerVault(@PathVariable Long id){
		try {
			Map<TransactionType,List<CategoryReportDTO>> result = operationService.getOperationCategoryReportPerVault(id);
			log.info("REST - Report list size : {} - CATEGORY REPORT", (result.size()));
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - CATEGORY REPORT");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - CATEGORY REPORT");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - CATEGORY REPORT");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  - CATEGORY REPORT");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}

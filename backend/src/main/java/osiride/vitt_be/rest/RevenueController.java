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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.RevenueDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.error.OperationNotPermittedException;
import osiride.vitt_be.service.AuthService;
import osiride.vitt_be.service.RevenueService;

@Slf4j
@RestController
@RequestMapping("/api/revenue-management")
public class RevenueController {

	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private AuthService authService;

	@Operation(summary = "Get all Revenue", description = "Get all Revenue")
	@GetMapping(value = "/revenues", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RevenueDTO>> getAllRevenue() {
		List<RevenueDTO> result = revenueService.getAll();
		log.info("REST - Third Party list size: {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "Find Revenue by id", description = "Get a Revenue by ID")
	@GetMapping(value = "/revenues/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevenueDTO> getRevenueById(@PathVariable Long id){
		try {			
			RevenueDTO result = revenueService.findById(id);
			log.info("REST - Revenue found : {} - READ ONE", result);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ONE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Revenue NOT found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Create revenue", description = "Creation revenue by given data")
	@PostMapping(value = "/revenues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevenueDTO> createRevenue(@Valid @RequestBody RevenueDTO revenueDTO){
		try {			
			RevenueDTO result = revenueService.create(revenueDTO);
			log.info("REST - Revenue created - CREATE");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Revenue Data NOT found - CREATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (OperationNotPermittedException e) {
			log.error("REST - Operation Not Permitted Error - CREATE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (NotAuthorizedException | InvalidTokenException e) {
			log.error("REST - Not Authorized || Invalid Token - CREATE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Operation(summary = "Update revenue", description = "Update revenue by given data")
	@PutMapping(value = "/revenues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<RevenueDTO> updateRevenue(@Valid @RequestBody RevenueDTO revenueDTO){
		try {
			RevenueDTO result = revenueService.update(revenueDTO);
			log.info("REST - Revenue Updated - UPDATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - UPDATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Revenue NOT found - UPDATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (OperationNotPermittedException e) {
			log.error("REST - Operation Not Permitted Error - UPDATE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (NotAuthorizedException | InvalidTokenException e) {
			log.error("REST - Not Authorized || Invalid Token - UPDATE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Operation(summary = "Delete revenue by id", description = "Delete revenue by id")
	@DeleteMapping(value = "/revenues/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RevenueDTO> deleteRevenueById(@PathVariable Long id){
		try {
			RevenueDTO result = revenueService.deleteById(id);
			log.info("REST - Revenue Deleted, ID : {} - DELETE", id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - DELETE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Revenue NOT found - DELETE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InternalServerException e) {
			log.error("REST - Error on deleting Revenue - DELETE");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (OperationNotPermittedException e) {
			log.error("REST - Operation Not Permitted Error - DELETE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (NotAuthorizedException | InvalidTokenException e) {
			log.error("REST - Not Authorized || Invalid Token - DELETE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Operation(summary = "Get revenues by vault", description = "Get revenues by vault")
	@GetMapping(value = "/revenues/vault/{id}")
	public ResponseEntity<List<RevenueDTO>> getRevenuesByVault(@PathVariable Long id){
		try {
			if(authService.isAdmin()) 
			{
				List<RevenueDTO> result = revenueService.getByVault(id);
				log.info("REST - Revenue list size: {} - READ ALL", result.size());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			else {
				log.error("REST - Revenue Operation not allowed - READ VAULT");
				throw new NotAuthorizedException();
			}
		} catch (BadRequestException e) {
			log.error("REST - User Bad Info - READ VAULT");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - User NOT found - READ VAULT");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - READ VAULT");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (NotAuthorizedException e) {
			log.error("REST - Not Authorized  - READ VAULT");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

	}
	
}

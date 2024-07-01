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
import osiride.vitt_be.dto.ExpenseDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.error.OperationNotPermittedException;
import osiride.vitt_be.service.ExpenseService;

@Slf4j
@RestController
@RequestMapping("/api/expense-management")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@Operation(summary = "Get all Expense", description = "Get all Expense")
	@GetMapping(value = "/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExpenseDTO>> getAllExpense() {
		List<ExpenseDTO> result = expenseService.getAll();
		log.info("REST - Third Party list size: {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "Find Expense by id", description = "Get a Expense by ID")
	@GetMapping(value = "/expenses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id){
		try {			
			ExpenseDTO result = expenseService.findById(id);
			log.info("REST - Expense found : {} - READ ONE", result);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ONE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Expense NOT found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Create expense", description = "Creation expense by given data")
	@PostMapping(value = "/expenses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO){
		try {			
			ExpenseDTO result = expenseService.create(expenseDTO);
			log.info("REST - Expense created - CREATE");
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch(BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Expense Data NOT found - CREATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (OperationNotPermittedException e) {
			log.error("REST - Operation Not Permitted Error - UPDATE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@Operation(summary = "Update expense", description = "Update expense by given data")
	@PutMapping(value = "/expenses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseDTO expenseDTO){
		try {
			ExpenseDTO result = expenseService.update(expenseDTO);
			log.info("REST - Expense Updated - UPDATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - UPDATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Expense NOT found - UPDATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (OperationNotPermittedException e) {
			log.error("REST - Operation Not Permitted Error - UPDATE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@Operation(summary = "Delete expense by id", description = "Delete expense by id")
	@DeleteMapping(value = "/expenses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseDTO> deleteExpenseById(@PathVariable Long id){
		try {
			ExpenseDTO result = expenseService.deleteById(id);
			log.info("REST - Expense Deleted, ID : {} - DELETE", id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad information given - DELETE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Expense NOT found - DELETE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (InternalServerException e) {
			log.error("REST - Error on deleting Expense - DELETE");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}

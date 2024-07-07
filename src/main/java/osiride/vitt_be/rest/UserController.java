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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.AuthService;
import osiride.vitt_be.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user-management")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@Operation(summary = "Get all Users", description = "Returns all Users")
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> result = userService.getAll();
		log.info("REST - Users's list size : {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "Get User By JWT", description = "Get User By JWT")
	@GetMapping(value = "/user/profile", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserDTO> getUserByJwt(@RequestHeader("Authorization") String jwt){
		try {
			UserDTO result = authService.getPrincipal(jwt);
			log.info("REST - User returned - JWT");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (InvalidTokenException e) {
			log.error("REST - Invalid Token - JWT");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (NotFoundException e) {
			log.error("REST - User has not been found - JWT");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	@Operation(summary = "Get user by Id", description = "Get user by Id")
	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		try {
			UserDTO result = userService.findById(id);
			log.info("REST - Users returned - READ ONE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch(BadRequestException e) {
			log.error("REST - Bad Request Error - READ ONE ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch(NotFoundException e) {
			log.error("REST - User has not been found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	

	@Operation(summary = "Create user", description = "Create a user")
	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		try {
			UserDTO result = userService.create(userDTO);
			log.info("REST - User created, id : {}, First Name : {}, Last Name : {} - READ ALL", result.getId() ,result.getFirstName(), result.getLastName());
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (BadRequestException e) {
			log.error("REST - Bad Request, Data given is invalid - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	@Operation(summary = "Update user", description = "Update a user")
	@PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO){
		try {
			UserDTO result = userService.update(userDTO);
			log.info("REST - User updated, id : {}, First Name : {}, Last Name : {} - READ ALL", result.getId() ,result.getFirstName(), result.getLastName());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch(NotFoundException e) {
			log.error("REST - User has not been found - UPDATE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch(BadRequestException e) {
			log.error("REST - Bad Request, Data given is invalid - UPDATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Delete user by Id", description = "Delete user by his id")
	@DeleteMapping( value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> deleteUserById(@PathVariable Long id) throws BadRequestException{
		try {
			UserDTO result = userService.deleteById(id);
			log.info("REST - User Deleted, Id : {}", id);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch(InternalServerException e){
			log.error("REST - Server Operation Error, User still present on Database - DELETE");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch(NotFoundException e) {
			log.error("REST - User has not been found - DELETE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}

package osiride.vitt_be.rest;

import java.util.List;
import java.util.Optional;

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
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user-management")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Operation(summary = "Get all Users", description = "Returns all Users")
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> users = userService.getAllUsers();
		log.info("REST - Users's list size : {}", users.size());
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}	
	
	@Operation(summary = "Get user by Id", description = "Get user by Id")
	@GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		Optional<UserDTO> result = userService.findById(id);
		if(result.isPresent()) {
			log.info("REST - Users returned");
			return ResponseEntity.status(HttpStatus.OK).body(result.get());
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	@Operation(summary = "Create user", description = "Create a user")
	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
		try {
			UserDTO result = userService.createUser(userDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@Operation(summary = "Update user", description = "Update a user")
	@PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@Operation(summary = "Delete user by Id", description = "Delete user by his id")
	@DeleteMapping( value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> deleteUserById(@PathVariable Long id){
		try {
			Optional<UserDTO> result = userService.deleteUserById(id);
			if(result.isPresent()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		catch(InternalServerException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}

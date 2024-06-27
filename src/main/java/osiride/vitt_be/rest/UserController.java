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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user-management")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Operation(summary = "Get all Users", description = "Returns all Users")
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.getAllUsers();
		log.info("REST - Users's list size : {}", users.size());
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}	
	

	

	
	//@PostMapping
	
	@Operation(summary = "Update user", description = "Update a user")
	@PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<User> updateUser(@PathVariable Long id){
		User user = new User();
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	//@DeleteMapping
	
}

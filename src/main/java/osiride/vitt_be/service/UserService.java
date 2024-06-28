package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.mapper.UserMapper;
import osiride.vitt_be.repository.UserRepository;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * getAllUsers
	 * @return List<User>
	 */
	public List<UserDTO> getAllUsers(){
		return userRepository.findAll().stream().map(user -> userMapper.toDto(user)).toList();
	}
	
	
	/**
	 * Find a User by Id, if not found, return empty optional
	 * @param id
	 * @return Optional<UserDTO> or Optional.empty()
	 *
	 */
	public Optional<UserDTO> findById(Long id){
		Optional<User> maybeUser = userRepository.findById(id);
		if(maybeUser.isPresent()) {
			return Optional.of(userMapper.toDto(maybeUser.get()));
		}
		else {
			return Optional.empty();
		}
	}
	
	public Optional<UserDTO> updateUser(UserDTO user) {
		/*
		 * controllo se esiste già  (ricerca tramite id)
		 * se non esiste - Exception 
		 * se esiste :
		 * 		controllo i dati -> se il fato è null, non lo modifico, altrimenti si
			   	firstName 
			    lastName;
			   	dob;
			   	imgProfile;
		 */
		return null;
	}
	
	/**
	 * Create user by a userDTO given
	 * @param userDTO
	 * @return UserDTO
	 * @throws IllegalArgumentException
	 */
	public UserDTO createUser(UserDTO userDTO){
		if(userDTO == null) {
			log.error("SERVICE - UserDTO is null | IllegalArgumentException thrown !");
			throw new IllegalArgumentException();
		}
		
		if(userDTO.getFirstName() == null || 
				userDTO.getLastName() == null || 
				userDTO.getDob() == null || 
				userDTO.getImgProfile() == null) 
		{	
			log.error("SERVICE - At least one UserDTO propertie is null | IllegalArgumentException thrown !");
			throw new IllegalArgumentException();
		}
		
		User user = userMapper.toEntity(userDTO);
		return userMapper.toDto(userRepository.save(user));
	}
	
	/**
	 * Delete User By Id 
	 * @param id
	 * @return Optional<UserDTO> or Optional.empty()
	 */
	public Optional<UserDTO> deleteUserById(Long id) {
		Optional<User> maybeUser = userRepository.findById(id);
		
		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			userRepository.delete(user);
			
			if(!userRepository.existsById(id)) {
				return Optional.of(userMapper.toDto(user));
			}
			else {
				throw new InternalServerException();
			}
		}
		else {
			return Optional.empty();
		}
	}
}

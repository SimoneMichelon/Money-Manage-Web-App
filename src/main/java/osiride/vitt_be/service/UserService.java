package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
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
	 * 
	 * Find a User by Id, if not found, return empty optional
	 * @param id
	 * @return Optional<UserDTO> or Optional.empty()
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	public UserDTO findById(Long id) throws NotFoundException, BadRequestException {
		if(id == null) {
			throw new BadRequestException();
		}
		
		Optional<User> maybeUser = userRepository.findById(id);
		if(maybeUser.isPresent()) {
			return userMapper.toDto(maybeUser.get());
		}
		else {
			throw new NotFoundException();
		}
	}

	/**
	 * Update user by a userDTO given
	 * @param userDTO
	 * @return
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	public UserDTO updateUser(UserDTO userDTO) throws NotFoundException, BadRequestException {
		if (userDTO == null || userDTO.getId() == null) {
			throw new BadRequestException();
		}
		
		if(!isDataValid(userDTO)) 
		{	
			log.error("SERVICE - At least one UserDTO propertie is null | BadRequestException thrown !");
			throw new BadRequestException();
		}

		Optional<User> maybeUser = userRepository.findById(userDTO.getId());
		if(maybeUser.isEmpty()) {
			throw new NotFoundException();
		}

		User newUser = userMapper.toEntity(userDTO);
		newUser = userRepository.save(newUser);
		return userMapper.toDto(newUser);
	}

	/**
	 * Create user by a userDTO given, doesn't case about userDTO.id
	 * @param userDTO
	 * @return UserDTO
	 * @throws BadRequestException
	 */
	public UserDTO createUser(UserDTO userDTO) throws BadRequestException{
		if(userDTO == null) {
			log.error("SERVICE - UserDTO is null | BadRequestException thrown !");
			throw new BadRequestException();
		}

		if(!isDataValid(userDTO)) 
		{	
			log.error("SERVICE - At least one UserDTO propertie is null | BadRequestException thrown !");
			throw new BadRequestException();
		}
		User user = userMapper.toEntity(userDTO);
		user.setId(null);
		return userMapper.toDto(userRepository.save(user));
	}

	/**
	 * Delete User By Id 
	 * @param id
	 * @return Optional<UserDTO> or Optional.empty()
	 */
	public UserDTO deleteUserById(Long id) {
		Optional<User> maybeUser = userRepository.findById(id);

		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			userRepository.delete(user);

			if(!userRepository.existsById(id)) {
				return userMapper.toDto(user);
			}
			else {
				throw new InternalServerException();
			}
		}
		else {
			throw new NotFoundException();
		}
	}

	public boolean isDataValid(UserDTO userDTO) {
		return (userDTO.getFirstName() == null || 
				userDTO.getLastName() == null || 
				userDTO.getDob() == null || 
				userDTO.getImgProfile() == null) 
				? false 
						: true;

	}
}

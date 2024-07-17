package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.Role;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.UserMapper;
import osiride.vitt_be.repository.UserRepository;

@Slf4j
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final AuthService authService;

	public UserService(UserRepository userRepository, 
			UserMapper userMapper,
			@Lazy AuthService authService) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.authService = authService;
	}

	/**
	 * <p>
	 * Retrieves a list of all users as UserDTO objects.
	 * <br><br>
	 *
	 * @return a list of UserDTO objects representing all users.
	 * @throws NotAuthorizedException if the current user is not authorized to perform this operation.
	 * <br><br>
	 *</p>
	 * This method checks if the current user has administrative privileges by calling the {@code isAdmin}
	 * method of the {@code authService}. If the user is an admin, it retrieves all users from the
	 * {@code userRepository}, maps them to {@code UserDTO} objects using the {@code userMapper}, and returns
	 * the list of these DTOs. If the user is not an admin, a {@code NotAuthorizedException} is thrown.
	 * Additionally, if any of the following exceptions are encountered during the operation:
	 * {@code InvalidTokenException}, {@code NotFoundException}, or {@code BadRequestExceptio-n}, a 
	 * {@code NotAuthorizedException} is thrown.
	 */
	public List<UserDTO> getAll(){
				return userRepository
						.findAll()
						.stream()
						.map(user -> userMapper
								.toDto(user))
						.toList();
	}

	/**
	 * Retrieves a user by their ID and maps it to a UserDTO object.
	 *
	 * @param id the ID of the user to be retrieved.
	 * @return a UserDTO object representing the user with the specified ID.
	 * @throws NotFoundException if a user with the specified ID is not found.
	 * @throws BadRequestException if the provided ID is null.
	 *
	 * This method attempts to retrieve a user by their ID from the {@code userRepository}. 
	 * If the provided ID is null, a {@code BadRequestException} is thrown. If a user with the 
	 * specified ID is found, it is mapped to a {@code UserDTO} object using the {@code userMapper} 
	 * and returned. If the user is not found, a {@code NotFoundException} is thrown.
	 * @throws InternalServerException 
	 */
	public UserDTO findById(Long id) throws NotFoundException, BadRequestException {
		if(id == null) {
			log.error("SERVICE - User id is null - FIND ONE");
			throw new BadRequestException();
		}

		Optional<User> maybeUser = userRepository.findById(id);
		if(maybeUser.isPresent()) {
			return userMapper.toDto(maybeUser.get());
		}
		else {
			log.error("SERVICE - User not found - FIND ONE");
			throw new NotFoundException();
		}
		

		
	}

	/**
	 * Update user by a userDTO given
	 * @param userDTO
	 * @return
	 * @throws NotFoundException
	 * @throws BadRequestException
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */
	public UserDTO update(UserDTO userDTO) throws NotFoundException, BadRequestException, InvalidTokenException, NotAuthorizedException {	
		if (userDTO == null || userDTO.getId() == null || !isDataValid(userDTO)) {
			log.error("SERVICE - USER Data given is null - UPDATE");
			throw new BadRequestException();
		}

		if(userRepository.existsById(userDTO.getId())) {
			User newUser = userMapper.toEntity(userDTO);
			if(authService.isAdmin() || authService.isPrincipal(newUser)) {
				newUser = userRepository.save(newUser);
				return userMapper.toDto(newUser);
			}
			else {
				log.error("SERVICE - User Operation not allowed - FIND ONE");
				throw new NotAuthorizedException();
			}	
		}
		else {
			log.error("SERVICE - User Not Found - UPDATE");
			throw new NotFoundException();
		}		
	}

	/**
	 * Create user by a userDTO given, doesn't case about userDTO.id
	 * @param userDTO
	 * @return UserDTO
	 * @throws BadRequestException
	 */
	public UserDTO create(UserDTO userDTO) throws BadRequestException{
		if(userDTO == null || !isDataValid(userDTO)) {
			log.error("SERVICE - USER Data given is null - CREATE");
			throw new BadRequestException();
		}

		User user = userMapper.toEntity(userDTO);
		user.setImgProfile("https://www.svgrepo.com/show/408476/user-person-profile-block-account-circle.svg");
		user.setId(null);
		user.setRole(Role.GUEST);
		return userMapper.toDto(userRepository.save(user));
	}

	/**
	 * Delete User By Id 
	 * @param id
	 * @return UserDTO
	 * @throws InternalServerException
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	public UserDTO deleteById(Long id) throws InternalServerException, NotFoundException, BadRequestException{
		if(id == null) {
			log.error("SERVICE - User id is null - DELETE");
			throw new BadRequestException();
		}
		Optional<User> maybeUser = userRepository.findById(id);

		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			userRepository.delete(user);

			if(!userRepository.existsById(id)) {
				return userMapper.toDto(user);
			}
			else {
				log.error("SERVICE - User Not Deleted due to Unknown Error - DELETE");
				throw new InternalServerException();
			}
		}
		else {
			log.error("SERVICE - User Not Found - DELETE");
			throw new NotFoundException();
		}
	}

	private boolean isDataValid(UserDTO userDTO) {
		return (userDTO.getFirstName() == null || 
				userDTO.getLastName() == null || 
				userDTO.getDob() == null || 
				userDTO.getImgProfile() == null ||
				userDTO.getRole() == null) 
				? false 
						: true;
	}
}

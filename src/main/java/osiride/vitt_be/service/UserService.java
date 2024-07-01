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
	public List<UserDTO> getAll(){
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
	 */
	public UserDTO update(UserDTO userDTO) throws NotFoundException, BadRequestException {
		if (userDTO == null || userDTO.getId() == null || !isDataValid(userDTO)) {
			log.error("SERVICE - USER Data given is null - UPDATE");
			throw new BadRequestException();
		}

		Optional<User> maybeUser = userRepository.findById(userDTO.getId());
		if(maybeUser.isEmpty()) {
			log.error("SERVICE - User Not Found - UPDATE");
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
	public UserDTO create(UserDTO userDTO) throws BadRequestException{
		if(userDTO == null || !isDataValid(userDTO)) {
			log.error("SERVICE - USER Data given is null - CREATE");
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
				userDTO.getImgProfile() == null) 
				? false 
						: true;
	}
}

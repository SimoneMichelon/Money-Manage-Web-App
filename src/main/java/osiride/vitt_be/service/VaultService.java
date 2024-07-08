package osiride.vitt_be.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.domain.Operation;
import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.UserMapper;
import osiride.vitt_be.mapper.VaultMapper;
import osiride.vitt_be.repository.VaultRepository;

@Slf4j
@Service
public class VaultService {

	@Autowired
	private VaultRepository vaultRepository;

	@Autowired
	private VaultMapper vaultMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	/**
	 * Get all vaults in database
	 * @return List<VaultDTO>
	 * @throws BadRequestException 
	 * @throws NotFoundException 
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */
	public List<VaultDTO> getAll() throws InvalidTokenException, NotFoundException, BadRequestException, NotAuthorizedException{
		if(authService.isAdmin()) 
		{
			return vaultRepository.findAll().stream().map(vault -> vaultMapper.toDto(vault)).toList(); 
		}
		else {
			log.error("SERVICE - Vault Operation not allowed - DELETE");
			throw new NotAuthorizedException();
		}
	}

	/**
	 * Find a vault by Id
	 * @param id
	 * @return VaultDTO
	 * @throws BadRequestException
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */
	public VaultDTO findById(Long id) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		if(id == null) {
			log.error("SERVICE - Vault id is null - FIND ONE");
			throw new BadRequestException();
		}
		Optional<Vault> maybeVault = vaultRepository.findById(id);

		if(maybeVault.isPresent()) {
			VaultDTO vaultDTO = vaultMapper.toDto(maybeVault.get());

			UserDTO principal = authService.getPrincipal();
			if(authService.isAdmin() || isOwner(principal, vaultDTO)) {
				return vaultDTO;
			}
			else {
				log.error("SERVICE - Vault Operation not allowed - FIND ONE");
				throw new NotAuthorizedException();
			}
		}
		else {
			log.error("SERVICE - Vault not found - FIND ONE");
			throw new NotFoundException();
		}
	}

	/**
	 * Create a Vault by data given by parameter
	 * @param vaultDTO
	 * @return VaultDTO
	 * @throws BadRequestException
	 * @throws NotFoundException
	 * @throws DuplicatedValueException 
	 * @throws InvalidTokenException 
	 * @throws SQLException
	 */

	//FIXME CREARE UN METODO CHE CERCA SE IL VAULT ESISTE GIA CON QUEL NOME PER NON ROMPERE NEL DB
	public VaultDTO create(VaultDTO vaultDTO) throws BadRequestException, NotFoundException, DuplicatedValueException, InvalidTokenException{
		if(vaultDTO == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - CREATE");
			throw new BadRequestException();
		}

		Vault vault = vaultMapper.toEntity(vaultDTO);
		
		UserDTO user = authService.isAdmin() ? 
                userService.findById(vaultDTO.getUserDTO().getId()) : 
                authService.getPrincipal();
		
		vault.setUser(userMapper.toEntity(user));

		vault.setId(null);
		try {			
			return vaultMapper.toDto(vaultRepository.save(vault));
		} catch (Exception e) {
			log.error("SERVICE - Dupicated Vault Name - CREATE");
			throw new DuplicatedValueException();
		}
	}

	/**
	 * Update a Vault by Data given by parameter
	 * @param vaultDTO
	 * @return VaultDTO
	 * @throws BadRequestException
	 * @throws NotFoundException
	 * @throws DuplicatedValueException
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */

	public VaultDTO update(VaultDTO vaultDTO) throws BadRequestException, NotFoundException ,DuplicatedValueException , InvalidTokenException, NotAuthorizedException{
		if(vaultDTO == null || vaultDTO.getId() == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - UPDATE");
			throw new BadRequestException();
		}
		
		if(!(authService.isAdmin() || isOwner(authService.getPrincipal(),vaultDTO))) {
			log.error("SERVICE - Vault Operation not allowed - UPDATE");
			throw new NotAuthorizedException();
		}

		Optional<Vault> maybeVault = vaultRepository.findById(vaultDTO.getId());
		if(maybeVault.isEmpty()) {
			log.error("SERVICE - Vault Not Found - UPDATE");
			throw new NotFoundException();
		}

		Vault newVault = vaultMapper.toEntity(vaultDTO);
		try {		
			newVault= vaultRepository.save(newVault);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SERVICE - Dupicated Vault Name - UPDATE");
			throw new DuplicatedValueException();
		}

		return vaultMapper.toDto(newVault);
	}

	/**
	 * Delete a Vault by id
	 * @param id
	 * @return VaultDTO
	 * @throws BadRequestException
	 * @throws InternalServerException
	 * @throws NotFoundException
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */
	public VaultDTO deleteById(Long id) throws BadRequestException, InternalServerException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		if(id == null) {
			log.error("SERVICE - Vault id is null - DELETE");
			throw new BadRequestException();
		}

		Optional<Vault> maybeVault = vaultRepository.findById(id);
		if(maybeVault.isPresent()) {
			Vault vault = maybeVault.get();

			if(authService.isAdmin() || isOwner(authService.getPrincipal(), vaultMapper.toDto(vault))) {
				vaultRepository.delete(vault);
			}
			else {
				log.error("SERVICE - Vault Operation not allowed - DELETE");
				throw new NotAuthorizedException();
			}

			if(!vaultRepository.existsById(id)) {
				return vaultMapper.toDto(vault);
			}
			else {
				log.error("SERVICE - Vault Not Deleted due to Unknown Error - DELETE");
				throw new InternalServerException();
			}
		}
		else {
			log.error("SERVICE - Vault Not Found - DELETE");
			throw new NotFoundException();
		}
	}

	/**
	 * Get all vaults by User Id given by parameter
	 * @param userId
	 * @return
	 * @throws BadRequestException
	 * @throws NotFoundException
	 * @throws InvalidTokenException 
	 * @throws NotAuthorizedException 
	 */
	public List<VaultDTO> getAllVaultByUserId(Long userId) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException {
		if(userId == null) {
			log.error("SERVICE - Vault id is null - GET VAUTLS BY USER");
			throw new BadRequestException();
		}

		UserDTO user = userService.findById(userId);

		if(!( authService.isAdmin() || authService.isPrincipal(userMapper.toEntity(user)))) {
			log.error("SERVICE - Vault Operation not allowed - GET VAULTS BY USER");
			throw new NotAuthorizedException();
		}

		List<VaultDTO> vaultList = 
				vaultRepository.getAllByUser(userMapper.toEntity(user))
				.stream()
				.map(vault -> vaultMapper.toDto(vault))
				.toList();
		return vaultList;
	}

	public List<VaultDTO> getAllVaultByPrincipal() throws BadRequestException, NotFoundException, InvalidTokenException{
		UserDTO user = authService.getPrincipal();

		List<VaultDTO> vaultList = 
				vaultRepository.getAllByUser(userMapper.toEntity(user))
				.stream()
				.map(vault -> vaultMapper.toDto(vault))
				.toList();
		return vaultList;
	}


	/**
	 * Updates the capital of a Vault based on the provided Operation.
	 * 
	 * This method takes an Operation object (either Expense or Revenue), validates it,
	 * and updates the capital of the corresponding Vault entity in the database.
	 * If the provided Operation is null, a {@link BadRequestException} is thrown.
	 * If the Vault corresponding to the Operation is not found, a {@link NotFoundException} is thrown.
	 * If the operation results in a duplicated value (e.g., trying to update with the same value),
	 * a {@link DuplicatedValueException} is thrown.
	 * 
	 * @param operation the Operation object containing data to update the Vault's capital
	 * @return true if the capital update operation is successful, false otherwise
	 * @throws BadRequestException if the provided Operation is null
	 * @throws NotFoundException if the Vault corresponding to the Operation is not found
	 * @throws DuplicatedValueException if the capital update operation results in a duplicated value
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     boolean updated = vaultService.updateCapital(expense);
	 *     if (updated) {
	 *         // handle success
	 *     } else {
	 *         // handle failure
	 *     }
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * } catch (DuplicatedValueException e) {
	 *     // handle duplicated value
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 */

	public boolean updateCapital(Operation operation) throws BadRequestException, NotFoundException, DuplicatedValueException, InvalidTokenException, NotAuthorizedException {
		if(operation == null) {
			log.error("SERVICE - Operation Data is null - OPERATION");
			throw new BadRequestException();
		}

		VaultDTO vaultDTO = findById(operation.getVault().getId());
		Vault vault = vaultMapper.toEntity(vaultDTO);

		if(operation instanceof Expense) {
			vault.setCapital(vault.getCapital().subtract(operation.getAmount()));
			update(vaultMapper.toDto(vault));
			return true;
		}

		if(operation instanceof Revenue) {
			vault.setCapital(vault.getCapital().add(operation.getAmount()));
			update(vaultMapper.toDto(vault));
			return true;
		}
		return false;
	}

	private boolean isDataValid(VaultDTO vaultDTO) {
		return (vaultDTO.getName() == null || 
				vaultDTO.getUserDTO() == null || 
				vaultDTO.getCapital() == null) 
				? false 
						: true;
	}

	private boolean isOwner(UserDTO userDTO , VaultDTO vaultDTO) throws BadRequestException {
		if(userDTO == null || vaultDTO == null) {
			throw new BadRequestException();
		}

		return vaultDTO.getUserDTO().equals(userDTO);
	}

}

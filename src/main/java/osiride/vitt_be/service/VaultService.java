package osiride.vitt_be.service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
import osiride.vitt_be.utils.VaultSummary;

@Slf4j
@Service
public class VaultService {

	private final VaultRepository vaultRepository;
	private final VaultMapper vaultMapper;
	private final UserMapper userMapper;
	private final UserService userService;
	private final AuthService authService;

	public VaultService(VaultRepository vaultRepository, 
			VaultMapper vaultMapper,
			UserMapper userMapper, 
			UserService userService, 
			AuthService authService) {
		this.vaultRepository = vaultRepository;
		this.vaultMapper = vaultMapper;
		this.userMapper = userMapper;
		this.userService = userService;
		this.authService = authService;
	}

	/**
	 * Retrieves all VaultDTO objects from the vault repository.
	 * 
	 * This method fetches all vault records from the repository and maps them to {@link VaultDTO} objects.
	 * Only users with admin privileges are authorized to perform this operation.
	 * 
	 * @return A list of VaultDTO objects representing all vaults in the repository.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 * @throws NotFoundException If the requested resource is not found.
	 * @throws BadRequestException If there is a problem with the request.
	 * @throws NotAuthorizedException If the user is not authorized to perform this operation.
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 */
	public List<VaultDTO> getAll() throws InvalidTokenException, NotFoundException, BadRequestException, NotAuthorizedException{
		return vaultRepository
				.findAll()
				.stream()
				.map(vault -> vaultMapper
						.toDto(vault))
				.toList(); 
	}

	/**
	 * Finds a VaultDTO by its ID.
	 * 
	 * This method retrieves a Vault entity from the repository based on the provided ID and maps it to a VaultDTO.
	 * If the ID is null, a {@link BadRequestException} is thrown.
	 * If no Vault is found with the provided ID, a {@link NotFoundException} is thrown.
	 * Only admins or owners of the Vault are authorized to perform this operation.
	 * 
	 * @param id The ID of the Vault to be retrieved.
	 * @return The VaultDTO corresponding to the provided ID.
	 * @throws BadRequestException If the provided ID is null.
	 * @throws NotFoundException If no Vault is found with the provided ID.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 * @throws NotAuthorizedException If the user is not authorized to perform this operation.
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
			if(isOwner(principal, vaultDTO) || authService.isAdmin() ) {
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

	public VaultDTO create(VaultDTO vaultDTO) throws BadRequestException, NotFoundException, DuplicatedValueException, InvalidTokenException{
		if(vaultDTO == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - CREATE");
			throw new BadRequestException();
		}

		Vault vault = vaultMapper.toEntity(vaultDTO);
		UserDTO user = authService.getPrincipal();
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
	 * <p>
	 * Updates a Vault entity based on the provided VaultDTO.
	 * 
	 * This method validates the provided VaultDTO, checks authorization, and updates the corresponding Vault entity in the repository.
	 * If the VaultDTO is null, its ID is null, or the data is invalid, a {@link BadRequestException} is thrown.
	 * If the user is not an admin or the owner of the Vault, a {@link NotAuthorizedException} is thrown.
	 * If no Vault is found with the provided ID, a {@link NotFoundException} is thrown.
	 * If the update operation results in a duplicated value, a {@link DuplicatedValueException} is thrown.
	 * </p>
	 * @param vaultDTO The VaultDTO containing the data to update the Vault.
	 * @return The updated VaultDTO.
	 * @throws BadRequestException If the provided VaultDTO is null, its ID is null, or the data is invalid.
	 * @throws NotFoundException If no Vault is found with the provided ID.
	 * @throws DuplicatedValueException If the update operation results in a duplicated value.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 * @throws NotAuthorizedException If the user is not authorized to perform this operation.
	 */
	public VaultDTO update(VaultDTO vaultDTO) throws BadRequestException, NotFoundException ,DuplicatedValueException , InvalidTokenException, NotAuthorizedException{
		if(vaultDTO == null || vaultDTO.getId() == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - UPDATE");
			throw new BadRequestException();
		}

		if(isOwner(authService.getPrincipal(),vaultDTO) || authService.isAdmin()) {

			Optional<Vault> maybeVault = vaultRepository.findById(vaultDTO.getId());
			if(maybeVault.isEmpty()) {
				log.error("SERVICE - Vault Not Found - UPDATE");
				throw new NotFoundException();
			}

			Vault newVault = vaultMapper.toEntity(vaultDTO);
			try {		
				newVault= vaultRepository.save(newVault);
			} catch (Exception e) {
				log.error("SERVICE - Duplicated Vault Name - UPDATE");
				throw new DuplicatedValueException();
			}

			return vaultMapper.toDto(newVault);
		}
		else {
			log.error("SERVICE - Vault Operation not allowed - UPDATE");
			throw new NotAuthorizedException();
		}
	}

	/**
	 * Deletes a Vault entity based on the provided ID.
	 * 
	 * This method checks the validity of the provided ID, verifies authorization, and deletes the corresponding Vault entity from the repository.
	 * If the ID is null, a {@link BadRequestException} is thrown.
	 * If no Vault is found with the provided ID, a {@link NotFoundException} is thrown.
	 * Only admins or owners of the Vault are authorized to perform this operation.
	 * If the deletion fails due to an unknown error, an {@link InternalServerException} is thrown.
	 * 
	 * @param id The ID of the Vault to be deleted.
	 * @return The VaultDTO of the deleted Vault.
	 * @throws BadRequestException If the provided ID is null.
	 * @throws InternalServerException If the Vault could not be deleted due to an unknown error.
	 * @throws NotFoundException If no Vault is found with the provided ID.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 * @throws NotAuthorizedException If the user is not authorized to perform this operation.
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
	 * Retrieves all VaultDTO objects for a specific user based on the provided user ID.
	 * 
	 * This method fetches all vault records associated with a given user from the repository and maps them to VaultDTO objects.
	 * If the user ID is null, a {@link BadRequestException} is thrown.
	 * If the user is not found, a {@link NotFoundException} is thrown.
	 * Only admins or the user themselves are authorized to perform this operation.
	 * 
	 * @param userId The ID of the user whose vaults are to be retrieved.
	 * @return A list of VaultDTO objects representing all vaults associated with the user.
	 * @throws BadRequestException If the provided user ID is null.
	 * @throws NotFoundException If no user is found with the provided ID.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 * @throws NotAuthorizedException If the user is not authorized to perform this operation. 
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
				vaultRepository.getAllByUser(user.getId())
				.stream()
				.map(vault -> vaultMapper.toDto(vault))
				.toList();
		return vaultList;
	}

	public List<VaultDTO> getAllVaultByPrincipal() throws BadRequestException, NotFoundException, InvalidTokenException{
		UserDTO user = authService.getPrincipal();

		List<VaultDTO> vaultList = 
				vaultRepository.getAllByUser(user.getId())
				.stream()
				.map(vault -> vaultMapper.toDto(vault))
				.toList().stream()
			    .sorted(Comparator.comparing(VaultDTO::getCapital).reversed())
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
	
	/**
	 * Retrieves the vault report summary for the currently authenticated user.
	 * 
	 * This method interacts with the authentication service to fetch the principal (authenticated user),
	 * then queries the vault repository to get the vault report associated with the user's ID.
	 * 
	 * @return VaultSummary - A summary report containing details about the user's vaults.
	 * 
	 * @throws BadRequestException if the request is malformed or contains invalid parameters.
	 * @throws InvalidTokenException if the user's authentication token is invalid or expired.
	 * @throws NotFoundException if no vault report is found for the given user.
	 */
	public List<VaultSummary>  getPrincipalVaultReport() throws BadRequestException, InvalidTokenException, NotFoundException {
		
		UserDTO principal = authService.getPrincipal();
		
		List<VaultSummary> summary =  vaultRepository.getVaultsReport(principal.getId());
		return summary;
	}
	

	/**
	 * Retrieves all VaultDTO objects for the currently authenticated principal user.
	 * 
	 * This method fetches all vault records associated with the currently authenticated user from the repository and maps them to VaultDTO objects.
	 * 
	 * @return A list of VaultDTO objects representing all vaults associated with the authenticated user.
	 * @throws BadRequestException If there is an issue with the request.
	 * @throws NotFoundException If the authenticated user is not found.
	 * @throws InvalidTokenException If the authentication token is invalid.
	 */
	private boolean isDataValid(VaultDTO vaultDTO) {
		return (vaultDTO.getName() == null || 
				vaultDTO.getUserDTO() == null || 
				vaultDTO.getCapital() == null || 
				vaultDTO.getImage() == null) 
				? false 
						: true;
	}


	/**
	 * Checks if the given user is the owner of the given vault.
	 * 
	 * This method validates that both the user and the vault are not null, and then checks if the user is the owner of the vault.
	 * If either the user or the vault is null, a {@link BadRequestException} is thrown.
	 * 
	 * @param userDTO The UserDTO object representing the user.
	 * @param vaultDTO The VaultDTO object representing the vault.
	 * @return true if the user is the owner of the vault, false otherwise.
	 * @throws BadRequestException If the user or vault is null.
	 */
	private boolean isOwner(UserDTO userDTO , VaultDTO vaultDTO) throws BadRequestException {
		if(userDTO == null || vaultDTO == null) {
			throw new BadRequestException();
		}

		return vaultDTO.getUserDTO().getId().equals(userDTO.getId());
	}

}

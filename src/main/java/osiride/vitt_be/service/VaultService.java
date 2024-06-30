package osiride.vitt_be.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
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

	/**
	 * Get all vaults in database
	 * @return List<VaultDTO>
	 */
	public List<VaultDTO> getAll(){
		return vaultRepository.findAll().stream().map(vault -> vaultMapper.toDto(vault)).toList(); 
	}

	/**
	 * Find a vault by Id
	 * @param id
	 * @return VaultDTO
	 * @throws BadRequestException
	 */
	public VaultDTO findById(Long id) throws BadRequestException, NotFoundException{
		if(id == null) {
			log.error("SERVICE - Vault id is null - FIND ONE");
			throw new BadRequestException();
		}
		Optional<Vault> maybeVault = vaultRepository.findById(id);

		if(maybeVault.isPresent()) {
			return vaultMapper.toDto(maybeVault.get());
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
	 * @throws SQLException
	 */
	public VaultDTO create(VaultDTO vaultDTO) throws BadRequestException, NotFoundException{
		if(vaultDTO == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - CREATE");
			throw new BadRequestException();
		}

		Vault vault = vaultMapper.toEntity(vaultDTO);
		vault.setUser(userMapper.toEntity(userService.findById(vaultDTO.getUserDTO().getId())));
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
	 */
	public VaultDTO update(VaultDTO vaultDTO) throws BadRequestException, NotFoundException,DuplicatedValueException{
		if(vaultDTO == null || vaultDTO.getId() == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data given is null - UPDATE");
			throw new BadRequestException();
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
	 */
	public VaultDTO deleteById(Long id) throws BadRequestException, InternalServerException, NotFoundException{
		if(id == null) {
			log.error("SERVICE - Vault id is null - DELETE");
			throw new BadRequestException();
		}

		Optional<Vault> maybeVault = vaultRepository.findById(id);
		if(maybeVault.isPresent()) {
			Vault vault = maybeVault.get();
			vaultRepository.delete(vault);

			if(!vaultRepository.existsById(id)) {
				return vaultMapper.toDto(vault);
			}
			else {
				log.error("SERVICE - Vault Not Deleted cause by Unknown Error - DELETE");
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
	 */
	public List<VaultDTO> getAllVaultByUserId(Long userId) throws BadRequestException, NotFoundException{
		if(userId == null) {
			log.error("SERVICE - Vault id is null - DELETE");
			throw new BadRequestException();
		}
		
		UserDTO user = userService.findById(userId);
		List<VaultDTO> vaultList = 
				vaultRepository.getAllByUser(userMapper.toEntity(user))
				.stream()
				.map(vault -> vaultMapper.toDto(vault))
				.toList();
		return vaultList;
	}

	public boolean isDataValid(VaultDTO vaultDTO) {
		return (vaultDTO.getName() == null || 
				vaultDTO.getUserDTO() == null || 
				vaultDTO.getCapital() == null) 
				? false 
						: true;
	}

}

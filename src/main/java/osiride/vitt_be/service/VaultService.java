package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.VaultMapper;
import osiride.vitt_be.repository.VaultRepository;

@Slf4j
@Service
public class VaultService {

	@Autowired
	private VaultRepository vaultRepository;

	@Autowired
	private VaultMapper vaultMapper;

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
	 */
	public VaultDTO create(VaultDTO vaultDTO) throws BadRequestException {
		if(vaultDTO == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data fiven is null - CREATE");
			throw new BadRequestException();
		}

		Vault vault = vaultMapper.toEntity(vaultDTO);
		vault.setId(null);
		return vaultMapper.toDto(vaultRepository.save(vault));
	}

	/**
	 *  Update a Vault by Data given by parameter
	 * @param vaultDTO
	 * @return VaultDTO
	 * @throws BadRequestException
	 * @throws NotFoundException
	 */
	public VaultDTO update(VaultDTO vaultDTO) throws BadRequestException, NotFoundException{
		if(vaultDTO == null || vaultDTO.getId() == null || !isDataValid(vaultDTO)) {
			log.error("SERVICE - Vault Data fiven is null - UPDATE");
			throw new BadRequestException();
		}

		Optional<Vault> maybeVault = vaultRepository.findById(vaultDTO.getId());
		if(maybeVault.isEmpty()) {
			log.error("SERVICE - Vault Not Found - UPDATE");
			throw new NotFoundException();
		}

		Vault newVault = vaultMapper.toEntity(vaultDTO);
		newVault= vaultRepository.save(newVault);
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
	public VaultDTO delete(Long id) throws BadRequestException, InternalServerException, NotFoundException{
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
	
	//IMPLEMENTARE QUESTO METODO CON UNA  BELLISSIMA QUERY JPQL IN VAULTREPOSITORY
	public List<VaultDTO> getAllVaultByUserId(){
		throw new NotImplementedException();
	}

	public boolean isDataValid(VaultDTO vaultDTO) {
		return (vaultDTO.getName() == null || 
				vaultDTO.getUserId() == null || 
				vaultDTO.getCapital() == null) 
				? false 
						: true;
	}

}

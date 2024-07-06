package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.dto.CategoryDTO;
import osiride.vitt_be.dto.RevenueDTO;
import osiride.vitt_be.dto.ThirdPartyDTO;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.error.OperationNotPermittedException;
import osiride.vitt_be.mapper.RevenueMapper;
import osiride.vitt_be.repository.RevenueRepository;

@Slf4j
@Service
public class RevenueService {

	@Autowired
	private RevenueRepository revenueRepository;

	@Autowired
	private RevenueMapper revenueMapper;

	@Autowired 
	private VaultService vaultService;

	@Autowired 
	private CategoryService categoryService;

	@Autowired
	private ThirdPartyService thirdPartyService;


	/**
	 * Retrieves all Revenues from the database.
	 * 
	 * This method fetches all Revenue entities from the database, maps them to RevenueDTOs,
	 * and returns them as a list. The method logs the operation.
	 * 
	 * @return a list of RevenueDTOs representing all Revenues in the database
	 * 
	 * @example
	 * {@code
	 * List<RevenueDTO> revenues = revenueService.getAll();
	 * for (RevenueDTO revenue : revenues) {
	 *     // process each revenue
	 * }
	 * }
	 * 
	 * @see RevenueDTO
	 * @see RevenueRepository
	 * @see RevenueMapper
	 * 
	 * @implNote This method uses Java Streams to map entities to DTOs.
	 * 
	 * @author Simone
	 */
	public List<RevenueDTO> getAll() {
		log.info("SERVICE - Get All Revenues from DB - READ ALL");
		return revenueRepository.findAll()
				.stream()
				.map(revenue -> revenueMapper.toDto(revenue))
				.toList();
	}

	/**
	 * Retrieves a Revenue by its ID.
	 * 
	 * This method fetches a Revenue entity from the database using the provided ID and maps it to a RevenueDTO.
	 * If the provided ID is null, a {@link BadRequestException} is thrown.
	 * If no Revenue is found with the given ID, a {@link NotFoundException} is thrown.
	 * 
	 * @param id the ID of the Revenue to retrieve
	 * @return the RevenueDTO representing the retrieved Revenue
	 * @throws BadRequestException if the provided ID is null
	 * @throws NotFoundException if no Revenue is found with the given ID
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     RevenueDTO revenue = revenueService.findById(1L);
	 *     // process revenue
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @see RevenueDTO
	 * @see RevenueRepository
	 * @see RevenueMapper
	 * 
	 * @implNote Ensure that the revenueRepository and revenueMapper are properly injected and not null.
	 * 
	 * @author Simone
	 */
	public RevenueDTO findById(Long id) throws BadRequestException, NotFoundException {
		if(id == null) {
			log.error("SERVICE - Revenue id is null- FIND ONE");
			throw new BadRequestException();
		}

		Optional<Revenue> maybeRevenue = revenueRepository.findById(id);
		if(maybeRevenue.isPresent()) {
			log.info("SERVICE - Found a Revenue on DB - FIND ONE");
			return revenueMapper.toDto(maybeRevenue.get());
		} 
		else {
			log.error("SERVICE - Revenue not found - FIND ONE");
			throw new NotFoundException();
		}
	}

	/**
	 * Creates a new Revenue.
	 * 
	 * Validates the provided RevenueDTO, retrieves related entities, and saves a new Revenue entity
	 * in the database. Updates the corresponding Vault's capital if the revenue is successfully saved.
	 * 
	 * @param revenueDTO the RevenueDTO containing the data to create a new Revenue
	 * @return the created RevenueDTO
	 * @throws BadRequestException if the provided data is invalid
	 * @throws NotFoundException if any related entity is not found
	 * @throws OperationNotPermittedException if the Vault's capital update fails
	 * 
	 * @example
	 * {@code
	 * try {
	 *     RevenueDTO newRevenue = revenueService.create(revenueDTO);
	 * } catch (BadRequestException | NotFoundException | OperationNotPermittedException e) {
	 *     // handle exception
	 * }
	 * }
	 * 
	 * @see RevenueDTO
	 * @see VaultService#updateCapital
	 * @see RevenueRepository
	 * @see RevenueMapper
	 * @see VaultService
	 * @see CategoryService
	 * @see ThirdPartyService
	 * 
	 * @implNote Ensure the services and repositories are properly injected.
	 * 
	 * @author Simone
	 */
	public RevenueDTO create(RevenueDTO revenueDTO) throws BadRequestException, NotFoundException, OperationNotPermittedException {
		if(revenueDTO == null || !isDataValid(revenueDTO)) {
			log.error("SERVICE - Revenue data is invalid - CREATE");
			throw new BadRequestException();
		}

		VaultDTO vaultDTO = vaultService.findById(revenueDTO.getVaultDTO().getId());
		revenueDTO.setVaultDTO(vaultDTO);

		CategoryDTO categoryDTO = categoryService.findById(revenueDTO.getCategoryDTO().getId());
		revenueDTO.setCategoryDTO(categoryDTO);

		ThirdPartyDTO thirdPartyDTO = thirdPartyService.findById(revenueDTO.getThirdPartyDTO().getId());
		revenueDTO.setThirdPartyDTO(thirdPartyDTO);

		Revenue revenue = revenueMapper.toEntity(revenueDTO);
		if(!revenue.getIsProgrammed()) {
			revenue.setEndDate(revenue.getStartDate());
		}else if(!revenue.getStartDate().isBefore(revenue.getEndDate())) {
			log.error("SERVICE - Revenue Date is invalid - CREATE");
			throw new BadRequestException();
		}
		revenue.setId(null);
		revenue = revenueRepository.save(revenue);

		boolean operationResult = false;
		try {
			operationResult = vaultService.updateCapital(revenue);
			if(operationResult) {
				return revenueMapper.toDto(revenue);
			}
			else {
				throw new OperationNotPermittedException();
			}
		} catch (DuplicatedValueException e) {
			throw new OperationNotPermittedException();
		}
	}


	/**
	 * Updates an existing Revenue.
	 * 
	 * Validates the provided RevenueDTO, checks if the revenue exists, and updates it in the database.
	 * Ensures that the Vault ID remains unchanged and the dates are valid.
	 * 
	 * @param revenueDTO the RevenueDTO containing the data to update the Revenue
	 * @return the updated RevenueDTO
	 * @throws BadRequestException if the provided data is invalid
	 * @throws OperationNotPermittedException if the Vault ID changes
	 * @throws NotFoundException if the revenue does not exist
	 * 
	 * @example
	 * {@code
	 * try {
	 *     RevenueDTO updatedRevenue = revenueService.update(revenueDTO);
	 * } catch (BadRequestException | NotFoundException | OperationNotPermittedException e) {
	 *     // handle exception
	 * }
	 * }
	 * 
	 * @see RevenueDTO
	 * @see RevenueRepository
	 * @see RevenueMapper
	 * @see VaultService
	 * 
	 * @implNote Ensure the services and repositories are properly injected.
	 * 
	 * @author Simone
	 */
	public RevenueDTO update(RevenueDTO revenueDTO) throws BadRequestException, OperationNotPermittedException, NotFoundException {
		if(revenueDTO == null || revenueDTO.getId() == null || !isDataValid(revenueDTO) ) {
			log.error("SERVICE - Revenue data is invalid - UPDATE");
			throw new BadRequestException();
		}

		RevenueDTO oldRevenue = findById(revenueDTO.getId());


		if(oldRevenue.getVaultDTO().getId() != revenueDTO.getVaultDTO().getId()) {
			log.error("SERVICE - Revenue data is invalid - UPDATE");
			throw new OperationNotPermittedException();
		} else if(!revenueDTO.getStartDate().isBefore(revenueDTO.getEndDate())) {
			log.error("SERVICE - Revenue Date is invalid - UPDATE");
			throw new BadRequestException();
		}

		Revenue revenue = revenueMapper.toEntity(revenueDTO);
		return revenueMapper.toDto(revenue);
	}


	/**
	 * Deletes a Revenue by its ID.
	 * 
	 * Finds the Revenue by its ID, deletes it from the database, and verifies the deletion.
	 * If the provided ID is invalid or the Revenue does not exist, an exception is thrown.
	 * 
	 * @param id the ID of the Revenue to delete
	 * @return the deleted RevenueDTO
	 * @throws BadRequestException if the provided ID is invalid
	 * @throws NotFoundException if no Revenue is found with the given ID
	 * @throws InternalServerException if the deletion operation fails
	 * 
	 * @example
	 * {@code
	 * try {
	 *     RevenueDTO deletedRevenue = revenueService.deleteById(1L);
	 * } catch (BadRequestException | NotFoundException | InternalServerException e) {
	 *     // handle exception
	 * }
	 * }
	 * 
	 * @see RevenueDTO
	 * @see RevenueRepository
	 * @see RevenueMapper
	 * 
	 * @implNote Ensure the services and repositories are properly injected.
	 * 
	 * @author Simone
	 */
	public RevenueDTO deleteById(Long id) throws BadRequestException, NotFoundException, InternalServerException{
		RevenueDTO revenueDTO = findById(id);
		revenueRepository.deleteById(id);

		if(revenueRepository.existsById(id)) {
			return revenueDTO;
		}
		else {
			log.error("SERVICE - Revenue Not Deleted due to Unknown Error - DELETE");
			throw new InternalServerException();
		}
	}

	private boolean isDataValid(RevenueDTO object) {
		if (object == null) {
			return false;
		}

		return object.getCausal() != null &&
				object.getAmount() != null &&
				object.getIsProgrammed() != null &&
				object.getStartDate() != null &&
				object.getEndDate() != null &&
				object.getVaultDTO() != null &&
				object.getCategoryDTO() != null &&
				object.getThirdPartyDTO() != null;
	}

}

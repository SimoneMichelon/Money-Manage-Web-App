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

	public List<RevenueDTO> getAll() {
		log.info("SERVICE - Get All Revenues from DB - READ ALL");
		return revenueRepository.findAll()
				.stream()
				.map(revenue -> revenueMapper.toDto(revenue))
				.toList();
	}

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

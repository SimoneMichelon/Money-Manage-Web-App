package osiride.vitt_be.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.dto.ExpenseDTO;
import osiride.vitt_be.dto.RevenueDTO;
import osiride.vitt_be.utils.OperationDTO;
import osiride.vitt_be.utils.TransactionType;

@Component
public class OperationMapper {

	@Autowired
	private VaultMapper vaultMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ThirdPartyMapper thirdPartyMapper;

	public OperationDTO toOperationDTO(ExpenseDTO dto) {
		if (dto == null) {
			return null;
		}
		OperationDTO entity = new OperationDTO();
		entity.setId(dto.getId());
		entity.setCausal(dto.getCausal());
		entity.setAmount(dto.getAmount());
		entity.setIsProgrammed(dto.getIsProgrammed());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setVault(vaultMapper.toEntity(dto.getVaultDTO()));
		entity.setCategory(categoryMapper.toEntity(dto.getCategoryDTO()));
		entity.setThirdParty(thirdPartyMapper.toEntity(dto.getThirdPartyDTO()));
		entity.setType(TransactionType.EXPENSE);
		return entity;
	}

	public OperationDTO toOperationDTO(RevenueDTO dto) {
		if (dto == null) {
			return null;
		}
		OperationDTO entity = new OperationDTO();
		entity.setId(dto.getId());
		entity.setCausal(dto.getCausal());
		entity.setAmount(dto.getAmount());
		entity.setIsProgrammed(dto.getIsProgrammed());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setVault(vaultMapper.toEntity(dto.getVaultDTO()));
		entity.setCategory(categoryMapper.toEntity(dto.getCategoryDTO()));
		entity.setThirdParty(thirdPartyMapper.toEntity(dto.getThirdPartyDTO()));
		entity.setType(TransactionType.REVENUE);
		return entity;
	}


}

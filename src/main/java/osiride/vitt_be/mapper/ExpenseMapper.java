package osiride.vitt_be.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.dto.ExpenseDTO;

@Component
public class ExpenseMapper {
	
	@Autowired
	private VaultMapper vaultMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ThirdPartyMapper thirdPartyMapper;
	
	
	  public ExpenseDTO toDto(Expense entity) {
	        if (entity == null) {
	            return null;
	        }
	        ExpenseDTO dto = new ExpenseDTO();
	        dto.setId(entity.getId());
	        dto.setCausal(entity.getCausal());
	        dto.setAmount(entity.getAmount());
	        dto.setIsProgrammed(entity.getIsProgrammed());
	        dto.setStartDate(entity.getStartDate());
	        dto.setEndDate(entity.getEndDate());
	        dto.setVaultDTO(vaultMapper.toDto(entity.getVault()));
	        dto.setCategoryDTO(categoryMapper.toDto(entity.getCategory()));
	        dto.setThirdPartyDTO(thirdPartyMapper.toDto(entity.getThirdPartys()));
	        return dto;
	    }

	    public Expense toEntity(ExpenseDTO dto) {
	        if (dto == null) {
	            return null;
	        }
	        Expense entity = new Expense();
	        entity.setId(dto.getId());
	        entity.setCausal(dto.getCausal());
	        entity.setAmount(dto.getAmount());
	        entity.setIsProgrammed(dto.getIsProgrammed());
	        entity.setStartDate(dto.getStartDate());
	        entity.setEndDate(dto.getEndDate());
	        entity.setVault(vaultMapper.toEntity(dto.getVaultDTO()));
	        entity.setCategory(categoryMapper.toEntity(dto.getCategoryDTO()));
	        entity.setThirdPartys(thirdPartyMapper.toEntity(dto.getThirdPartyDTO()));
	        return entity;
	    }
}

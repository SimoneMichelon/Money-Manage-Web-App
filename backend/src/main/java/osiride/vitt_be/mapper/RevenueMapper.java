package osiride.vitt_be.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.dto.RevenueDTO;

@Component
public class RevenueMapper {
	
	@Autowired
	private VaultMapper vaultMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ThirdPartyMapper thirdPartyMapper;
	
	
	  public RevenueDTO toDto(Revenue entity) {
	        if (entity == null) {
	            return null;
	        }
	        RevenueDTO dto = new RevenueDTO();
	        dto.setId(entity.getId());
	        dto.setCausal(entity.getCausal());
	        dto.setAmount(entity.getAmount());
	        dto.setIsProgrammed(entity.getIsProgrammed());
	        dto.setStartDate(entity.getStartDate());
	        dto.setEndDate(entity.getEndDate());
	        dto.setVaultDTO(vaultMapper.toDto(entity.getVault()));
	        dto.setCategoryDTO(categoryMapper.toDto(entity.getCategory()));
	        dto.setThirdPartyDTO(thirdPartyMapper.toDto(entity.getThirdParty()));
	        return dto;
	    }

	    public Revenue toEntity(RevenueDTO dto) {
	        if (dto == null) {
	            return null;
	        }
	        Revenue entity = new Revenue();
	        entity.setId(dto.getId());
	        entity.setCausal(dto.getCausal());
	        entity.setAmount(dto.getAmount());
	        entity.setIsProgrammed(dto.getIsProgrammed());
	        entity.setStartDate(dto.getStartDate());
	        entity.setEndDate(dto.getEndDate());
	        entity.setVault(vaultMapper.toEntity(dto.getVaultDTO()));
	        entity.setCategory(categoryMapper.toEntity(dto.getCategoryDTO()));
	        entity.setThirdParty(thirdPartyMapper.toEntity(dto.getThirdPartyDTO()));
	        return entity;
	    }
}

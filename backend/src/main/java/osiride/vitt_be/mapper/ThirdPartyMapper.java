package osiride.vitt_be.mapper;

import osiride.vitt_be.domain.ThirdParty;
import osiride.vitt_be.dto.ThirdPartyDTO;
import org.springframework.stereotype.Component;

@Component
public class ThirdPartyMapper {

    public ThirdPartyDTO toDto(ThirdParty entity) {
        if (entity == null) {
            return null;
        }
        ThirdPartyDTO dto = new ThirdPartyDTO();
        dto.setId(entity.getId());
        dto.setThirdPartyName(entity.getThirdPartyName());
        return dto;
    }

    public ThirdParty toEntity(ThirdPartyDTO dto) {
        if (dto == null) {
            return null;
        }
        ThirdParty entity = new ThirdParty(); 
        entity.setId(dto.getId());
        entity.setThirdPartyName(dto.getThirdPartyName());
        return entity;
    }
}
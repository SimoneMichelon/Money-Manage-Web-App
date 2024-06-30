package osiride.vitt_be.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.dto.VaultDTO;

@Component
public class VaultMapper {
	
	@Autowired
	private UserMapper userMapper;

	public VaultDTO toDto(Vault object) {
		if(object == null) {
			return null;
		}
		VaultDTO dto = new VaultDTO();
	    dto.setId(object.getId());
	    dto.setName(object.getName());
	    dto.setUserDTO(userMapper.toDto(object.getUser()));
	    dto.setCapital(object.getCapital());
	    return dto;
	}
	
	public Vault toEntity(VaultDTO object) {
		if(object == null) {
			return null;
		}
		
	    Vault entity = new Vault();
	    entity.setId(object.getId());
	    entity.setName(object.getName());
	    entity.setUser(userMapper.toEntity(object.getUserDTO()));
	    entity.setCapital(object.getCapital());
	    return entity;
	}
}

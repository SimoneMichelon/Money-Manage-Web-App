package osiride.vitt_be.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.dto.CredentialDTO;

@Component
public class CredentialMapper {
	
	@Autowired
	private UserMapper userMapper;

	public CredentialDTO toDto(Credential object) {
		if(object == null) {
			return null;
		}
		
		CredentialDTO dto = new CredentialDTO();
		dto.setId(object.getId());
		dto.setEmail(object.getEmail());
		dto.setPassword(object.getPassword());
		dto.setUserDTO(userMapper.toDto(object.getUser()));
	    return dto;
	}
	
	public Credential toEntity(CredentialDTO object) {
		if(object == null) {
			return null;
		}
		
		Credential entity = new Credential();
		entity.setId(object.getId());
		entity.setEmail(object.getEmail());
		entity.setPassword(object.getPassword());
		entity.setUser(userMapper.toEntity(object.getUserDTO()));
	    return entity;
	}
}

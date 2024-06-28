package osiride.vitt_be.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.User;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.repository.UserRepository;

@Component
public class VaultMapper {
	
	@Autowired
	private UserRepository userRepository;

	public VaultDTO toDto(Vault object) {
		if(object == null) {
			return null;
		}
		VaultDTO dto = new VaultDTO();
	    dto.setId(object.getId());
	    dto.setName(object.getName());
	    dto.setUserId(object.getUser().getId());
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
	    Optional<User> maybeUser = userRepository.findById(object.getUserId());
	    entity.setUser(maybeUser.isPresent() ? maybeUser.get() : null);
	    entity.setCapital(object.getCapital());
	    return entity;
	}
}

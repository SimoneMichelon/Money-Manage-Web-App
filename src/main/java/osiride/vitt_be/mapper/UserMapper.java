package osiride.vitt_be.mapper;

import org.springframework.stereotype.Component;

import osiride.vitt_be.domain.User;
import osiride.vitt_be.dto.UserDTO;

@Component
public class UserMapper{

	public UserDTO toDto(User object) {
		if(object == null) {
			return null;
		}
	    UserDTO dto = new UserDTO();
	    dto.setId(object.getId());
	    dto.setFirstName(object.getFirstName());
	    dto.setLastName(object.getLastName());
	    dto.setDob(object.getDob());
	    dto.setImgProfile(object.getImgProfile());
	    return dto;
	}
	
	public User toEntity(UserDTO object) {
		if(object == null) {
			return null;
		}
		
	    User entity = new User();
	    entity.setId(object.getId());
	    entity.setFirstName(object.getFirstName());
	    entity.setLastName(object.getLastName());
	    entity.setDob(object.getDob());
	    entity.setImgProfile(object.getImgProfile());
	    return entity;
	}


}

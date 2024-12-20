package osiride.vitt_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO {

	private Long id;
	private String email;
	private String password;
	private UserDTO userDTO;
}

package osiride.vitt_be.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.dto.CredentialDTO;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.CredentialMapper;
import osiride.vitt_be.repository.CredentialRepository;

@Slf4j
@Service
public class CredentialService {

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	private CredentialMapper credentialMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	public List<CredentialDTO> getAll(){
		return credentialRepository.findAll()
				.stream()
				.map(credential -> credentialMapper
						.toDto(credential))
				.toList();
	}

	public boolean existsByEmail(String email) throws BadRequestException{
		if(email == null) {
			throw new BadRequestException();
		}
		
		if(credentialRepository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	public Credential findByEmail(String email) throws NotFoundException{
		Optional<Credential> maybeCredential = credentialRepository.findByEmail(email);

		if(maybeCredential.isEmpty()) {
			throw new NotFoundException();
		}
		return maybeCredential.get();
	} 

	public CredentialDTO create(CredentialDTO credentialDTO) throws BadRequestException, DuplicatedValueException, NotFoundException {
		if(credentialDTO == null || 
				!isDataValid(credentialDTO)) {
			log.error("SERVICE - Credential data is invalid - CREATE");
			throw new BadRequestException();
		}

		if(existsByEmail(credentialDTO.getEmail())) {
			log.error("SERVICE - Email already exists : {} - CREATE", credentialDTO.getEmail());
			throw new DuplicatedValueException();
		}
		
		UserDTO userDTO = userService.create(credentialDTO.getUserDTO());
		credentialDTO.setUserDTO(userDTO);
		Credential credential = credentialMapper.toEntity(credentialDTO);
		
		credential.setId(null);
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));

		return credentialMapper.toDto(credentialRepository.save(credential));
	}

	private boolean isDataValid(CredentialDTO object) {
		return (object.getEmail() == null ||
				object.getPassword() == null || 
				object.getUserDTO() == null) 
				? false 
						: true;
	}

}

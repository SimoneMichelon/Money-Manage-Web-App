package osiride.vitt_be.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.service.VaultService;

@Slf4j
@RestController
@RequestMapping("/api/vault-management")
public class VaultController {
	
	@Autowired 
	private VaultService vaultService;
	
	@Operation(summary = "Get all Vault")
	@GetMapping(value = "/vaults", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VaultDTO>> getAllVaults(){
		List<VaultDTO> result = vaultService.getAll();
		log.info("REST - Vault's list size : {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	

}

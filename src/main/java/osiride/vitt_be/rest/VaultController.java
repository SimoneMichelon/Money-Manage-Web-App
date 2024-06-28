package osiride.vitt_be.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.VaultService;

@Slf4j
@RestController
@RequestMapping("/api/vault-management")
public class VaultController {

	@Autowired 
	private VaultService vaultService;

	@Operation(summary = "Get all Vault", description = "Get all vault ")
	@GetMapping(value = "/vaults", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VaultDTO>> getAllVaults(){
		List<VaultDTO> result = vaultService.getAll();
		log.info("REST - Vault's list size : {} - READ ALL", result.size());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@Operation(summary = "Find vault by id")
	@GetMapping(value = "/vaults/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VaultDTO> getVaultById(@PathVariable Long id){
		try {			
			VaultDTO result = vaultService.findById(id);
			log.info("REST - Vault found : {} - READ ONE", result);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (BadRequestException e) {
			log.error("REST - Bad information given - READ ONE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			log.error("REST - Vault NOT found - READ ONE");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Create vault", description = "Creation vault by given data")
	@PostMapping(value = "/vaults", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VaultDTO> createVault(@RequestBody VaultDTO vaultDTO){
		try {			
			VaultDTO result = vaultService.create(vaultDTO);
			log.info("REST - Vault created - CREATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch(BadRequestException e) {
			log.error("REST - Bad information given - CREATE");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Update vault", description = "Update vault by given data")
	@PutMapping(value = "/vaults", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<VaultDTO> updateVault(@RequestBody VaultDTO vaultDTO){
		try {
			VaultDTO result = vaultService.update(vaultDTO);
			log.info("REST - Vault Updated - UPDATE");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (BadRequestException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}






}

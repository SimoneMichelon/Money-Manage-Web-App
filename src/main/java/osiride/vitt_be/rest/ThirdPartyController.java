package osiride.vitt_be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.dto.ThirdPartyDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.service.ThirdPartyService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/third-party-management")
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Operation(summary = "Get all Third Parties", description = "Get all Third Parties")
    @GetMapping(value = "/third-parties", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThirdPartyDTO>> getAllThirdParties() {
        List<ThirdPartyDTO> result = thirdPartyService.getAll();
        log.info("REST - Third Party list size: {} - READ ALL", result.size());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Find Third Party by ID", description = "Get a Third Party by ID")
    @GetMapping(value = "/third-parties/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThirdPartyDTO> getThirdPartyById(@PathVariable Long id) {
        try {
            ThirdPartyDTO result = thirdPartyService.findById(id);
            log.info("REST - Third Party found: {} - READ ONE", result);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (BadRequestException e) {
            log.error("REST - Bad information given - READ ONE");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotFoundException e) {
            log.error("REST - Third Party not found - READ ONE");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Create Third Party", description = "Create a Third Party")
    @PostMapping(value = "/third-parties", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThirdPartyDTO> createThirdParty(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        try {
            ThirdPartyDTO result = thirdPartyService.create(thirdPartyDTO);
            log.info("REST - Third Party created - CREATE");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (BadRequestException e) {
            log.error("REST - Bad information given - CREATE");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DuplicatedValueException e) {
            log.error("REST - Duplicated Third Party Name Error - CREATE");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Update Third Party", description = "Update a Third Party")
    @PutMapping(value = "/third-parties", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThirdPartyDTO> updateThirdParty(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        try {
            ThirdPartyDTO result = thirdPartyService.update(thirdPartyDTO);
            log.info("REST - Third Party updated - UPDATE");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (BadRequestException e) {
            log.error("REST - Bad information given - UPDATE");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotFoundException e) {
            log.error("REST - Third Party not found - UPDATE");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DuplicatedValueException e) {
            log.error("REST - Duplicated Third Party Name Error - UPDATE");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Delete Third Party by ID", description = "Delete a Third Party by ID")
    @DeleteMapping(value = "/third-parties/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThirdPartyDTO> deleteThirdPartyById(@PathVariable Long id) {
        try {
            ThirdPartyDTO result = thirdPartyService.deleteById(id);
            log.info("REST - Third Party deleted, ID: {} - DELETE", id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (BadRequestException e) {
            log.error("REST - Bad information given - DELETE");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotFoundException e) {
            log.error("REST - Third Party not found - DELETE");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InternalServerException e) {
            log.error("REST - Error on deleting Third Party - DELETE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

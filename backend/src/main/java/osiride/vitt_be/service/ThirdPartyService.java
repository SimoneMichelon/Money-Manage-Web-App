package osiride.vitt_be.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.ThirdParty;
import osiride.vitt_be.dto.ThirdPartyDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.ThirdPartyMapper;
import osiride.vitt_be.repository.ThirdPartyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThirdPartyService {

	private final ThirdPartyRepository thirdPartyRepository;
    private final ThirdPartyMapper thirdPartyMapper;

    public ThirdPartyService(ThirdPartyRepository thirdPartyRepository, ThirdPartyMapper thirdPartyMapper) {
        this.thirdPartyRepository = thirdPartyRepository;
        this.thirdPartyMapper = thirdPartyMapper;
    }

    /**
     * Retrieves all third parties from the database.
     *
     * @return List of ThirdPartyDTOs
     */
    public List<ThirdPartyDTO> getAll() {
        log.info("SERVICE - Get All Third Parties from DB - READ ALL");
        return thirdPartyRepository.findAll().stream()
                .map(thirdPartyMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds a third party by its ID.
     *
     * @param id ID of the third party to find
     * @return ThirdPartyDTO corresponding to the found third party
     * @throws BadRequestException if ID is null
     * @throws NotFoundException   if no third party is found with the given ID
     */
    public ThirdPartyDTO findById(Long id) throws BadRequestException, NotFoundException {
        if (id == null) {
            log.error("SERVICE - Third Party not found - FIND ONE");
            throw new BadRequestException();
        }

        Optional<ThirdParty> thirdParty = thirdPartyRepository.findById(id);
        if (thirdParty.isPresent()) {
            log.info("SERVICE - Found a Third Party on DB - FIND ONE");
            return thirdPartyMapper.toDto(thirdParty.get());
        } else {
            log.error("SERVICE - Third Party not found - FIND ONE");
            throw new NotFoundException();
        }
    }

    /**
     * Creates a new third party.
     *
     * @param thirdPartyDTO Data of the third party to create
     * @return Created ThirdPartyDTO
     * @throws BadRequestException    if thirdPartyDTO is null or invalid
     * @throws DuplicatedValueException if a third party with the same name already exists
     */
    public ThirdPartyDTO create(ThirdPartyDTO thirdPartyDTO) throws BadRequestException, DuplicatedValueException {
        if (thirdPartyDTO == null || !isDataValid(thirdPartyDTO)) {
            log.error("SERVICE - Third Party data is invalid - CREATE");
            throw new BadRequestException();
        }

        ThirdParty thirdParty = thirdPartyMapper.toEntity(thirdPartyDTO);
        thirdParty.setId(null);
        try {
            return thirdPartyMapper.toDto(thirdPartyRepository.save(thirdParty));
        } catch (Exception e) {
            log.error("SERVICE - Duplicated Third Party Name - CREATE");
            throw new DuplicatedValueException();
        }
    }

    /**
     * Updates an existing third party.
     *
     * @param thirdPartyDTO Data of the third party to update
     * @return Updated ThirdPartyDTO
     * @throws BadRequestException    if thirdPartyDTO is null, invalid, or ID is null
     * @throws NotFoundException      if no third party is found with the given ID
     * @throws DuplicatedValueException if a third party with the updated name already exists
     */
    public ThirdPartyDTO update(ThirdPartyDTO thirdPartyDTO)
            throws BadRequestException, NotFoundException, DuplicatedValueException {
        if (thirdPartyDTO == null || thirdPartyDTO.getId() == null || !isDataValid(thirdPartyDTO)) {
            log.error("SERVICE - Third Party data is invalid - UPDATE");
            throw new BadRequestException();
        }

        Optional<ThirdParty> maybeThirdParty = thirdPartyRepository.findById(thirdPartyDTO.getId());
        if (maybeThirdParty.isEmpty()) {
            log.error("SERVICE - Third Party Not Found - UPDATE");
            throw new NotFoundException();
        }

        ThirdParty updatedThirdParty = thirdPartyMapper.toEntity(thirdPartyDTO);
        try {
            updatedThirdParty = thirdPartyRepository.save(updatedThirdParty);
        } catch (Exception e) {
            log.error("SERVICE - Duplicated Third Party Name - UPDATE");
            throw new DuplicatedValueException();
        }

        return thirdPartyMapper.toDto(updatedThirdParty);
    }

    /**
     * Deletes a third party by its ID.
     *
     * @param id ID of the third party to delete
     * @return Deleted ThirdPartyDTO
     * @throws BadRequestException       if ID is null
     * @throws NotFoundException         if no third party is found with the given ID
     * @throws InternalServerException   if deletion fails due to an unknown error
     */
    public ThirdPartyDTO deleteById(Long id) throws BadRequestException, NotFoundException, InternalServerException {
        ThirdPartyDTO thirdPartyDTO = findById(id);
        thirdPartyRepository.delete(thirdPartyMapper.toEntity(thirdPartyDTO));
        if (thirdPartyRepository.existsById(id)) {
            log.error("SERVICE - Third Party Not Deleted due to Unknown Error - DELETE");
            throw new InternalServerException();
        }

        return thirdPartyDTO;
    }


    private boolean isDataValid(ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyDTO.getThirdPartyName() != null;
    }
}

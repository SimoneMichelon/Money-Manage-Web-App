package osiride.vitt_be.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.dto.CategoryDTO;
import osiride.vitt_be.dto.ExpenseDTO;
import osiride.vitt_be.dto.ThirdPartyDTO;
import osiride.vitt_be.dto.UserDTO;
import osiride.vitt_be.dto.VaultDTO;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.DuplicatedValueException;
import osiride.vitt_be.error.InternalServerException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.error.OperationNotPermittedException;
import osiride.vitt_be.mapper.CategoryMapper;
import osiride.vitt_be.mapper.ExpenseMapper;
import osiride.vitt_be.mapper.VaultMapper;
import osiride.vitt_be.repository.ExpenseRepository;
import osiride.vitt_be.utils.CategoryReport;
import osiride.vitt_be.utils.CategoryReportDTO;

@Slf4j
@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;
	private final CategoryMapper categoryMapper; 
	private final ExpenseMapper expenseMapper;
	private final VaultService vaultService;
	private final CategoryService categoryService;
	private final ThirdPartyService thirdPartyService;
	private final VaultMapper vaultMapper;
	private final AuthService authService;
	
	public ExpenseService(ExpenseRepository expenseRepository, 
			ExpenseMapper expenseMapper,
			VaultService vaultService, 
			CategoryService categoryService,
			ThirdPartyService thirdPartyService,
			VaultMapper vaultMapper,
			CategoryMapper categoryMapper,
			AuthService authService) {
		this.expenseRepository = expenseRepository;
		this.expenseMapper = expenseMapper;
		this.vaultService = vaultService;
		this.categoryService = categoryService;
		this.thirdPartyService = thirdPartyService;
		this.vaultMapper = vaultMapper;
		this.authService = authService;
		this.categoryMapper = categoryMapper;
	}

	/**
	 * Retrieves all Expenses from the database.
	 * 
	 * This method fetches all Expense entities from the database, maps them to ExpenseDTO objects,
	 * and returns them as a list.
	 * 
	 * @return a list of ExpenseDTOs representing all Expenses in the database
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * List<ExpenseDTO> expenses = expenseService.getAll();
	 * for (ExpenseDTO expense : expenses) {
	 *     // process each expense
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 */
	public List<ExpenseDTO> getAll() {
		log.info("SERVICE - Get All Expenses from DB - READ ALL");
		return expenseRepository.findAll()
				.stream()
				.map(expense -> expenseMapper.toDto(expense))
				.toList();
	}

	/**
	 * Finds an Expense by its ID.
	 * 
	 * This method attempts to retrieve an Expense object from the database using the provided ID.
	 * If the ID is null, a {@link BadRequestException} is thrown.
	 * If no Expense is found with the given ID, a {@link NotFoundException} is thrown.
	 * Otherwise, the Expense object is mapped to an ExpenseDTO and returned.
	 * 
	 * @param id the ID of the Expense to find
	 * @return the ExpenseDTO corresponding to the found Expense
	 * @throws BadRequestException if the provided ID is null
	 * @throws NotFoundException if no Expense is found with the given ID
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     ExpenseDTO expense = expenseService.findById(1L);
	 *     // process expense
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 */
	public ExpenseDTO findById(Long id) throws BadRequestException, NotFoundException {
		if(id == null) {
			log.error("SERVICE - Expense id is null- FIND ONE");
			throw new BadRequestException();
		}

		Optional<Expense> maybeExpense = expenseRepository.findById(id);
		if(maybeExpense.isPresent()) {
			log.info("SERVICE - Found a Expense on DB - FIND ONE");
			return expenseMapper.toDto(maybeExpense.get());
		} 
		else {
			log.error("SERVICE - Expense not found - FIND ONE");
			throw new NotFoundException();
		}
	}

	/**
	 * Creates a new Expense.
	 * 
	 * This method takes an ExpenseDTO, validates its data, and saves it as a new Expense entity
	 * in the database. The method ensures that the ExpenseDTO is valid and that related entities
	 * (Vault, Category, ThirdParty) exist in the database. If the provided data is invalid, a 
	 * {@link BadRequestException} is thrown. If the start date of the Expense is not before the end date
	 * (if the expense is programmed), a {@link BadRequestException} is thrown. After saving the Expense,
	 * it attempts to update the corresponding Vault's capital. If the capital update operation is successful,
	 * the method returns the created ExpenseDTO. If the update operation fails due to a duplicated value,
	 * an {@link OperationNotPermittedException} is thrown.
	 * 
	 * @param expenseDTO the ExpenseDTO containing the data to create a new Expense
	 * @return the created ExpenseDTO
	 * @throws BadRequestException if the provided ExpenseDTO is null or contains invalid data,
	 *                             or if the start date is not before the end date for a programmed Expense
	 * @throws NotFoundException if any related entity (Vault, Category, ThirdParty) is not found
	 * @throws OperationNotPermittedException if the Vault's capital update operation fails
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     ExpenseDTO newExpense = expenseService.create(expenseDTO);
	 *     // process newExpense
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * } catch (OperationNotPermittedException e) {
	 *     // handle operation not permitted
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 */
	public ExpenseDTO create(ExpenseDTO expenseDTO) throws BadRequestException, NotFoundException, OperationNotPermittedException, InvalidTokenException, NotAuthorizedException {
		if(expenseDTO == null || !isDataValid(expenseDTO)) {
			log.error("SERVICE - Expense data is invalid - CREATE");
			throw new BadRequestException();
		}

		VaultDTO vaultDTO = vaultService.findById(expenseDTO.getVaultDTO().getId());
		expenseDTO.setVaultDTO(vaultDTO);

		CategoryDTO categoryDTO = categoryService.findById(expenseDTO.getCategoryDTO().getId());
		expenseDTO.setCategoryDTO(categoryDTO);

		ThirdPartyDTO thirdPartyDTO = thirdPartyService.findById(expenseDTO.getThirdPartyDTO().getId());
		expenseDTO.setThirdPartyDTO(thirdPartyDTO);

		Expense expense = expenseMapper.toEntity(expenseDTO);
		if(!expense.getIsProgrammed()) {
			expense.setEndDate(expense.getStartDate());
		}else if(!expense.getStartDate().isBefore(expense.getEndDate())) {
			log.error("SERVICE - Expense Date is invalid - CREATE");
			throw new BadRequestException();
		}
		expense.setId(null);
		expense = expenseRepository.save(expense);

		boolean operationResult = false;
		try {
			operationResult = vaultService.updateCapital(expense);
			if(operationResult) {
				return expenseMapper.toDto(expense);
			}
			else {
				throw new OperationNotPermittedException();
			}
		} catch (DuplicatedValueException e) {
			throw new OperationNotPermittedException();
		}
	}

	/**
	 * Updates an existing Expense.
	 * 
	 * This method takes an ExpenseDTO, validates its data, and updates the corresponding 
	 * Expense entity in the database. The method ensures that the ExpenseDTO is valid and
	 * that the related entities (Vault, Category, ThirdParty) are correct and exist in the 
	 * database. If the provided data is invalid, a {@link BadRequestException} is thrown.
	 * If the Vault ID in the old Expense differs from the one in the new ExpenseDTO, an 
	 * {@link OperationNotPermittedException} is thrown. If the Expense to be updated is 
	 * not found, a {@link NotFoundException} is thrown.
	 * 
	 * @param expenseDTO the ExpenseDTO containing the data to update the Expense
	 * @return the updated ExpenseDTO
	 * @throws BadRequestException if the provided ExpenseDTO is null, has a null ID, or contains invalid data
	 * @throws OperationNotPermittedException if the Vault ID of the existing Expense differs from the one in the new ExpenseDTO
	 * @throws NotFoundException if the Expense to be updated is not found
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     ExpenseDTO updatedExpense = expenseService.update(expenseDTO);
	 *     // process updatedExpense
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (OperationNotPermittedException e) {
	 *     // handle operation not permitted
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 * @throws DuplicatedValueException 
	 */
	public ExpenseDTO update(ExpenseDTO expenseDTO) throws BadRequestException, OperationNotPermittedException, NotFoundException, InvalidTokenException, NotAuthorizedException {
		if(expenseDTO == null || expenseDTO.getId() == null || !isDataValid(expenseDTO) ) {
			log.error("SERVICE - Expense data is invalid - UPDATE");
			throw new BadRequestException();
		}

		expenseDTO.setVaultDTO(vaultService.findById(expenseDTO.getVaultDTO().getId()));
		ExpenseDTO oldExpense = findById(expenseDTO.getId());


		if(oldExpense.getVaultDTO().getId() != expenseDTO.getVaultDTO().getId()) {
			log.error("SERVICE - Expense data is invalid - UPDATE");
			throw new OperationNotPermittedException();
		} else if(expenseDTO.getStartDate().isAfter(expenseDTO.getEndDate()) ) {
			log.error("SERVICE - Expense Date is invalid - UPDATE");
			throw new BadRequestException();
		}
		
		oldExpense.setAmount(oldExpense.getAmount().multiply(BigDecimal.valueOf(-1)));
		Expense toSubtract = expenseMapper.toEntity(oldExpense);
		Expense toAdd = expenseMapper.toEntity(expenseDTO);
		
		try {
			if(!(vaultService.updateCapital(toSubtract) && vaultService.updateCapital(toAdd))) {
				throw new OperationNotPermittedException();
			}
		} catch (DuplicatedValueException e) {
			throw new OperationNotPermittedException();
		}

		toAdd = expenseRepository.save(toAdd);
		return expenseMapper.toDto(toAdd);
	}

	/**
	 * Deletes an existing Expense by its ID.
	 * 
	 * This method attempts to delete an Expense entity from the database using the provided ID.
	 * If the ID is null or if the Expense with the given ID does not exist, a 
	 * {@link BadRequestException} or {@link NotFoundException} is thrown respectively.
	 * After attempting deletion, if the Expense still exists in the repository, an 
	 * {@link InternalServerException} is thrown.
	 * 
	 * @param id the ID of the Expense to delete
	 * @return the deleted ExpenseDTO
	 * @throws BadRequestException if the provided ID is null
	 * @throws NotFoundException if no Expense is found with the given ID
	 * @throws InternalServerException if the Expense is not deleted due to an unknown error
	 * 
	 * <p>
	 * Example usage:
	 * <pre>
	 * {@code
	 * try {
	 *     ExpenseDTO deletedExpense = expenseService.deleteById(1L);
	 *     // process deletedExpense
	 * } catch (BadRequestException e) {
	 *     // handle bad request
	 * } catch (NotFoundException e) {
	 *     // handle not found
	 * } catch (InternalServerException e) {
	 *     // handle internal server error
	 * }
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @author Simone
	 * @throws OperationNotPermittedException 
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 */
	public ExpenseDTO deleteById(Long id) throws BadRequestException, NotFoundException, InternalServerException, InvalidTokenException, NotAuthorizedException, OperationNotPermittedException{
		ExpenseDTO expenseDTO = findById(id);
		expenseDTO.setAmount(expenseDTO.getAmount().multiply(BigDecimal.valueOf(-1)));

		try {
			if(!vaultService.updateCapital(expenseMapper.toEntity(expenseDTO))) {
				throw new OperationNotPermittedException();
			}
		} catch (DuplicatedValueException e) {
			throw new OperationNotPermittedException();
		}
		expenseDTO.setAmount(expenseDTO.getAmount().multiply(BigDecimal.valueOf(-1)));
		
		
		expenseRepository.deleteById(id);

		if(!expenseRepository.existsById(id)) {
			return expenseDTO;
		}
		else {
			log.error("SERVICE - Expense Not Deleted due to Unknown Error - DELETE");
			throw new InternalServerException();
		}
	}
	
	
	/**
	 * Get all Expense By Vault
	 * @param vaultDTO
	 * @return
	 * @throws NotAuthorizedException 
	 * @throws InvalidTokenException 
	 * @throws NotFoundException 
	 * @throws BadRequestException 
	 */
	public List<ExpenseDTO> getByVault(Long id) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		List<ExpenseDTO> list = new ArrayList<ExpenseDTO>();
		VaultDTO vaultDTO = vaultService.findById(id);
		
		log.info("SERVICE - Get All Expenses by Vault ID - READ VAULT");
		list = expenseRepository.findByVault(vaultMapper.toEntity(vaultDTO))
				.stream()
				.map( expense -> expenseMapper.toDto(expense))
				.toList();

		return list;
	}
	
	
	public List<ExpenseDTO> getAllByPrincipal() throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		List<ExpenseDTO> list = new ArrayList<ExpenseDTO>();
		UserDTO userDTO = authService.getPrincipal();
		
		log.info("SERVICE - Get All Expenses by Principal - READ USER");
		list = expenseRepository.findExpensesByVaultUserId(userDTO.getId())
				.stream()
				.map( expense -> expenseMapper.toDto(expense))
				.toList();

		return list;
	}
	
	/**
	 * Retrieves the expense reports for each category based on the specified vault ID.
	 * 
	 * This method fetches the list of category expense reports from the repository, 
	 * maps them into a list of {@link CategoryReportDTO}, and returns the result.
	 * It handles multiple custom exceptions in case of issues during the process.
	 * 
	 * @param vaultId The ID of the vault for which the expense reports are retrieved.
	 * @return A list of {@link CategoryReportDTO} containing the expense percentages for each category.
	 * 
	 * @throws BadRequestException If the request is malformed or contains invalid data.
	 * @throws NotFoundException If the vault with the specified ID is not found.
	 * @throws InvalidTokenException If the provided token is invalid or expired.
	 * @throws NotAuthorizedException If the user is not authorized to access the vault's data.
	 */
	public List<CategoryReportDTO> getExpensesCategoryReports(Long vaultId) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		List<CategoryReport> listTmp = expenseRepository.getExpensesCategoryReports(vaultId);
		
		List<CategoryReportDTO> listReport = listTmp
				.stream()
				.map(wrapper -> new CategoryReportDTO(
						categoryMapper.toDto(wrapper.getCategory()),
						wrapper.getPercentage())
						).toList();
		
		return listReport;
	}
	
	public BigDecimal getTotalAmount() {
		return BigDecimal.ZERO;
	}


	private boolean isDataValid(ExpenseDTO object) {
		if (object == null) {
			return false;
		}

		return object.getCausal() != null &&
				object.getAmount() != null &&
				object.getIsProgrammed() != null &&
				object.getStartDate() != null &&
				object.getEndDate() != null &&
				object.getVaultDTO() != null &&
				object.getCategoryDTO() != null &&
				object.getThirdPartyDTO() != null;
	}
}

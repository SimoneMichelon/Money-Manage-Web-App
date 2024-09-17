package osiride.vitt_be.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.OperationMapper;
import osiride.vitt_be.utils.OperationDTO;

@Slf4j
@Service
public class OperationService {

	private final RevenueService revenueService;
	private final ExpenseService expenseService;
	private final OperationMapper operationMapper;

	public OperationService(RevenueService revenueService,
			ExpenseService expenseService,
			OperationMapper operationMapper) {
		this.revenueService = revenueService;
		this.expenseService = expenseService;
		this.operationMapper = operationMapper;
	}

	/**
	 * Retrieves all operations related to expenses and revenues.
	 * <p>
	 * This method is responsible for fetching all the operations associated with 
	 * expenses and revenues from their respective services. It then encapsulates 
	 * these lists into an `OperationListsWrapper` object, which is returned to the caller.
	 * </p>
	 *
	 * @return OperationListsWrapper - A wrapper object containing two lists: 
	 *         one for all expenses and another for all revenues.
	 *
	 * <p><b>Log Message:</b></p>
	 * The method logs an informational message indicating that all operations 
	 * (expenses and revenues) are being retrieved.
	 *
	 * <p><b>Usage Example:</b></p>
	 * <pre>
	 * {@code
	 * OperationListsWrapper operations = getAllOperations();
	 * List<Expense> expenses = operations.getListExpense();
	 * List<Revenue> revenues = operations.getListRevenue();
	 * }
	 * </pre>
	 */
	public List<OperationDTO> getAllOperations(){
		List<OperationDTO> operationList = new ArrayList<OperationDTO>();

		log.info("SERVICE - Get All Operation - READ ALL");

		operationList.addAll(expenseService.getAll()
				.stream()
				.map(expense -> operationMapper
						.toOperationDTO(expense))
				.toList());

		operationList.addAll(revenueService.getAll()
				.stream()
				.map(revenue -> operationMapper
						.toOperationDTO(revenue))
				.toList());

		return operationList;
	}


	/**
	 * Retrieves all operations (expenses and revenues) associated with a specific vault.
	 * <p>
	 * This method fetches all the operations related to expenses and revenues from their respective 
	 * services, filtered by the given vault ID. The results are encapsulated within an 
	 * `OperationListsWrapper` object, which is returned to the caller.
	 * </p>
	 *
	 * @param id The ID of the vault for which operations are to be retrieved.
	 * 
	 * @return OperationListsWrapper - A wrapper object containing two lists: 
	 *         one for all expenses and another for all revenues associated with the specified vault.
	 *
	 * @throws BadRequestException if the provided vault ID is invalid or null.
	 * @throws NotFoundException if no vault with the specified ID exists.
	 * @throws InvalidTokenException if the request token is invalid.
	 * @throws NotAuthorizedException if the caller is not authorized to access the vault's operations.
	 *
	 * <p><b>Log Message:</b></p>
	 * The method logs an informational message indicating that operations (expenses and revenues) 
	 * for the specified vault are being retrieved.
	 *
	 * <p><b>Usage Example:</b></p>
	 * <pre>
	 * {@code
	 * Long vaultId = 123L;
	 * OperationListsWrapper operations = getAllOperationsByVault(vaultId);
	 * List<Expense> expenses = operations.getListExpense();
	 * List<Revenue> revenues = operations.getListRevenue();
	 * }
	 * </pre>
	 */
	public List<OperationDTO> getAllOperationsByVaultId(Long id) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		List<OperationDTO> operationList = new ArrayList<OperationDTO>();

		log.info("SERVICE - Get All Operation By Vault - READ VAULT");

		operationList.addAll(expenseService.getByVault(id)
				.stream()
				.map(expense -> operationMapper
						.toOperationDTO(expense))
				.toList());

		operationList.addAll(revenueService.getByVault(id)
				.stream()
				.map(revenue -> operationMapper
						.toOperationDTO(revenue))
				.toList());

		return operationList;
	}
	
	public List<OperationDTO> getAllOperationsByPrincipal() throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		List<OperationDTO> operationList = new ArrayList<OperationDTO>();

		log.info("SERVICE - Get All Operation By Vault - READ VAULT");

		operationList.addAll(expenseService.getAllByPrincipal()
				.stream()
				.map(expense -> operationMapper
						.toOperationDTO(expense))
				.toList());

		operationList.addAll(revenueService.getAllByPrincipal()
				.stream()
				.map(revenue -> operationMapper
						.toOperationDTO(revenue))
				.toList());

		return operationList;
	}

}

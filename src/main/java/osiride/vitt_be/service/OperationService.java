package osiride.vitt_be.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.utils.OperationListsWrapper;

@Slf4j
@Service
public class OperationService {
	
	private final RevenueService revenueService;
	private final ExpenseService expenseService;

	public OperationService(RevenueService revenueService,
			ExpenseService expenseService) {
		this.revenueService = revenueService;
		this.expenseService = expenseService;
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
	public OperationListsWrapper getAllOperations(){
		OperationListsWrapper operListWrapper = new OperationListsWrapper();
		
		log.info("SERVICE - Get All Operation - READ ALL");
		
		operListWrapper.setListExpense(expenseService.getAll());		
		operListWrapper.setListRevenue(revenueService.getAll());
		
		return operListWrapper;
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
	public OperationListsWrapper getAllOperationsByVault(Long id) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException{
		OperationListsWrapper operListWrapper = new OperationListsWrapper();
		
		log.info("SERVICE - Get All Operation By Vault - READ VAULT");
		
		operListWrapper.setListExpense(expenseService.getByVault(id));		
		operListWrapper.setListRevenue(revenueService.getByVault(id));
		
		return operListWrapper;
	}

}

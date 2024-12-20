package osiride.vitt_be.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.error.BadRequestException;
import osiride.vitt_be.error.InvalidTokenException;
import osiride.vitt_be.error.NotAuthorizedException;
import osiride.vitt_be.error.NotFoundException;
import osiride.vitt_be.mapper.OperationMapper;
import osiride.vitt_be.utils.CategoryReportDTO;
import osiride.vitt_be.utils.OperationDTO;
import osiride.vitt_be.utils.PriceHistoryObj;
import osiride.vitt_be.utils.TransactionType;

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

		Collections.sort(operationList, Comparator.comparing(OperationDTO::getStartDate).reversed());

		return operationList;
	}


	/**
	 * Retrieves all operations (expenses and revenues) associated with a specific vault.
	 * <p>
	 * This method fetches all the operations related to expenses and revenues from their respective 
	 * services, filtered by the given vault ID. The results are encapsulated within a 
	 * list of {@link OperationDTO} objects, which is returned to the caller.
	 * </p>
	 *
	 * @param id The ID of the vault for which operations are to be retrieved. Must not be null or negative.
	 * 
	 * @return List<OperationDTO> - A list containing all expenses and revenues associated with the specified vault.
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
	 * List<OperationDTO> operations = getAllOperationsByVaultId(vaultId);
	 * List<Expense> expenses = operations.getListExpense();
	 * List<Revenue> revenues = operations.getListRevenue();
	 * }
	 * </pre>
	 */
	public List<OperationDTO> getAllOperationsByVaultId(Long id) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException {
	    if (id == null || id < 0) {
	        throw new BadRequestException("Invalid vault ID: " + id);
	    }

	    List<OperationDTO> operationList = new ArrayList<>();

	    log.info("SERVICE - Get All Operation By Vault - READ VAULT");

	    // Retrieve expenses and revenues, then map them to OperationDTO
	    operationList.addAll(expenseService.getByVault(id)
	            .stream()
	            .map(operationMapper::toOperationDTO)
	            .toList());

	    operationList.addAll(revenueService.getByVault(id)
	            .stream()
	            .map(operationMapper::toOperationDTO)
	            .toList());

	    // Sort the operation list by start date in descending order
	    Collections.sort(operationList, Comparator.comparing(OperationDTO::getStartDate).reversed());

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

		Collections.sort(operationList, Comparator.comparing(OperationDTO::getStartDate).reversed());

		return operationList;
	}

	/**
	 * Retrieves the operation history report for a given vault, identified by its ID.
	 * 
	 * This method fetches all operations related to the specified vault, orders them 
	 * chronologically by their start date, and calculates the cumulative capital over time
	 * based on the type of each operation (either EXPENSE or REVENUE).
	 * 
	 * @param vaultId The ID of the vault for which the operation history report is requested.
	 * @return A list of {@code PriceHistoryObj} representing the history of the vault's capital over time.
	 * 
	 * @throws BadRequestException If the operation type is invalid or unspecified.
	 * @throws NotFoundException If the vault with the given ID cannot be found.
	 * @throws InvalidTokenException If the provided token is invalid or expired.
	 * @throws NotAuthorizedException If the user does not have authorization to access the vault.
	 * 
	 * <b>Details:</b>
	 * - The operations are retrieved using {@code getAllOperationsByVaultId(vaultId)}.
	 * - The list of operations is sorted by start date in chronological order.
	 * - For each operation:
	 *    - If it's an EXPENSE, the amount is subtracted from the cumulative capital.
	 *    - If it's a REVENUE, the amount is added to the cumulative capital.
	 * - The capital change is tracked and stored in a {@code Map<LocalDate, BigDecimal>} structure.
	 * - Finally, the map is converted into a list of {@code PriceHistoryObj}, which contains the date and corresponding capital value.
	 */
	public List<PriceHistoryObj> getOperationHistoryReport(Long vaultId) 
			throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException {		
		/*
		 * Get Vault operations list by ID
		 */
		List<OperationDTO> operationList = getAllOperationsByVaultId(vaultId);
		
		/*
		 * Sort by StartDate (Chronological History)
		 */
		Collections.sort(operationList, Comparator.comparing(OperationDTO::getStartDate));

		Map<LocalDate, BigDecimal> utility = new HashMap<>();
		BigDecimal capitalTmp = BigDecimal.ZERO;
		
		/*
		 * Insert DAY 0 of the Vault - 0 Capital
		 */
		if(operationList.size() > 0) {
			utility.put(operationList.get(0).getStartDate().minusDays(1), BigDecimal.ZERO);
		}
		else {
			utility.put(LocalDate.now(), BigDecimal.ZERO);
		}

		/*
		 * Sum o Subtract the amount of each operation to capitalTmp
		 * based on the Operation Type ( EXPENSE or REVENUE )
		 */
		for (OperationDTO operationDTO : operationList) {
			switch (operationDTO.getType()) {
			case EXPENSE:
				capitalTmp = capitalTmp.subtract(operationDTO.getAmount());
				break;
			case REVENUE:
				capitalTmp = capitalTmp.add(operationDTO.getAmount());
				break;
			default:
				throw new BadRequestException();
			}
			utility.put(operationDTO.getStartDate(), capitalTmp);
		}
		
		utility = new TreeMap<>(utility);


		/*
		 * Converting Map<LocalDate, BigDecimal> into List<PriceHistoryObj>
		 */
		List<PriceHistoryObj> vaultHistoryReport = new ArrayList<>();
		for (Map.Entry<LocalDate, BigDecimal> entry : utility.entrySet()) {
			LocalDate localDate = entry.getKey();
			BigDecimal capital = entry.getValue();
			vaultHistoryReport.add(new PriceHistoryObj(localDate, capital));
		}

		return vaultHistoryReport;
	}
	
	/**
	 * Retrieves the operation category report for a specific vault, categorizing 
	 * the reports into revenues and expenses.
	 * 
	 * This method creates a map that associates each transaction type 
	 * (REVENUE and EXPENSE) with its corresponding list of category reports 
	 * for the specified vault. It handles multiple custom exceptions that may 
	 * arise during the operation.
	 * 
	 * @param vaultId The ID of the vault for which the operation category reports are retrieved.
	 * @return A map where the keys are {@link TransactionType} and the values are 
	 *         lists of {@link CategoryReportDTO} containing the reports for each category.
	 * 
	 * @throws BadRequestException If the request is malformed or contains invalid data.
	 * @throws NotFoundException If the vault with the specified ID is not found.
	 * @throws InvalidTokenException If the provided token is invalid or expired.
	 * @throws NotAuthorizedException If the user is not authorized to access the vault's data.
	 */
	public Map<TransactionType,List<CategoryReportDTO>> getOperationCategoryReportPerVault(Long vaultId) throws BadRequestException, NotFoundException, InvalidTokenException, NotAuthorizedException {
		/*
		 * Dichiaration List of Category Reports
		 */
		Map<TransactionType, List<CategoryReportDTO>> reportList = new HashMap<TransactionType, List<CategoryReportDTO>>();
		
		/*
		 * Add Revenue Report List to the ReportList
		 */
		reportList.put(TransactionType.REVENUE, revenueService.getRevenuesCategoryReports(vaultId));
		
		/*
		 * Add Expense Report List to the ReportList
		 */
		reportList.put(TransactionType.EXPENSE, expenseService.getExpensesCategoryReports(vaultId));
		
		return reportList;
	}


}

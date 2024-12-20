package osiride.vitt_be.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import osiride.vitt_be.dto.ExpenseDTO;
import osiride.vitt_be.dto.RevenueDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationListsWrapper {
	
	List<ExpenseDTO> listExpense;
	List<RevenueDTO> listRevenue;
}

package osiride.vitt_be.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaultSummary {

	private Long vaultId;
	private BigDecimal balance;
	private BigDecimal totalExpenses;
	private BigDecimal totalRevenue;
}

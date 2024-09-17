package osiride.vitt_be.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import osiride.vitt_be.domain.Category;
import osiride.vitt_be.domain.ThirdParty;
import osiride.vitt_be.domain.Vault;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDTO {

	private Long id;
	private String causal;
	private BigDecimal amount;
	private Boolean isProgrammed;
	private LocalDate startDate;
	private LocalDate endDate;
	private Vault vault;
	private Category category;
	private ThirdParty thirdParty;
	private TransactionType type;
}

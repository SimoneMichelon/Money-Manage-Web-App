package osiride.vitt_be.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
	private Long id;
	@NotNull
	private String causal;
	@NotNull
	@Min(value = 0)
    private BigDecimal amount;
	@NotNull
	private Boolean isProgrammed;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	@NotNull
	private VaultDTO vaultDTO;
	@NotNull
	private CategoryDTO categoryDTO;
	@NotNull
	private ThirdPartyDTO thirdPartyDTO;
}

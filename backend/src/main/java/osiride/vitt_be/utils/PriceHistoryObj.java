package osiride.vitt_be.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistoryObj {

	private LocalDate date;
	private BigDecimal capital;
}

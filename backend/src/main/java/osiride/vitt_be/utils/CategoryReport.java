package osiride.vitt_be.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import osiride.vitt_be.domain.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReport {

	Category category;
	BigDecimal percentage;
}

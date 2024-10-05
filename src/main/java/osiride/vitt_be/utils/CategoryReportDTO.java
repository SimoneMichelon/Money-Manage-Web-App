package osiride.vitt_be.utils;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import osiride.vitt_be.dto.CategoryDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {

	CategoryDTO categoryDTO;
	BigDecimal percentage;
}

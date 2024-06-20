package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "expense")
public class Expense  extends Operation{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exp_opr_id")
	private Long id;
}

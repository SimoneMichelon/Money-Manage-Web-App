package osiride.vitt_be.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "operation")
@Getter
@Setter
public abstract class Operation extends AbstractAuditingEntity{
	
	@Column(name = "opr_causal")
	private String causal;
	
	@Column(name = "opr_amount", scale = 2, precision = 10, nullable = false)
    private BigDecimal amount;
	
	@Column(name = "opr_prg")
	private Boolean isProgrammed;
	
	@Column(name = "opr_start")
	private LocalDate startDate;
	
	@Column(name = "opr_end")
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "opr_vlt_id", referencedColumnName = "vlt_id")
	private Vault vault;
	
	@ManyToOne
	@JoinColumn(name = "opr_ctg_id", referencedColumnName = "ctg_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "opr_thp_id", referencedColumnName = "thp_id")
	private ThirdParty thirdPartys;
}

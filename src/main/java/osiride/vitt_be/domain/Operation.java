package osiride.vitt_be.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(Operation.class)
public abstract class Operation extends AbstractAuditingEntity{
	
	@NotNull
	@Column(name = "opr_causal")
	private String causal;
	
	@NotNull
	@Column(name = "opr_amount", scale = 2, precision = 10, nullable = false)
    private BigDecimal amount;
	
	@NotNull
	@Column(name = "opr_prg")
	private Boolean isProgrammed;
	
	@NotNull
	@Column(name = "opr_start")
	private LocalDate startDate;
	
	@NotNull
	@Column(name = "opr_end")
	private LocalDate endDate;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "opr_vlt_id", referencedColumnName = "vlt_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Vault vault;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "opr_ctg_id", referencedColumnName = "ctg_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Category category;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "opr_thp_id", referencedColumnName = "thp_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private ThirdParty thirdPartys;
}

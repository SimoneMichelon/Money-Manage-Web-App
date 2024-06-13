package osiride.vitt_be.domain;

import java.math.BigDecimal;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "vault")
@Getter
@Setter
public class Vault extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vlt_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "vlt_usr_id", referencedColumnName = "usr_id")
    private User user;

    @Column(name = "vlt_cpt", scale = 2, precision = 10, nullable = false)
    private BigDecimal capital;

}

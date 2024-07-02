package osiride.vitt_be.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "vault")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vault extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vlt_id")
    private Long id;

    @NotNull
    @Column(name = "vlt_name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "vlt_usr_id", referencedColumnName = "usr_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @NotNull
    @Column(name = "vlt_cpt", scale = 2, precision = 10, nullable = false)
    private BigDecimal capital;
}

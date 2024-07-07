package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "credential")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credential extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crd_id")
    private Long id;

    @Column(name = "crd_email", unique = true)
    private String email;

    @Column(name = "crd_psw")
    private String password;
    
    @OneToOne
    @JoinColumn(name = "crd_usr_id", referencedColumnName = "usr_id", nullable = false)
    private User user;
}

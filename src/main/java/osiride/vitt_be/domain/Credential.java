package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "credential")
public class Credential extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crd_id")
    private Long id;

    @Column(name = "crd_email", unique = true)
    private String email;

    @Column(name = "crd_psw")
    private String password;

}

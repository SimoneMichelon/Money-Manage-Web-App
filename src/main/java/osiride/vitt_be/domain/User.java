package osiride.vitt_be.domain;

import java.sql.Blob;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "crd_usr_id", referencedColumnName = "crd_id", nullable = false)
    private Credential credential;

    @Column(name = "usr_frt_name")
    private String firstName;

    @Column(name = "usr_lst_name")
    private String lastName;

    @Column(name = "usr_dob")
    private LocalDate dob;

    @Lob
    @Column(name = "usr_img_prf")
    private Blob imgProfile;

}

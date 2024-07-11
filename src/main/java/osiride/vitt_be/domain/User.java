package osiride.vitt_be.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osiride.vitt_be.constant.Role;

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

    @NotNull
    @Column(name = "usr_frt_name")
    private String firstName;
    
    @NotNull
    @Column(name = "usr_lst_name")
    private String lastName;

    @NotNull
    @Column(name = "usr_dob")
    private LocalDate dob;

    @NotNull
    @Column(name = "usr_img_prf")
    private String imgProfile;
    
    @NotNull
    @Column(name = "usr_role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;
}

package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "third_party")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdParty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thp_id")
    private Long id;

    @NotNull
    @Column(name = "thp_name", unique = true)
    private String thirdPartyName;

}

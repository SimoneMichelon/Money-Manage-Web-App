package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "third_party")
@Getter
@Setter
public class ThirdParty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thp_id")
    private Long id;

    @Column(name = "thp_name", unique = true)
    private String thirdPartyName;

}

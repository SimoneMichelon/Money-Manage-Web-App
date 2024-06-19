package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "firm")
@Getter
@Setter
public class Firm {
    
    @Id
    @Column(name = "frm_id")
    private Long id;

    @Column(name = "frm_name")
    private String firmName;

}

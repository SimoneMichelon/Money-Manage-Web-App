package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "op_category")
@Getter
@Setter
public class Category extends AbstractAuditingEntity{

    @Id
    @Column(name = "ctg_id")
    private Long id;

    @Column(name = "ctg_name")
    private String categoryName;

    


    
}

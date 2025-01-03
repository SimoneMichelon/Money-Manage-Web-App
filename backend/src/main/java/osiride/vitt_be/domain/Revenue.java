package osiride.vitt_be.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "revenue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue extends Operation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rev_opr_id")
	private Long id;

}

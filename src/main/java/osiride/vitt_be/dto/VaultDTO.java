package osiride.vitt_be.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaultDTO {

    private Long id;
    private String name;
    private UserDTO userDTO;
    private BigDecimal capital;
    private String image;
}

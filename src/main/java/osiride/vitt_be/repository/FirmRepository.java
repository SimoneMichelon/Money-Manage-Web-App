package osiride.vitt_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Firm;

@Repository
public interface FirmRepository extends JpaRepository<Firm, Long> {

}

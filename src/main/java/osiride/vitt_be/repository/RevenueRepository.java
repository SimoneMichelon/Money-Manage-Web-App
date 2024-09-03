package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.domain.Vault;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

	Optional<Revenue> findById(Long id);
	
	List<Revenue> findByVault(Vault vault);

}

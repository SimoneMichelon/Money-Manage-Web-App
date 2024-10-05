package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.utils.VaultSummary;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

	Optional<Vault> findById(Long id);
	
	@Query(value = "SELECT v "
			+ " FROM vault v "
			+ " WHERE v.user.id = :userId ")
	List<Vault> getAllByUser(@Param("userId") Long id);
	
	@Query(value ="SELECT new osiride.vitt_be.utils.VaultSummary( " +
    "v.id, " +
    "v.capital, " +
    "COALESCE((SELECT SUM(e.amount) FROM expense e WHERE e.vault.id = v.id), 0), " +
    "COALESCE((SELECT SUM(r.amount) FROM revenue r WHERE r.vault.id = v.id), 0)) " +
    "FROM vault v " +
    "WHERE v.user.id = :id " +
    "GROUP BY v.id, v.capital")
	List<VaultSummary> getVaultsReport(@Param("id") Long id);

}

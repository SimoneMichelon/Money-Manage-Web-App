package osiride.vitt_be.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.utils.CategoryReport;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

	Optional<Revenue> findById(Long id);
	
	List<Revenue> findByVault(Vault vault);
	
	
	@Query("SELECT r " +
		       "FROM revenue r " +
		       "JOIN r.vault v " +
		       "WHERE v.user.id = :id")
	List<Revenue> findRevenueByVaultUserId(Long id);
	

	@Query("SELECT new osiride.vitt_be.utils.CategoryReport (r.category, "
		     + "Round((Sum(r.amount) / (SELECT Sum(amount) FROM revenue WHERE vault.id = :vaultId)) * 100, 2)) "
		     + "FROM revenue r "
		     + "WHERE r.vault.id = :vaultId "
		     + "GROUP BY r.category.id "
		     + "ORDER BY Round((Sum(r.amount) / (SELECT Sum(amount) FROM revenue WHERE vault.id = :vaultId)) * 100, 2) DESC")
	List<CategoryReport> getRevenuesCategoryReports(Long vaultId);
 
}

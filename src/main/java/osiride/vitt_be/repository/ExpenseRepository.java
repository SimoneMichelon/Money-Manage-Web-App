package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.utils.CategoryReport;

public interface ExpenseRepository  extends JpaRepository<Expense, Long>{

	Optional<Expense> findById(Long id);
	List<Expense> findByVault(Vault vault);
	
	@Query("SELECT e " +
		       "FROM expense e " +
		       "JOIN e.vault v " +
		       "WHERE v.user.id = :id")
	List<Expense> findExpensesByVaultUserId(Long id);
	
	@Query("SELECT new osiride.vitt_be.utils.CategoryReport (e.category, "
		     + "Round((Sum(e.amount) / (SELECT Sum(amount) FROM expense WHERE vault.id = :vaultId)) * 100, 2)) "
		     + "FROM expense e "
		     + "WHERE e.vault.id = :vaultId "
		     + "GROUP BY e.category.id")
	List<CategoryReport> getExpensesCategoryReports(Long vaultId);

}

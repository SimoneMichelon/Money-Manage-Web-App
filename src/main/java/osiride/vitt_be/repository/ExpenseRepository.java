package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.domain.Vault;

public interface ExpenseRepository  extends JpaRepository<Expense, Long>{

	Optional<Expense> findById(Long id);
	List<Expense> findByVault(Vault vault);
	
	@Query("SELECT e " +
		       "FROM Expense e " +
		       "JOIN e.vault v " +
		       "WHERE v.user.id = :id")
	List<Expense> findExpensesByVaultUserId(Long id);

}
